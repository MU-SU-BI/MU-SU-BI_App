package com.example.musubi.presenter.implementation;

import com.example.musubi.model.dto.GuardianDto;
import com.example.musubi.model.dto.UserDto;
import com.example.musubi.model.remote.RetrofitClient;
import com.example.musubi.util.callback.ResultCallback;
import com.example.musubi.presenter.contract.SignupContract;

public class SignupPresenter implements SignupContract.Presenter {
    private final SignupContract.View view;
    private final RetrofitClient retrofitClient;

    public SignupPresenter(SignupContract.View view) {
        this.view = view;
        this.retrofitClient = new RetrofitClient();
        this.retrofitClient.initRetrofit();
    }

    @Override
    public void userSignup(UserDto user) {
        retrofitClient.postSignupUser(user, new ResultCallback<String>() {
            @Override
            public void onSuccess(String result) {
                view.onSignupSuccess(result);
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onSignupFailure(result);
            }
        });
    }

    @Override
    public void guardianSignup(GuardianDto guardian) {
        retrofitClient.postSignupGuardian(guardian, new ResultCallback<String>() {
            @Override
            public void onSuccess(String result) {
                view.onSignupSuccess(result);
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onSignupFailure(result);
            }
        });
    }

    @Override
    public void checkPasswordMatch(String password, String passwordRe) {
        if (!password.equals(passwordRe)) {
            view.onPasswordMatchFailure();
        }else{
            view.onPasswordMatchSuccess();
        }
    }
}
