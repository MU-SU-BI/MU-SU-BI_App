package com.example.musubi.presenter.implementation;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.example.musubi.model.dto.CommentDto;
import com.example.musubi.model.dto.DistrictDto;
import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.GpsDto;
import com.example.musubi.model.dto.PostDetailDto;
import com.example.musubi.model.dto.PostDto;
import com.example.musubi.model.dto.SendCommentDto;
import com.example.musubi.model.dto.WritePostDto;
import com.example.musubi.model.entity.Guardian;
import com.example.musubi.model.entity.User;
import com.example.musubi.model.remote.RetrofitClient;
import com.example.musubi.presenter.contract.CommunityContract;
import com.example.musubi.util.callback.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommunityPresenter {
    private final CommunityContract.View view;
    private final RetrofitClient retrofitClient;
    private static final String TAG = "CommunityPresenter";  // 로그 태그 설정
    private FusedLocationProviderClient fusedLocationClient;  // 위치 클라이언트 추가

    public CommunityPresenter(CommunityContract.View view, Context context) {
        this.view = view;
        this.retrofitClient = new RetrofitClient();
        this.retrofitClient.initRetrofit();
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context); // 위치 클라이언트 초기화
    }

    public void getGuardianPosts(String type, long userId) {
        retrofitClient.getGuardianPosts(type, userId, new ResultCallback<Dto<List<PostDto>>>() {
            @Override
            public void onSuccess(Dto<List<PostDto>> result) {
                List<PostDto> posts = result.getData();

                // 각 포스트의 createAt 필드를 예쁘게 포맷
                for (PostDto post : posts) {
                    post.setCreateAt(formatDate(post.getCreateAt()));
                }

                view.onPostsLoaded(posts);  // 포맷팅된 데이터를 뷰로 전달
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onSetDistrictFailure(result);  // 실패 처리
                Log.e(TAG, "게시물 조회 실패: " + result, t);
            }
        });
    }

    // 날짜 포맷팅 함수
    private String formatDate(String rawDate) {
        if (rawDate == null || rawDate.isEmpty()) {
            return "날짜 없음";
        }

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date date = inputFormat.parse(rawDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "잘못된 날짜 형식";
        }
    }

    public void createPost(String title, String content, long userId) {
        String type = (userId == User.getInstance().getId()) ? "user" : "guardian";
        WritePostDto dto = new WritePostDto(title, content, userId);
        retrofitClient.postCreatePost(type, dto, new ResultCallback<Dto<Void>>(){
            @Override
            public void onSuccess(Dto<Void> result) {
                view.onPostCreated(result.getResponseMessage());
            }
            @Override
            public void onFailure(String result, Throwable t) {
                view.onPostCreateFailure(result);
            }
        });
    }

    // 현재 좌표를 받아와서 GpsDto로 변환하고 서버에 전송
    public void setMyDistrict(String type) {
        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // User 타입일 때 지역 설정이 안 되어 있으면 설정
                    if (type.equals("user") && User.getInstance().getDistrict() == null) {
                        long userId = User.getInstance().getId();
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        String coordinate = longitude + ", " + latitude;  // 좌표를 문자열로 변환

                        GpsDto gps = new GpsDto(coordinate, userId);  // 좌표로 GpsDto 생성

                        // retrofitClient를 사용하여 setMyDistrict 호출
                        retrofitClient.setMyDistrict(type, gps, new ResultCallback<Dto<DistrictDto>>() {
                            @Override
                            public void onSuccess(Dto<DistrictDto> result) {
                                view.onSetDistrictSuccess(result.getData().getDistrict());
                            }

                            @Override
                            public void onFailure(String result, Throwable t) {
                                view.onSetDistrictFailure(result);
                            }
                        });
                    }
                    // Guardian 타입일 때 지역 설정이 안 되어 있으면 설정
                    else if (type.equals("guardian") && Guardian.getInstance().getDistrict() == null) {
                        long userId = Guardian.getInstance().getId();
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        String coordinate = longitude + ", " + latitude;  // 좌표를 문자열로 변환

                        GpsDto gps = new GpsDto(coordinate, userId);  // 좌표로 GpsDto 생성

                        // retrofitClient를 사용하여 setMyDistrict 호출
                        retrofitClient.setMyDistrict(type, gps, new ResultCallback<Dto<DistrictDto>>() {
                            @Override
                            public void onSuccess(Dto<DistrictDto> result) {
                                view.onSetDistrictSuccess(result.getData().getDistrict());
                            }

                            @Override
                            public void onFailure(String result, Throwable t) {
                                view.onSetDistrictFailure(result);
                            }
                        });
                    }
                    // 지역 설정이 이미 되어 있는 경우 바로 게시물 목록 호출
                    else {
                        long userId = (type.equals("user")) ? User.getInstance().getId() : Guardian.getInstance().getId();
                        getGuardianPosts(type, userId);  // 게시물 목록 가져오기
                    }
                } else {
                    view.onSetDistrictFailure("Failed to get location");
                }
            }
        });
    }


    // 서버에서 게시물 상세 정보를 가져오는 메서드
    public void getPostDetail(long postId, String type, long userId) {
        Log.d(TAG, "Requesting post details with postId: " + postId + ", type: " + type + ", userId: " + userId);  // 로그로 값 확인
        retrofitClient.getPostDetail(postId, type, userId, new ResultCallback<Dto<PostDetailDto>>() {
            @Override
            public void onSuccess(Dto<PostDetailDto> result) {
                if (view != null) {
                    view.onPostDetailLoaded(result.getData());
                }
            }

            @Override
            public void onFailure(String result, Throwable t) {
                if (view != null) {
                    view.onPostCreateFailure(result);
                }
            }
        });
    }

    public void getComments(long postId, long userId, String type) {
        retrofitClient.getPostComments(postId, type, userId, new ResultCallback<Dto<List<CommentDto>>>() {
            @Override
            public void onSuccess(Dto<List<CommentDto>> result) {
                view.onCommentsLoaded(result.getData());
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onSetDistrictFailure(result);
            }
        });
    }

    public void setComment(long postId, String content, String type, long userId) {
        SendCommentDto commentDto = new SendCommentDto(userId, content);
        retrofitClient.postCreateComment(postId, type, userId, commentDto, new ResultCallback<Dto<Void>>() {
            @Override
            public void onSuccess(Dto<Void> result) {
                view.onPostCreated(result.getResponseMessage());
            }
            @Override
            public void onFailure(String result, Throwable t) {
                view.onPostCreateFailure(result);
            }
        });
    }
}
