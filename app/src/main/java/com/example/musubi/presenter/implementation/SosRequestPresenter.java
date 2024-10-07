package com.example.musubi.presenter.implementation;

import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.SosUserInfoDto;
import com.example.musubi.model.remote.RetrofitClient;
import com.example.musubi.presenter.contract.SosRequestContract;
import com.example.musubi.util.callback.ResultCallback;

public class SosRequestPresenter implements SosRequestContract.Presenter {
    private final SosRequestContract.View view;
    private final RetrofitClient retrofitClient;

    public SosRequestPresenter(SosRequestContract.View view) {
        this.view = view;
        this.retrofitClient = new RetrofitClient();
        this.retrofitClient.initRetrofit();
    }

    @Override
    public void inquirySosUserInfo(long userId) {
        retrofitClient.getSosUserInfo(userId, new ResultCallback<Dto<SosUserInfoDto>>() {
            @Override
            public void onSuccess(Dto<SosUserInfoDto> result) {
                view.onInquirySosUserInfoSuccess(result.getData(), result.getResponseMessage());
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onInquirySosUserInfoFailure(result);
            }
        });
    }
}
