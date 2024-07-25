package com.example.musubi.model.http;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.musubi.model.dto.GpsDto;
import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.MsgDto;
import com.example.musubi.model.dto.UserDto;
import com.example.musubi.model.entity.User;
import com.example.musubi.model.http.callback.ResultCallback;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private RetrofitURL retrofitService;

    public void initRetrofit() {
        final String BASEURL = "http://43.202.1.81/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitURL.class);
    }

    public void postSignupUser(UserDto user, ResultCallback<String> resultCallback){
        Call<MsgDto> call = retrofitService.userSignup(user);

        call.enqueue((new Callback<MsgDto>() {
            @Override
            public void onResponse(@NonNull Call<MsgDto> call, @NonNull Response<MsgDto> response) {
                assert response.body() != null;
                String responseMessage = response.body().getResponseMessage();

                if (response.isSuccessful())
                    resultCallback.onSuccess(responseMessage);
                else
                    resultCallback.onFailure(responseMessage, new Exception("status code is not 201"));
            }

            @Override
            public void onFailure(@NonNull Call<MsgDto> call, @NonNull Throwable t) {
                resultCallback.onFailure("NETWORK_ERROR", t);
            }
        }));
    }

    public void postLoginUser(Map<String, String> loginData, ResultCallback<Dto<UserDto>> resultCallback){
        Call<Dto<UserDto>> call = retrofitService.userLogin(loginData);

        call.enqueue(new Callback<Dto<UserDto>>() {
            @Override
            public void onResponse(@NonNull Call<Dto<UserDto>> call, @NonNull Response<Dto<UserDto>> response) {
                assert response.body() != null;

                if (response.isSuccessful())
                    resultCallback.onSuccess(response.body());
                else
                    resultCallback.onFailure("FUCK", new Exception("status code is not 200"));
            }

            @Override
            public void onFailure(@NonNull Call<Dto<UserDto>> call, @NonNull Throwable t) {
                resultCallback.onFailure("NETWORK_ERROR", t);
            }
        });
    };

//    public void postCoordinateUser(String coordinate, ResultCallback<UserDto> resultCallback){
//        Call<GpsDto> call = retrofitService.setCoordinate(coordinate);
//
//        call.enqueue(new Callback<GpsDto>() {
//            @Override
//            public void onResponse(Call<GpsDto> call, Response<GpsDto> response) {
//                assert response.body() != null;
//
//                if (response.isSuccessful()) {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GpsDto> call, Throwable t) {
//
//            }
//        });
//    }
    }
}
