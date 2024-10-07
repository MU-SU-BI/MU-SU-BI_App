package com.example.musubi.presenter.contract;

import com.example.musubi.model.dto.SosUserInfoDto;

public interface SosRequestContract {
    interface View {
        void onInquirySosUserInfoSuccess(SosUserInfoDto sosUserInfoDto, String message);
        void onInquirySosUserInfoFailure(String message);
    }

    interface Presenter {
        void inquirySosUserInfo(long userId);
    }
}
