package com.example.musubi.presenter.implementation;

import com.example.musubi.model.dto.CallDto;
import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.remote.RetrofitClient;
import com.example.musubi.model.remote.callback.ResultCallback;
import com.example.musubi.presenter.contract.CallContract;

public class CallPresenter implements CallContract.Presenter {
    private final CallContract.View view;
    private final RetrofitClient retrofitClient;

    public CallPresenter(CallContract.View view) {
        this.view = view;
        this.retrofitClient = new RetrofitClient();
        this.retrofitClient.initRetrofit();
    }

    @Override
    public void callGuardian(long userId, String callMessage) {
        CallDto callDto = new CallDto(userId, callMessage);
        retrofitClient.postCallGuardianWithMessage(callDto, new ResultCallback<Dto<Void>>() {
            @Override
            public void onSuccess(Dto<Void> result) {
                view.onCallSuccess(result.getResponseMessage());
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onCallFailure(result);
            }
        });
    }
}
