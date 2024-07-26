package com.example.musubi.presenter.contract;

public interface CallContract {
    interface View {
        void onCallSuccess(String message);
        void onCallFailure(String message);
    }

    interface Presenter {
        void callGuardian(long userId, String callMessage);
    }
}
