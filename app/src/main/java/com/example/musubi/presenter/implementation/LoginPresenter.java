package com.example.musubi.presenter.implementation;

import android.util.Log;

import com.example.musubi.model.dto.UserDto;
import com.example.musubi.model.entity.User;
import com.example.musubi.model.http.RetrofitClient;
import com.example.musubi.model.http.callback.ResultCallback;
import com.example.musubi.presenter.contract.LoginContract;

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
        retrofitClient.postLoginUser(email, password, new ResultCallback<UserDto>() {
            @Override
            public void onSuccess(UserDto result) {
                User.getInstance().initUser(result);
            }

            @Override
            public void onFailure(String result, Throwable t) {

            }
        });
    }

    @Override
    public void redirectToSignup() {
        view.redirectToSignup();
    }
}
