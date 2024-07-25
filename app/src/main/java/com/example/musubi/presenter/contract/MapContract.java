package com.example.musubi.presenter.contract;

public interface MapContract {
    interface View {
        void showLocationPermissionRequest();
        void showCurrentLocation(double latitude, double longitude);
        void showLocationPermissionDenied();
    }

    interface Presenter {
        void requestLocationPermissions();
        void onLocationPermissionsResult(boolean granted);
    }
}
