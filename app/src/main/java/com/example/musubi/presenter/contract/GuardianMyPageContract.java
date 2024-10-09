package com.example.musubi.presenter.contract;

import android.net.Uri;

public interface GuardianMyPageContract {
    interface View {
        void onLogoutSuccess();
        void onLogoutFailure();
        void onConnectSuccess(String message);
        void onConnectFailure(String message);
        void onLoadMyUserInfoSuccess(String message);
        void onLoadMyUserInfoFailure(String message);
        void onUploadUserImageSuccess(String message);
        void onUploadUserImageFailure(String message);
    }

    interface Presenter {
        void logoutGuardian();
        void connectUser(String userName, String phoneNumber);
        void loadMyUserInfo(long guardianId);
        void uploadUserImage(Uri imageUri);
    }
}
