package com.example.musubi.presenter.implementation;

import android.content.Context;

import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.GuardianDto;
import com.example.musubi.model.dto.UserDto;
import com.example.musubi.model.entity.Guardian;
import com.example.musubi.model.entity.User;
import com.example.musubi.model.local.SPFManager;
import com.example.musubi.model.remote.RetrofitClient;
import com.example.musubi.presenter.contract.MainContract;
import com.example.musubi.util.callback.ResultCallback;

import java.util.HashMap;
import java.util.Map;

public class MainPresenter implements MainContract.Presenter {
    private final MainContract.View view;
    private final RetrofitClient retrofitClient;
    private final SPFManager spfManager;

    public MainPresenter(MainContract.View view, Context context) {
        this.view = view;
        this.retrofitClient = new RetrofitClient();
        this.retrofitClient.initRetrofit();
        this.spfManager = new SPFManager(context, "ACCOUNT");
    }

    @Override
    public void autoLogin() {
        String email = spfManager.getSharedPreferences().getString("EMAIL", "");
        String password = spfManager.getSharedPreferences().getString("PASSWORD", "");
        String userType = spfManager.getSharedPreferences().getString("USER_TYPE", "");
        String fcmToken = spfManager.getSharedPreferences().getString("FCM_TOKEN", "");

        if (email.isEmpty() || password.isEmpty() || fcmToken.isEmpty() || userType.isEmpty())
            return;

        Map<String, String> loginData = new HashMap<>();
        loginData.put("email", email);
        loginData.put("password", password);
        loginData.put("fcmToken", fcmToken);

        if (userType.equals("USER")) {
            retrofitClient.postLoginUser(loginData, new ResultCallback<Dto<UserDto>>() {
                @Override
                public void onSuccess(Dto<UserDto> result1) {
                    retrofitClient.getFindMyGuardian(result1.getData().getUserId(), new ResultCallback<Dto<GuardianDto>>() {
                        @Override
                        public void onSuccess(Dto<GuardianDto> result2) {
                            User.getInstance().initUser(result1.getData(), result2.getData());
                            view.onAutoLoginSuccess(result1.getResponseMessage(), userType);
                        }

                        @Override
                        public void onFailure(String result, Throwable t) {
                            User.getInstance().initUser(result1.getData(), null);
                            view.onAutoLoginSuccess(result1.getResponseMessage(), userType);
                        }
                    });
                }

                @Override
                public void onFailure(String result, Throwable t) {
                    view.onAutoLoginFailure(result);
                }
            });
        }
        else {
            retrofitClient.postLoginGuardian(loginData, new ResultCallback<Dto<GuardianDto>>() {
                @Override
                public void onSuccess(Dto<GuardianDto> result1) {
                    retrofitClient.getFindMyUser(result1.getData().getUserId(), new ResultCallback<Dto<UserDto>>() {
                        @Override
                        public void onSuccess(Dto<UserDto> result2) {
                            Guardian.getInstance().initGuardian(result1.getData(), result2.getData());
                            view.onAutoLoginSuccess(result1.getResponseMessage(), userType);
                        }

                        @Override
                        public void onFailure(String result, Throwable t) {
                            Guardian.getInstance().initGuardian(result1.getData(), null);
                            view.onAutoLoginSuccess(result1.getResponseMessage(), userType);
                        }
                    });
                }

                @Override
                public void onFailure(String result, Throwable t) {
                    view.onAutoLoginFailure(result);
                }
            });
        }
    }
}
