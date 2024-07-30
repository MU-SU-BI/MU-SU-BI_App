package com.example.musubi.presenter.contract;

public interface LoginContract {
    interface View {
        void onLoginSuccess(String message);
        void onLoginFailure(String message);
        void redirectToSignup();
    }

    interface Presenter {
        void loginUser(String email, String password);
        void loginGuardian(String email, String password);
        void redirectToSignup();
    }
}
