package com.example.musubi.presenter.contract;

public interface UserMyPageContract {
    interface View {
        void onLogoutSuccess();
        void onLogoutFailure();
    }

    interface Presenter {
        void logoutUser();
    }
}
