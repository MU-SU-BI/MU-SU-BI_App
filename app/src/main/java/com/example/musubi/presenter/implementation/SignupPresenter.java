package com.example.musubi.presenter.implementation;

import com.example.musubi.model.dto.UserDto;
import com.example.musubi.model.http.RetrofitClient;
import com.example.musubi.model.http.callback.ResultCallback;
import com.example.musubi.presenter.contract.SignupContract;

public class SignupPresenter implements SignupContract.Presenter {
    private final SignupContract.View view;
    private final RetrofitClient retrofitClient;

    public SignupPresenter(SignupContract.View view) {
        this.view = view;
        this.retrofitClient = new RetrofitClient();
    }

    @Override
    public void userSignup(UserDto user) {
        retrofitClient.postSignupUser(user, new ResultCallback<String>() {
            @Override
            public void onSuccess(String result) {
                view.showSignupSuccess(result);
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.showSignupFailure(result);
            }
        });
    }
}
