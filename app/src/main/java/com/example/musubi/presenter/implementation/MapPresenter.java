package com.example.musubi.presenter.implementation;

import android.util.Log;

import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.GpsDto;
import com.example.musubi.model.entity.User;
import com.example.musubi.model.remote.RetrofitClient;
import com.example.musubi.util.callback.ResultCallback;
import com.example.musubi.presenter.contract.MapContract;

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
        GpsDto dto = new GpsDto(gps,User.getInstance().getId());

        retrofitClient.setMyDistrict(dto, new ResultCallback<Dto<String>>() {
            @Override
            public void onSuccess(Dto<String> result) {
                User.getInstance().setDistrict(result.getData());
            }

            @Override
            public void onFailure(String result, Throwable t) {}
        });
    }
}
