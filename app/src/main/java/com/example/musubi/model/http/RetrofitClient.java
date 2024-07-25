package com.example.musubi.model.http;

import androidx.annotation.NonNull;

import com.example.musubi.model.dto.GpsDto;
import com.example.musubi.model.dto.MsgDto;
import com.example.musubi.model.dto.UserDto;
import com.example.musubi.model.entity.User;
import com.example.musubi.model.http.callback.ResultCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private RetrofitURL retrofitService;

    public void initRetrofit() {
        final String BASEURL = "https://9a7793e7-8dfa-4fa8-b1f7-406f60dfd051.mock.pstmn.io/";
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

    public void postLoginUser(String email,  String password, ResultCallback<UserDto> resultCallback){
        Call<UserDto> call = retrofitService.userLogin(email, password);

        call.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(@NonNull Call<UserDto> call, @NonNull Response<UserDto> response) {
                assert response.body() != null;

                if (response.isSuccessful()) {
                    UserDto user = response.body();
                    resultCallback.onSuccess(user);
                }
                else
                    resultCallback.onFailure("FUCK", new Exception("status code is not 200"));
            }

            @Override
            public void onFailure(@NonNull Call<UserDto> call, @NonNull Throwable t) {
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
