package com.example.musubi.model.remote;

import com.example.musubi.model.dto.CallDto;
import com.example.musubi.model.dto.GpsDto;
import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.GuardianDto;
import com.example.musubi.model.dto.MsgDto;
import com.example.musubi.model.dto.UserDto;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitURL {
    @POST("api/v1/users/signup")
    Call<MsgDto> userSignup(@Body UserDto user);
    @POST("api/v1/guardians/signup")
    Call<MsgDto> guardianSignup(@Body GuardianDto guardian);
    @POST("api/v1/users/login")
    Call<Dto<UserDto>> userLogin(@Body Map<String, String> loginData);
    @POST("api/v1/guardians/login")
    Call<Dto<GuardianDto>> guardianLogin(@Body Map<String, String> loginData);
    @POST("api/v1/location")
    Call<Dto<String>> setMyDistrict(@Body GpsDto gpsDto);
    @POST("api/v1/guardians/calling")
    Call<Dto<Void>> guardianCallWithMessage(@Body CallDto callDto);
}