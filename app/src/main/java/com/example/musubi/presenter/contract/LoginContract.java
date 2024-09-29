package com.example.musubi.presenter.contract;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public interface LoginContract {
    interface View {
        void onLoginSuccess(String message);
        void onLoginFailure(String message);
        void redirectToGoogleLogin(GoogleSignInClient mGoogleSignInClient);
    }

    interface Presenter {
        void startGoogleLogin();
        void handleGoogleSignInResult(Intent data, String userType);
    }
}
