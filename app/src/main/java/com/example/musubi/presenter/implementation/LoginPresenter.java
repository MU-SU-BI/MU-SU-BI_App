package com.example.musubi.presenter.implementation;

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
    public void loginUser(String email, String password) {
        Map<String, String> loginData = new HashMap<>();
        loginData.put("email", email);
        loginData.put("password", password);

        retrofitClient.postLoginUser(loginData, new ResultCallback<Dto<UserDto>>() {
            @Override
            public void onSuccess(Dto<UserDto> result) {
                User.getInstance().initUser(result.getData());
                view.onLoginSuccess("사용자 로그인 성공");
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onLoginFailure(result);
            }
        });
    }

    @Override
    public void loginGuardian(String email, String password) {
        Map<String, String> loginData = new HashMap<>();
        loginData.put("email", email);
        loginData.put("password", password);

        retrofitClient.postLoginGuardian(loginData, new ResultCallback<Dto<GuardianDto>>() {
            @Override
            public void onSuccess(Dto<GuardianDto> result) {
                Guardian.getInstance().initGuardian(result.getData());
                view.onLoginSuccess("보호자 로그인 성공");
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
