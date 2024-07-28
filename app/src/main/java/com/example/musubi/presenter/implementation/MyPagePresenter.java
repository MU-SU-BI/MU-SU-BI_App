package com.example.musubi.presenter.implementation;

import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.UserConnectDto;
import com.example.musubi.model.entity.Guardian;
import com.example.musubi.model.remote.RetrofitClient;
import com.example.musubi.util.callback.ResultCallback;
import com.example.musubi.presenter.contract.MyPageContract;

public class MyPagePresenter implements MyPageContract.Presenter {
    private final MyPageContract.View view;
    private final RetrofitClient retrofitClient;

    public MyPagePresenter(MyPageContract.View view, RetrofitClient retrofitClient) {
        this.view = view;
        this.retrofitClient = retrofitClient;
        this.retrofitClient.initRetrofit();
    }

    @Override
    public void connectUser(String userName, String phoneNumber) {
        retrofitClient.connectUserWithGuardian(new UserConnectDto(Guardian.getInstance().getId(), userName, phoneNumber), new ResultCallback<Dto<String>>() {
            @Override
            public void onSuccess(Dto<String> result) {
                view.onConnectSuccess(result.getData());
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onConnectFailure(result);
            }
        });
    }
}
