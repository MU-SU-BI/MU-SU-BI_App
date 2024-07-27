package com.example.musubi.presenter.contract;

public interface MyPageContract {
    interface View {
        void onConnectSuccess(String message);
        void onConnectFailure(String message);
    }

    interface Presenter {
        void connectUser(String userName, String phoneNumber);
    }
}
