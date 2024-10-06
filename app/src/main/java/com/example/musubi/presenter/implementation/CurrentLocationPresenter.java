package com.example.musubi.presenter.implementation;

import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.LocationDto;
import com.example.musubi.model.dto.MyUserDto;
import com.example.musubi.model.entity.Guardian;
import com.example.musubi.model.entity.User;
import com.example.musubi.model.remote.RetrofitClient;
import com.example.musubi.presenter.contract.CurrentLocationContract;
import com.example.musubi.util.callback.ResultCallback;

public class CurrentLocationPresenter {
    private final CurrentLocationContract.View view;
    private final RetrofitClient retrofitClient;

    public CurrentLocationPresenter(CurrentLocationContract.View view) {
        this.view = view;
        this.retrofitClient = new RetrofitClient();
        this.retrofitClient.initRetrofit();
    }

    public void putCurrentLocation(String type, double latitude, double longitude) {
        long userId = -1;
        if (type.equals("user"))
            userId = User.getInstance().getId();
        else
            userId = Guardian.getInstance().getId();
        retrofitClient.putCurrentLocation(type, new LocationDto(userId, latitude, longitude), new ResultCallback<Dto<Void>>() {
            @Override
            public void onSuccess(Dto<Void> result) {
                view.onCurrentLocationSuccess(result.getResponseMessage());
            }
            @Override
            public void onFailure(String result, Throwable t) {
                view.onCurrentLocationFailure(result);
            }
        });
    }
    public void getFindMyUserLocation() {
        // Guardian의 user 객체가 null인지 먼저 확인
        if (Guardian.getInstance().getUser() == null) {
            // user가 null인 경우 처리 (필요한 경우 로그를 남기거나 오류를 사용자에게 알릴 수 있음)
            view.onCurrentLocationFailure("User information is not initialized.");
            return;
        }

        // user가 null이 아닌 경우에만 위치 조회 진행
        retrofitClient.getFindMyUserLocation(Guardian.getInstance().getId(), new ResultCallback<Dto<MyUserDto>>() {
            @Override
            public void onSuccess(Dto<MyUserDto> result) {
                Guardian.getInstance().getUser().setLatitude(result.getData().getLatitude());
                Guardian.getInstance().getUser().setLongitude(result.getData().getLongitude());
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onCurrentLocationFailure(result);
            }
        });
    }

}
