package com.example.musubi.presenter.implementation;

import android.util.Log;

import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.LocationDto;
import com.example.musubi.model.entity.Guardian;
import com.example.musubi.model.entity.User;
import com.example.musubi.model.remote.RetrofitClient;
import com.example.musubi.presenter.contract.currentLocationContract;
import com.example.musubi.util.callback.ResultCallback;

public class CurrentLocationPresenter {
    private final currentLocationContract.View view;
    private final RetrofitClient retrofitClient;

    public CurrentLocationPresenter(currentLocationContract.View view) {
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
        Log.d("CurrentLocationPresenter", "putCurrentLocation called with userId: " + userId);
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
}
