package com.example.musubi.presenter.contract;

import com.example.musubi.model.dto.UserDto;

public interface SignupContract {
    interface View {
        void showSignupSuccess(String message);
        void showSignupFailure(String message);
    }

    interface Presenter {

        void userSignup(UserDto user);
    }
}
