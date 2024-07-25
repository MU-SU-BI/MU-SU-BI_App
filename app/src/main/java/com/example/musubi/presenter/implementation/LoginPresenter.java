package com.example.musubi.presenter.implementation;

import android.util.Log;

import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.UserDto;
import com.example.musubi.model.entity.User;
import com.example.musubi.model.http.RetrofitClient;
import com.example.musubi.model.http.callback.ResultCallback;
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
                view.onLoginSuccess("hello");
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onLoginFailure("이메일 또는 비밀번호가 틀립니다.");
            }
        });
    }

    @Override
    public void redirectToSignup() {
        view.redirectToSignup();
    }
}
