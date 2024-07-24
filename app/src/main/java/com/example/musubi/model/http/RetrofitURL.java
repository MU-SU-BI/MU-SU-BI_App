package com.example.musubi.model.http;

import com.example.musubi.model.dto.MsgDto;
import com.example.musubi.model.dto.UserDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitURL {
    @POST("api/v1/users/signup")
    Call<MsgDto> userSignup(@Body UserDto user);
}