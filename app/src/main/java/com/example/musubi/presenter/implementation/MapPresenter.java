package com.example.musubi.presenter.implementation;

import android.nfc.Tag;
import android.util.Log;

import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.GpsDto;
import com.example.musubi.model.entity.User;
import com.example.musubi.model.http.RetrofitClient;
import com.example.musubi.model.http.callback.ResultCallback;
import com.example.musubi.presenter.contract.MapContract;
import com.google.android.material.tabs.TabLayout;

public class MapPresenter implements MapContract.Presenter {

    private final MapContract.View view;
    private final RetrofitClient retrofitClient;

    public MapPresenter(MapContract.View view) {
        this.view = view;
        this.retrofitClient = new RetrofitClient();
        this.retrofitClient.initRetrofit();
    }

    @Override
    public void requestLocationPermissions() {
        view.showLocationPermissionRequest();
    }

    @Override
    public void onLocationPermissionsResult(boolean granted) {
        if (granted) {
            // 위치 권한이 허용된 경우 현재 위치를 표시합니다.
            // 여기서 현재 위치의 좌표를 가져오는 로직을 구현해야 합니다.
            double latitude = 37.5665; // 임의의 위도 값
            double longitude = 126.9780; // 임의의 경도 값
            view.showCurrentLocation(latitude, longitude);
        } else {
            // 위치 권한이 거부된 경우
            view.showLocationPermissionDenied();
        }
    }

    @Override
    public void createDistrict(double latitude, double longitude) {
        String gps = longitude + ", " + latitude;
        GpsDto dto = new GpsDto(gps,User.getInstance().getId());
        retrofitClient.setMyDistrict(dto, new ResultCallback<Dto<String>>() {
            @Override
            public void onSuccess(Dto<String> result) {
                User.getInstance().setDistrict(result.getData());
            }

            @Override
            public void onFailure(String result, Throwable t) {
                Log.e("createDistrict", "createDistrict ERROR");
            }
        });
    }
}
