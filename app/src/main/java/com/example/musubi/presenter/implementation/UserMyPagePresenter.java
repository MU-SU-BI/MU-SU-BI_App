package com.example.musubi.presenter.implementation;

import com.example.musubi.model.dto.UserDto;
import com.example.musubi.model.entity.User;
import com.example.musubi.presenter.contract.UserMyPageContract;

public class UserMyPagePresenter implements UserMyPageContract.Presenter {
    private final UserMyPageContract.View view;

    public UserMyPagePresenter(UserMyPageContract.View view) {
        this.view = view;
    }

    @Override
    public void loadUserInfo() {
        User user = User.getInstance();
        UserDto userDto = new UserDto(
                user.getId(),
                user.getEmail(),
                null,
                user.getName(),
                user.getGender(),
                user.getAge(),
                user.getNickname(),
                user.getPhone(),
                user.getAddress(),
                user.getDistrict()
        );
        view.showUserInfo(userDto);
    }
}
