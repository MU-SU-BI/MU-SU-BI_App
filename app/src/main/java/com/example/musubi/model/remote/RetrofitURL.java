package com.example.musubi.model.remote;

import com.example.musubi.model.dto.CallDto;
import com.example.musubi.model.dto.GpsDto;
import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.GuardianDto;
import com.example.musubi.model.dto.LocationDto;
import com.example.musubi.model.dto.UserConnectDto;
import com.example.musubi.model.dto.UserDto;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Path;

public interface RetrofitURL {
    @POST("api/v1/users/signup")
    Call<Dto<Void>> userSignup(@Body UserDto user);
    @POST("api/v1/guardians/signup")
    Call<Dto<Void>> guardianSignup(@Body GuardianDto guardian);
    @POST("api/v1/users/login")
    Call<Dto<UserDto>> userLogin(@Body Map<String, String> loginData);
    @POST("api/v1/guardians/login")
    Call<Dto<GuardianDto>> guardianLogin(@Body Map<String, String> loginData);
    @POST("api/v1/location")
    Call<Dto<String>> setMyDistrict(@Body GpsDto gpsDto);
    @POST("api/v1/guardians/connection")
    Call<Dto<Void>> connectGuardian(@Body UserConnectDto userDto);
    @POST("api/v1/guardians/help")
    Call<Dto<Void>> guardianCallWithMessage(@Body CallDto callDto);
    @PUT("api/v1/current-location")
    Call<Dto<Void>> currentLocation(@Query("type") String type, @Body LocationDto locationDto);
    @GET("api/v1/guardians/{id}/user")
    Call<Dto<UserDto>> findMyUser(@Path("id") long id);
    @GET("api/v1/users/{id}/guardian")
    Call<Dto<GuardianDto>> findMyGuardian(@Path("id") long id);
}