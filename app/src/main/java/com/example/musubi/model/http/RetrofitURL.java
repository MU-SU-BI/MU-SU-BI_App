package com.example.musubi.model.http;

import com.example.musubi.model.dto.GpsDto;
import com.example.musubi.model.dto.MsgDto;
import com.example.musubi.model.dto.UserDto;
import com.example.musubi.model.entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface RetrofitURL {
    @POST("api/v1/users/signup")
    Call<MsgDto> userSignup(@Body UserDto user);
    @FormUrlEncoded
    @POST("api/v1/users/login")
    Call<UserDto> userLogin(@Field("email") String email, @Field("password") String password);
//    @POST("api/v1/location")
//    Call<Dto<String>> setCoordinate(@Body GpsDto gpsDto);
}