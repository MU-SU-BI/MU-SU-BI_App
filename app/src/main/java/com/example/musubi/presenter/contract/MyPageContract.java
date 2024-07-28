package com.example.musubi.presenter.contract;

import com.example.musubi.model.dto.GuardianDto;

public interface MyPageContract {
    interface View {
        void onConnectSuccess(String message);
        void onConnectFailure(String message);
    }

    interface Presenter {
        void connectUser(String userName, String phoneNumber);
    }
}
