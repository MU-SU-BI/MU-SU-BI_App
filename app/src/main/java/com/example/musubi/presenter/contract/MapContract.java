package com.example.musubi.presenter.contract;

import com.example.musubi.model.dto.SafeAreaDto;

import java.util.List;

public interface MapContract {
    interface View {
        void onSetSafeAreaSuccess(String responseMessage);

        void onSetSafeAreaFailure(String result);

        void addSafeZone(SafeAreaDto safeArea); // 안전구역을 지도에 추가하는 메서드

        void onCallSosSuccess(String message);
        void onCallSosFailure(String message);
    }

    interface Presenter {
        void createDistrict(double latitude, double longitude);

        void setMyUserSafeArea(List<SafeAreaDto> safeAreas);

        void getMyUserSafeArea(long userId); // 서버로부터 안전구역을 가져오는 메서드

        void requestSosToCommunity(long userId);
    }
}
