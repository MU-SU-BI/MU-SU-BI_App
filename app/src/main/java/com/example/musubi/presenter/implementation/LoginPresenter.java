package com.example.musubi.presenter.implementation;

import android.util.Log;

import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.GuardianDto;
import com.example.musubi.model.dto.UserDto;
import com.example.musubi.model.entity.Guardian;
import com.example.musubi.model.entity.User;
import com.example.musubi.model.remote.RetrofitClient;
import com.example.musubi.util.callback.ResultCallback;
import com.example.musubi.presenter.contract.LoginContract;

import java.util.HashMap;
import java.util.Map;

public class LoginPresenter  implements LoginContract.Presenter {
    private final LoginContract.View view;
    private final RetrofitClient retrofitClient;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.retrofitClient = new RetrofitClient();
        this.retrofitClient.initRetrofit();
    }

    @Override
    public void loginUser(String email, String password, String fcmToken) {
        Map<String, String> loginData = new HashMap<>();
        loginData.put("email", email);
        loginData.put("password", password);
        loginData.put("fcmToken", fcmToken);

        retrofitClient.postLoginUser(loginData, new ResultCallback<Dto<UserDto>>() {
            @Override
            public void onSuccess(Dto<UserDto> result1) {
                retrofitClient.getFindMyGuardian(result1.getData().getUserId(), new ResultCallback<Dto<GuardianDto>>() {
                    @Override
                    public void onSuccess(Dto<GuardianDto> result2) {
                        User.getInstance().initUser(result1.getData(), result2.getData());
                        view.onLoginSuccess("사용자 로그인 성공");
                    }

                    @Override
                    public void onFailure(String result, Throwable t) {
                        User.getInstance().initUser(result1.getData(), null);
                        view.onLoginSuccess("사용자 로그인 성공");
                    }
                });
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onLoginFailure(result);
            }
        });
    }

    @Override
    public void loginGuardian(String email, String password, String fcmToken) {
        Map<String, String> loginData = new HashMap<>();
        loginData.put("email", email);
        loginData.put("password", password);
        loginData.put("fcmToken", fcmToken);

        retrofitClient.postLoginGuardian(loginData, new ResultCallback<Dto<GuardianDto>>() {
            @Override
            public void onSuccess(Dto<GuardianDto> result1) {
                retrofitClient.getFindMyUser(result1.getData().getUserId(), new ResultCallback<Dto<UserDto>>() {
                    @Override
                    public void onSuccess(Dto<UserDto> result2) {
                        Guardian.getInstance().initGuardian(result1.getData(), result2.getData());
                        view.onLoginSuccess("보호자 로그인 성공");
                    }

                    @Override
                    public void onFailure(String result, Throwable t) {
                        Guardian.getInstance().initGuardian(result1.getData(), null);
                        view.onLoginSuccess("보호자 로그인 성공");
                    }
                });
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onLoginFailure(result);
            }
        });
    }

    @Override
    public void redirectToSignup() {
        view.redirectToSignup();
    }
}
