package com.example.musubi.presenter.contract;

public interface MapContract {
    interface View { }

    interface Presenter {
        void createDistrict(double latitude, double longitude);
    }
}
