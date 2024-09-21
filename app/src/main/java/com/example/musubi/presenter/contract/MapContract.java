package com.example.musubi.presenter.contract;

import com.example.musubi.model.dto.SafeAreaDto;

import java.util.List;

public interface MapContract {
    interface View {
        void onSetSafeAreaSuccess(String responseMessage);

        void onSetSafeAreaFailure(String result);
    }

    interface Presenter {
        void createDistrict(double latitude, double longitude);

        void setMyUserSafeArea(List<SafeAreaDto> safeAreas);
    }
}
