package com.example.musubi.presenter.contract;

import com.example.musubi.model.dto.UserDto;

public interface MainContract {
    interface View {
        void redirectToLogin();
    }

    interface Presenter {
        void redirectToLogin();
    }
}
