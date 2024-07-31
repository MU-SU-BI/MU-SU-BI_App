package com.example.musubi.presenter.implementation;

import android.content.Context;
import android.util.Log;

import com.example.musubi.model.dto.UserDto;
import com.example.musubi.model.entity.User;
import com.example.musubi.model.local.SPFManager;
import com.example.musubi.presenter.contract.UserMyPageContract;

public class UserMyPagePresenter implements UserMyPageContract.Presenter {
    private final UserMyPageContract.View view;
    private final SPFManager spfManager;

    public UserMyPagePresenter(UserMyPageContract.View view, Context context) {
        this.view = view;
        this.spfManager = new SPFManager(context, "ACCOUNT");
    }

    @Override
    public void logoutUser() {
        User.getInstance().initUser(null, null);
        spfManager.getEditor().remove("EMAIL").apply();
        spfManager.getEditor().remove("PASSWORD").apply();
        spfManager.getEditor().remove("USER_TYPE").apply();

        int i = 1;
        while (spfManager.getSharedPreferences().getString("newBtn" + i, null) != null) {
            spfManager.getEditor().remove("newBtn" + i).apply();
            i++;
        }
        view.onLogoutSuccess();
    }
}
