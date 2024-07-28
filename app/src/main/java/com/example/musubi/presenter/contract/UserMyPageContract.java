package com.example.musubi.presenter.contract;

import com.example.musubi.model.dto.UserDto;

public interface UserMyPageContract {
    interface View {
        void showUserInfo(UserDto userDto);
    }

    interface Presenter {
        void loadUserInfo();
    }
}
