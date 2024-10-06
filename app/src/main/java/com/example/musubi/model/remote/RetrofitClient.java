package com.example.musubi.model.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.musubi.model.dto.CallDto;
import com.example.musubi.model.dto.GpsDto;
import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.GuardianDto;
import com.example.musubi.model.dto.LocationDto;
import com.example.musubi.model.dto.MyUserDto;
import com.example.musubi.model.dto.SafeAreaDto;
import com.example.musubi.model.dto.SosDto;
import com.example.musubi.model.dto.UserConnectDto;
import com.example.musubi.model.dto.UserDto;
import com.example.musubi.util.callback.ResultCallback;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private RetrofitURL retrofitService;

    public void initRetrofit() {
        final String BASEURL = "http://43.202.1.81/";                                      // https://9a7793e7-8dfa-4fa8-b1f7-406f60dfd051.mock.pstmn.io/
        Retrofit retrofit = new Retrofit.Builder()                      // http://43.202.1.81/
                .baseUrl(BASEURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitURL.class);
    }

    public void postSignupUser(UserDto user, ResultCallback<String> resultCallback) {
        Call<Dto<Void>> call = retrofitService.userSignup(user);

        call.enqueue((new Callback<Dto<Void>>() {
            @Override
            public void onResponse(@NonNull Call<Dto<Void>> call, @NonNull Response<Dto<Void>> response) {
                assert response.body() != null;
                String responseMessage = response.body().getResponseMessage();

                if (response.isSuccessful() && response.code() == 201)
                    resultCallback.onSuccess(responseMessage);
                else
                    resultCallback.onFailure("이메일 또는 휴대폰 번호가 중복입니다.\n다시 시도해주세요.", new Exception("status code is not 201"));
            }

            @Override
            public void onFailure(@NonNull Call<Dto<Void>> call, @NonNull Throwable t) {
                resultCallback.onFailure("NETWORK_ERROR", t);
            }
        }));
    }

    public void postSignupGuardian(GuardianDto guardian, ResultCallback<String> resultCallback) {
        Call<Dto<Void>> call = retrofitService.guardianSignup(guardian);

        call.enqueue((new Callback<Dto<Void>>() {
            @Override
            public void onResponse(@NonNull Call<Dto<Void>> call, @NonNull Response<Dto<Void>> response) {
                if (response.isSuccessful() && response.code() == 201) {
                    assert response.body() != null;
                    resultCallback.onSuccess(response.body().getResponseMessage());
                } else
                    resultCallback.onFailure("이메일 또는 휴대폰 번호가 중복입니다.\n다시 시도해주세요.", new Exception("status code is not 201"));
            }

            @Override
            public void onFailure(@NonNull Call<Dto<Void>> call, @NonNull Throwable t) {
                resultCallback.onFailure("NETWORK_ERROR", t);
            }
        }));
    }


    public void postLoginUser(Map<String, String> loginData, ResultCallback<Dto<UserDto>> resultCallback) {
        Call<Dto<UserDto>> call = retrofitService.userLogin(loginData);

        call.enqueue(new Callback<Dto<UserDto>>() {
            @Override
            public void onResponse(@NonNull Call<Dto<UserDto>> call, @NonNull Response<Dto<UserDto>> response) {
                if (response.isSuccessful() && response.code() == 200)
                    resultCallback.onSuccess(response.body());
                else
                    resultCallback.onFailure("이메일 또는 비밀번호가 틀립니다.", new Exception("status code is not 200"));
            }

            @Override
            public void onFailure(@NonNull Call<Dto<UserDto>> call, @NonNull Throwable t) {
                resultCallback.onFailure("NETWORK_ERROR", t);
            }
        });
    }


    public void postLoginGuardian(Map<String, String> loginData, ResultCallback<Dto<GuardianDto>> resultCallback) {
        Call<Dto<GuardianDto>> call = retrofitService.guardianLogin(loginData);

        call.enqueue(new Callback<Dto<GuardianDto>>() {
            @Override
            public void onResponse(@NonNull Call<Dto<GuardianDto>> call, @NonNull Response<Dto<GuardianDto>> response) {
                if (response.isSuccessful() && response.code() == 200)
                    resultCallback.onSuccess(response.body());
                else
                    resultCallback.onFailure("이메일 또는 비밀번호가 틀립니다.", new Exception("status code is not 200"));
            }

            @Override
            public void onFailure(@NonNull Call<Dto<GuardianDto>> call, @NonNull Throwable t) {
                resultCallback.onFailure("NETWORK_ERROR", t);
            }
        });
    }

    ;

    public void setMyDistrict(GpsDto gps, ResultCallback<Dto<String>> resultCallback) {
        Call<Dto<String>> call = retrofitService.setMyDistrict(gps);

        call.enqueue(new Callback<Dto<String>>() {
            @Override
            public void onResponse(Call<Dto<String>> call, Response<Dto<String>> response) {
                assert response.body() != null;

                if (response.isSuccessful()) {
                    resultCallback.onSuccess(response.body());
                } else
                    resultCallback.onFailure(response.body().getResponseMessage(), new Exception("status code in not 200"));
            }

            @Override
            public void onFailure(Call<Dto<String>> call, Throwable t) {
                resultCallback.onFailure("NETWORK_ERROR", t);
            }
        });
    }

    public void postConnectUserWithGuardian(UserConnectDto userDto, ResultCallback<Dto<Void>> resultCallback) {
        Call<Dto<Void>> call = retrofitService.connectGuardian(userDto);

        call.enqueue(new Callback<Dto<Void>>() {
            @Override
            public void onResponse(Call<Dto<Void>> call, Response<Dto<Void>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    resultCallback.onSuccess(response.body());
                } else if (response.body() != null) {
                    resultCallback.onFailure(response.body().getResponseMessage(), new Exception("status code in not 200"));
                } else {
                    resultCallback.onFailure("Response body is null", new Exception("Response body is null"));
                }
            }

            @Override
            public void onFailure(Call<Dto<Void>> call, Throwable t) {
                resultCallback.onFailure("NETWORK_ERROR", t);
            }
        });
    }

    public void postCallGuardianWithMessage(CallDto callDto, ResultCallback<Dto<Void>> resultCallback) {
        Call<Dto<Void>> call = retrofitService.guardianCallWithMessage(callDto);

        call.enqueue(new Callback<Dto<Void>>() {
            @Override
            public void onResponse(@NonNull Call<Dto<Void>> call, @NonNull Response<Dto<Void>> response) {
                if (response.isSuccessful() && response.code() == 201)
                    resultCallback.onSuccess(response.body());
                else
                    resultCallback.onFailure("보호자 호출에 실패했습니다.", new Exception("status code is not 200"));
            }

            @Override
            public void onFailure(@NonNull Call<Dto<Void>> call, @NonNull Throwable t) {
                resultCallback.onFailure(t.getMessage(), t);
            }
        });
    }

    public void putCurrentLocation(String type, LocationDto locationDto, ResultCallback<Dto<Void>> resultCallback) {
        Call<Dto<Void>> call = retrofitService.currentLocation(type, locationDto);

        call.enqueue(new Callback<Dto<Void>>() {
            @Override
            public void onResponse(Call<Dto<Void>> call, Response<Dto<Void>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    resultCallback.onSuccess(response.body());
                } else
                    resultCallback.onFailure("위치 정보 업데이트에 실패했습니다.", new Exception("status code is not 200"));
            }

            @Override
            public void onFailure(Call<Dto<Void>> call, Throwable t) {
                resultCallback.onFailure(t.getMessage(), t);
            }
        });
    }

    public void getFindMyUser(long guardianId, ResultCallback<Dto<UserDto>> resultCallback) {
        Call<Dto<UserDto>> call = retrofitService.findMyUser(guardianId);

        call.enqueue(new Callback<Dto<UserDto>>() {
            @Override
            public void onResponse(@NonNull Call<Dto<UserDto>> call, @NonNull Response<Dto<UserDto>> response) {
                if (response.isSuccessful() && response.code() == 200)
                    resultCallback.onSuccess(response.body());
                else
                    resultCallback.onFailure("나의 사용자 조회에 실패했습니다.", new Exception("status code is not 200"));
            }

            @Override
            public void onFailure(@NonNull Call<Dto<UserDto>> call, @NonNull Throwable t) {
                resultCallback.onFailure(t.getMessage(), t);
            }
        });
    }

    public void getFindMyGuardian(long userId, ResultCallback<Dto<GuardianDto>> resultCallback) {
        Call<Dto<GuardianDto>> call = retrofitService.findMyGuardian(userId);

        call.enqueue(new Callback<Dto<GuardianDto>>() {
            @Override
            public void onResponse(@NonNull Call<Dto<GuardianDto>> call, @NonNull Response<Dto<GuardianDto>> response) {
                if (response.isSuccessful() && response.code() == 200)
                    resultCallback.onSuccess(response.body());
                else
                    resultCallback.onFailure("나의 보호자 조회에 실패했습니다.", new Exception("status code is not 200"));
            }

            @Override
            public void onFailure(@NonNull Call<Dto<GuardianDto>> call, @NonNull Throwable t) {
                resultCallback.onFailure(t.getMessage(), t);
            }
        });
    }

    public void getFindMyUserLocation(long userId, ResultCallback<Dto<MyUserDto>> resultCallback) {
        Call<Dto<MyUserDto>> call = retrofitService.findMyUserLocation(userId);

        call.enqueue(new Callback<Dto<MyUserDto>>() {
            @Override
            public void onResponse(Call<Dto<MyUserDto>> call, Response<Dto<MyUserDto>> response) {
                if (response.isSuccessful() && response.code() == 200)
                    resultCallback.onSuccess(response.body());
                else
                    resultCallback.onFailure("나의 위치 조회에 실패했습니다.", new Exception("status code is not 200"));
            }

            @Override
            public void onFailure(Call<Dto<MyUserDto>> call, Throwable t) {
                resultCallback.onFailure(t.getMessage(), t);
            }
        });
    }

    public void setSafeZone(SafeAreaDto safeAreaDto, ResultCallback<Dto<Void>> resultCallback) {
        Call<Dto<Void>> call = retrofitService.setUserSafeZone(safeAreaDto);

        call.enqueue(new Callback<Dto<Void>>() {
            @Override
            public void onResponse(Call<Dto<Void>> call, Response<Dto<Void>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    resultCallback.onSuccess(response.body());
                } else {
                    Log.e("SafeZone", "Error: " + response.code() + " - " + response.body().getResponseMessage());
                    resultCallback.onFailure("위험 지역 설정에 실패했습니다.", new Exception("status code is not 200"));
                }
            }

            @Override
            public void onFailure(Call<Dto<Void>> call, Throwable t) {
                resultCallback.onFailure(t.getMessage(), t);
            }
        });
    }

    public void getSafeZones(long guardianId, ResultCallback<Dto<List<SafeAreaDto>>> resultCallback) {
        Call<Dto<List<SafeAreaDto>>> call = retrofitService.setSafeZones(guardianId);

        call.enqueue(new Callback<Dto<List<SafeAreaDto>>>() {
            @Override
            public void onResponse(Call<Dto<List<SafeAreaDto>>> call, Response<Dto<List<SafeAreaDto>>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    resultCallback.onSuccess(response.body()); // List<SafeAreaDto>를 포함한 Dto 객체 전달
                } else {
                    resultCallback.onFailure("안전 지역 조회에 실패했습니다.", new Exception("status code is not 200"));
                }
            }

            @Override
            public void onFailure(Call<Dto<List<SafeAreaDto>>> call, Throwable t) {
                resultCallback.onFailure(t.getMessage(), t);
            }
        });
    }

    public void postMyUserImage(RequestBody idPart, MultipartBody.Part imagePart, ResultCallback<Dto<Void>> resultCallback) {
        Call<Dto<Void>> call = retrofitService.postMyUserImage(idPart, imagePart);

        call.enqueue(new Callback<Dto<Void>>() {
            @Override
            public void onResponse(Call<Dto<Void>> call, Response<Dto<Void>> response) {
                if (response.isSuccessful() && response.code() == 200)
                    resultCallback.onSuccess(response.body()); // List<SafeAreaDto>를 포함한 Dto 객체 전달
                else
                    resultCallback.onFailure("나의 사용자 이미지 업로드에 실패했습니다.", new Exception("status code is not 200"));

            }

            @Override
            public void onFailure(Call<Dto<Void>> call, Throwable t) {
                Log.e("ImageUpload", "Error:" + t.getMessage());
                resultCallback.onFailure(t.getMessage(), t);
            }
        });
    }

    public void postSosCall(SosDto sosDto, ResultCallback<Dto<Void>> resultCallback) {
        Call<Dto<Void>> call = retrofitService.requestSosToCommunity(sosDto);

        call.enqueue(new Callback<Dto<Void>>() {
            @Override
            public void onResponse(Call<Dto<Void>> call, Response<Dto<Void>> response) {
                if (response.isSuccessful() && response.code() == 200)
                    resultCallback.onSuccess(response.body()); // List<SafeAreaDto>를 포함한 Dto 객체 전달
                else
                    resultCallback.onFailure("커뮤니티 SoS 요청에 실패했습니다.", new Exception("status code is not 200"));

            }

            @Override
            public void onFailure(Call<Dto<Void>> call, Throwable t) {
                resultCallback.onFailure(t.getMessage(), t);
            }
        });
    }
}
