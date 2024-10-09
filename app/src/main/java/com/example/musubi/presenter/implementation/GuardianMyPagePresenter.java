package com.example.musubi.presenter.implementation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.UserConnectDto;
import com.example.musubi.model.dto.UserProfileDto;
import com.example.musubi.model.entity.Guardian;
import com.example.musubi.model.entity.User;
import com.example.musubi.model.local.SPFManager;
import com.example.musubi.model.remote.RetrofitClient;
import com.example.musubi.presenter.contract.GuardianMyPageContract;
import com.example.musubi.util.callback.ResultCallback;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class GuardianMyPagePresenter implements GuardianMyPageContract.Presenter {
    private final GuardianMyPageContract.View view;
    private final RetrofitClient retrofitClient;
    private final SPFManager spfManager;
    private final Context context;

    public GuardianMyPagePresenter(GuardianMyPageContract.View view, Context context) {
        this.view = view;
        this.retrofitClient = new RetrofitClient();
        this.retrofitClient.initRetrofit();
        this.context = context;
        spfManager = new SPFManager(this.context, "ACCOUNT");
    }

    @Override
    public void logoutGuardian() {
        Guardian.getInstance().initGuardian(null, null);
        spfManager.getEditor().remove("EMAIL").apply();
        spfManager.getEditor().remove("PASSWORD").apply();
        spfManager.getEditor().remove("USER_TYPE").apply();
        view.onLogoutSuccess();
    }

    @Override
    public void connectUser(String userName, String phoneNumber) {
        retrofitClient.postConnectUserWithGuardian(new UserConnectDto(Guardian.getInstance().getId(), userName, phoneNumber), new ResultCallback<Dto<Void>>() {
            @Override
            public void onSuccess(Dto<Void> result) {
                view.onConnectSuccess(result.getResponseMessage());
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onConnectFailure(result);
            }
        });
    }

    @Override
    public void uploadUserImage(Uri imageUri) {
        File file = new File(getPathFromImageUri(context, imageUri));
        RequestBody requestFile = RequestBody.create(MultipartBody.FORM, file);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        RequestBody idPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(Guardian.getInstance().getId()));

        retrofitClient.postMyUserImage(idPart, imagePart, new ResultCallback<Dto<UserProfileDto>>() {
            @Override
            public void onSuccess(Dto<UserProfileDto> result) {
                ((User)(Guardian.getInstance().getUser())).setProfileImage(result.getData().getProfileImageUrl());
                view.onUploadUserImageSuccess(result.getResponseMessage());
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onUploadUserImageFailure(result);
            }
        });
    }

    //Uri -> Path
    private String getPathFromImageUri(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        assert cursor != null;
        cursor.moveToNext();
        @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));

        cursor.close();
        return path;
    }
}
