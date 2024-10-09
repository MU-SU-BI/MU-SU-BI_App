package com.example.musubi.model.remote;

import com.example.musubi.model.dto.CallDto;
import com.example.musubi.model.dto.CommentDto;
import com.example.musubi.model.dto.DistrictDto;
import com.example.musubi.model.dto.GpsDto;
import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.GuardianDto;
import com.example.musubi.model.dto.LocationDto;
import com.example.musubi.model.dto.MyUserDto;
import com.example.musubi.model.dto.PostDetailDto;
import com.example.musubi.model.dto.PostDto;
import com.example.musubi.model.dto.SafeAreaDto;
import com.example.musubi.model.dto.SosRequestDto;
import com.example.musubi.model.dto.SendCommentDto;
import com.example.musubi.model.dto.SosUserInfoDto;
import com.example.musubi.model.dto.UserConnectDto;
import com.example.musubi.model.dto.UserDto;
import com.example.musubi.model.dto.UserProfileDto;
import com.example.musubi.model.dto.WritePostDto;

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
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    Call<Dto<DistrictDto>> setMyDistrict(@Query("type") String type, @Body GpsDto gpsDto);
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
    Call<Dto<UserProfileDto>> postMyUserImage(@Part("userId") RequestBody idPart, @Part MultipartBody.Part image);
    @POST("api/v1/guardians/sos")
    Call<Dto<Void>> postSosRequestToCommunity(@Body SosRequestDto sosDto);
    @GET("api/v1/users/{userId}/user")
    Call<Dto<SosUserInfoDto>> getSosUserInfo(@Path("userId") long userId);
    @GET("api/v1/posts")
    Call<Dto<List<PostDto>>> getGuardianPosts(@Query("type") String type, @Query("userId") long userId);
    @POST("api/v1/posts")
    Call<Dto<Void>> createPost(@Query("type") String type, @Body WritePostDto writePostDto);
    @GET("api/v1/posts/{postId}")
    Call<Dto<PostDetailDto>> getPostDetail(@Path("postId") long postId, @Query("type") String type, @Query("userId") long userId);
    @GET("api/v1/posts/{postId}/comments")
    Call<Dto<List<CommentDto>>> getComments(@Path("postId") long postId, @Query("userId") long userId, @Query("type") String type);
    @POST("api/v1/posts/{postId}/comments")
    Call<Dto<Void>> createComment(@Path("postId") long postId, @Query("type") String type, @Query("userId") long userId, @Body SendCommentDto sendCommentDto);
}