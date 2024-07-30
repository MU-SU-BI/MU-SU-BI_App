package com.example.musubi.presenter.implementation;

import android.content.Context;

import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.UserConnectDto;
import com.example.musubi.model.entity.Guardian;
import com.example.musubi.model.local.SPFManager;
import com.example.musubi.model.remote.RetrofitClient;
import com.example.musubi.util.callback.ResultCallback;
import com.example.musubi.presenter.contract.GuardianMyPageContract;

public class GuardianMyPagePresenter implements GuardianMyPageContract.Presenter {
    private final GuardianMyPageContract.View view;
    private final RetrofitClient retrofitClient;
    private final SPFManager spfManager;

    public GuardianMyPagePresenter(GuardianMyPageContract.View view, Context context) {
        this.view = view;
        this.retrofitClient = new RetrofitClient();
        this.retrofitClient.initRetrofit();
        spfManager = new SPFManager(context, "ACCOUNT");
    }

    @Override
    public void logoutGuardian() {
        Guardian.getInstance().initGuardian(null, null);
        spfManager.getEditor().remove("EMAIL").apply();
        spfManager.getEditor().remove("PASSWORD").apply();
        spfManager.getEditor().remove("USER_TYPE").apply();
        view.onLogoutSuccess();
    }

    @Override
    public void connectUser(String userName, String phoneNumber) {
        retrofitClient.postConnectUserWithGuardian(new UserConnectDto(Guardian.getInstance().getId(), userName, phoneNumber), new ResultCallback<Dto<Void>>() {
            @Override
            public void onSuccess(Dto<Void> result) {
                view.onConnectSuccess(result.getResponseMessage());
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onConnectFailure(result);
            }
        });
    }
}
