package com.example.musubi.presenter.implementation;

import android.util.Log;

import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.GpsDto;
import com.example.musubi.model.dto.SafeAreaDto;
import com.example.musubi.model.entity.Guardian;
import com.example.musubi.model.entity.User;
import com.example.musubi.model.remote.RetrofitClient;
import com.example.musubi.util.callback.ResultCallback;
import com.example.musubi.presenter.contract.MapContract;

import java.util.List;

public class MapPresenter implements MapContract.Presenter {
    private final MapContract.View view;
    private final RetrofitClient retrofitClient;

    public MapPresenter(MapContract.View view) {
        this.view = view;
        this.retrofitClient = new RetrofitClient();
        this.retrofitClient.initRetrofit();
    }

    @Override
    public void createDistrict(double latitude, double longitude) {
        String gps = longitude + ", " + latitude;
        GpsDto dto = new GpsDto(gps, User.getInstance().getId());

        retrofitClient.setMyDistrict(dto, new ResultCallback<Dto<String>>() {
            @Override
            public void onSuccess(Dto<String> result) {
                User.getInstance().setDistrict(result.getData());
            }

            @Override
            public void onFailure(String result, Throwable t) {}
        });
    }

    @Override
    public void setMyUserSafeArea(List<SafeAreaDto> safeAreas) {
        for (SafeAreaDto safeArea : safeAreas) {
            retrofitClient.setSafeZone(Guardian.getInstance().getId(), safeArea, new ResultCallback<Dto<Void>>() {
                @Override
                public void onSuccess(Dto<Void> result) {
                    Log.d("SafeZone", "SafeArea sent successfully to server.");
                }

                @Override
                public void onFailure(String result, Throwable t) {
                    Log.e("SafeZone", "Failed to send SafeArea to server: " + result, t);
                }
            });
        }
    }

    @Override
    public void getMyUserSafeArea(long userId) {
        retrofitClient.getSafeZones(userId, new ResultCallback<Dto<List<SafeAreaDto>>>() {
            @Override
            public void onSuccess(Dto<List<SafeAreaDto>> result) {
                List<SafeAreaDto> safeAreas = result.getData(); // 안전구역 리스트
                if (safeAreas != null) {
                    for (SafeAreaDto safeArea : safeAreas) {
                        view.addSafeZone(safeArea); // View에 안전구역 추가
                    }
                }
            }

            @Override
            public void onFailure(String result, Throwable t) {
                Log.e("SafeZone", "Failed to retrieve SafeAreas: " + result, t);
            }
        });
    }

}
