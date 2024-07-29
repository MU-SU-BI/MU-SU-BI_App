package com.example.musubi.presenter.implementation;

import com.example.musubi.model.dto.UserDto;
import com.example.musubi.model.entity.User;
import com.example.musubi.presenter.contract.UserMyPageContract;

public class UserMyPagePresenter implements UserMyPageContract.Presenter {
    private final UserMyPageContract.View view;

    public UserMyPagePresenter(UserMyPageContract.View view) {
        this.view = view;
    }
}
