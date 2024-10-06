package com.example.musubi.model.remote;

import com.example.musubi.model.dto.CallDto;
import com.example.musubi.model.dto.GpsDto;
import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.GuardianDto;
import com.example.musubi.model.dto.LocationDto;
import com.example.musubi.model.dto.MyUserDto;
import com.example.musubi.model.dto.SafeAreaDto;
import com.example.musubi.model.dto.UserConnectDto;
import com.example.musubi.model.dto.UserDto;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
    @GET("api/v1/current-location/{id}")
    Call<Dto<MyUserDto>> findMyUserLocation(@Path("id") long id);
    @POST("api/v1/safe-area")
    Call<Dto<Void>> setUserSafeZone(@Body SafeAreaDto safeAreaDto);
    @GET("api/v1/safe-area/{guardianId}")
    Call<Dto<List<SafeAreaDto>>> setSafeZones(@Path("guardianId") long guardianId);
    @Multipart
    @POST("api/v1/guardians/profile")
    Call<Dto<Void>> postMyUserImage(@Part("userId") RequestBody idPart, @Part MultipartBody.Part image);
}