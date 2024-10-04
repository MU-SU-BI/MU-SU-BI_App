package com.example.musubi.presenter.contract;

public interface CurrentLocationContract {
    interface View {
        void onCurrentLocationSuccess(String message);
        void onCurrentLocationFailure(String message);
    }
    interface Presenter {
        void putCurrentLocation(String type, double latitude, double longitude);
    }
}
