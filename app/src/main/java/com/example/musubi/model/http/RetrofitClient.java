package com.example.musubi.model.http;

import androidx.annotation.NonNull;

import com.example.musubi.model.dto.MsgDto;
import com.example.musubi.model.dto.UserDto;
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
}
