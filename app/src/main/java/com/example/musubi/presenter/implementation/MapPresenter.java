package com.example.musubi.presenter.implementation;

import com.example.musubi.presenter.contract.MapContract;

public class MapPresenter implements MapContract.Presenter {

    private final MapContract.View view;

    public MapPresenter(MapContract.View view) {
        this.view = view;
    }

    @Override
    public void requestLocationPermissions() {
        view.showLocationPermissionRequest();
    }

    @Override
    public void onLocationPermissionsResult(boolean granted) {
        if (granted) {
            // 위치 권한이 허용된 경우 현재 위치를 표시합니다.
            // 여기서 현재 위치의 좌표를 가져오는 로직을 구현해야 합니다.
            double latitude = 37.5665; // 임의의 위도 값
            double longitude = 126.9780; // 임의의 경도 값
            view.showCurrentLocation(latitude, longitude);
        } else {
            // 위치 권한이 거부된 경우
            view.showLocationPermissionDenied();
        }
    }
}
