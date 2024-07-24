package com.example.musubi.presenter.contract;

import com.example.musubi.model.dto.UserDto;

public interface SignupContract {
    interface View {
        void onSignupSuccess(String message);
        void onSignupFailure(String message);
    }

    interface Presenter {
        void userSignup(UserDto user);
    }
}
