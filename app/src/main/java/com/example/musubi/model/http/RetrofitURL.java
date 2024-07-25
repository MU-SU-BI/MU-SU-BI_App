package com.example.musubi.model.http;

import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.MsgDto;
import com.example.musubi.model.dto.UserDto;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitURL {
    @POST("api/v1/users/signup")
    Call<MsgDto> userSignup(@Body UserDto user);
    @FormUrlEncoded
    @POST("api/v1/users/login")
    Call<Dto<UserDto>> userLogin(Map<String, String> loginData);
}