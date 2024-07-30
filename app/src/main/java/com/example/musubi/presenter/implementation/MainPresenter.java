package com.example.musubi.presenter.implementation;

import com.example.musubi.presenter.contract.MainContract;

public class MainPresenter implements MainContract.Presenter{
    private final MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void redirectToLogin() {
        view.redirectToLogin();
    }
}
