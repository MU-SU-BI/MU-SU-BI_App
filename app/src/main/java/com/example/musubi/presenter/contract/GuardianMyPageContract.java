package com.example.musubi.presenter.contract;

public interface GuardianMyPageContract {
    interface View {
        void onLogoutSuccess();
        void onLogoutFailure();
        void onConnectSuccess(String message);
        void onConnectFailure(String message);
    }

    interface Presenter {
        void logoutGuardian();
        void connectUser(String userName, String phoneNumber);
    }
}
