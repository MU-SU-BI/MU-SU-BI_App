package com.example.musubi.presenter.contract;

public interface MainContract {
    interface View {
        void onAutoLoginSuccess(String message, String userType);
        void onAutoLoginFailure(String message);
    }

    interface Presenter {
        void autoLogin();
    }
}
