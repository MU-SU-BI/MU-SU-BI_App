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
    private static final String TAG = "CommunityPresenter";
    private FusedLocationProviderClient fusedLocationClient;

    public CommunityPresenter(CommunityContract.View view, Context context) {
        this.view = view;
        this.retrofitClient = new RetrofitClient();
        this.retrofitClient.initRetrofit();
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public void getGuardianPosts(String type, long userId) {
        retrofitClient.getGuardianPosts(type, userId, new ResultCallback<Dto<List<PostDto>>>() {
            @Override
            public void onSuccess(Dto<List<PostDto>> result) {
                List<PostDto> posts = result.getData();

                for (PostDto post : posts) {
                    post.setCreateAt(formatDate(post.getCreateAt()));
                }

                view.onPostsLoaded(posts);
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onSetDistrictFailure(result);
                Log.e(TAG, "게시물 조회 실패: " + result, t);
            }
        });
    }

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
        retrofitClient.postCreatePost(type, dto, new ResultCallback<Dto<Void>>() {
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

    public void setMyDistrict(String type) {
        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    if (type.equals("user") && User.getInstance().getDistrict() == null) {
                        updateDistrictAndFetchPosts(type, location);
                    } else if (type.equals("guardian") && Guardian.getInstance().getDistrict() == null) {
                        updateDistrictAndFetchPosts(type, location);
                    } else {
                        long userId = (type.equals("user")) ? User.getInstance().getId() : Guardian.getInstance().getId();
                        String district = (type.equals("user")) ? User.getInstance().getDistrict() : Guardian.getInstance().getDistrict();

                        // 지역 정보를 공백으로 분리하고 마지막 단어만 가져옴
                        String[] locationParts = district.split(" ");
                        String lastLocation = locationParts[locationParts.length - 1];

                        view.setLocationText(lastLocation);  // 마지막 단어만 표시
                        getGuardianPosts(type, userId);
                    }
                } else {
                    view.onSetDistrictFailure("Failed to get location");
                }
            }
        });
    }

    private void updateDistrictAndFetchPosts(String type, Location location) {
        long userId = (type.equals("user")) ? User.getInstance().getId() : Guardian.getInstance().getId();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        String coordinate = longitude + ", " + latitude;
        GpsDto gps = new GpsDto(coordinate, userId);

        retrofitClient.setMyDistrict(type, gps, new ResultCallback<Dto<DistrictDto>>() {
            @Override
            public void onSuccess(Dto<DistrictDto> result) {
                view.onSetDistrictSuccess(result.getData().getDistrict());

                // 지역 설정이 성공적으로 완료되었으므로 게시물 조회 시작
                getGuardianPosts(type, userId);
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onSetDistrictFailure(result);
            }
        });
    }

    public void getPostDetail(long postId, String type, long userId) {
        retrofitClient.getPostDetail(postId, type, userId, new ResultCallback<Dto<PostDetailDto>>() {
            @Override
            public void onSuccess(Dto<PostDetailDto> result) {
                view.onPostDetailLoaded(result.getData());
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onPostCreateFailure(result);
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
