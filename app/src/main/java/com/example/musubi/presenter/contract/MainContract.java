package com.example.musubi.presenter.contract;

import com.example.musubi.model.dto.UserDto;

public interface MainContract {
    interface View {
        void redirectToLogin();
        void redirectToSignup();
    }

    interface Presenter {
        void redirectToLogin();
        void redirectToSignup();
    }
}
