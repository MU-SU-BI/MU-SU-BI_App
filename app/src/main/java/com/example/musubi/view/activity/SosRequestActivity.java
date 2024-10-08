package com.example.musubi.view.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.musubi.R;
import com.example.musubi.model.dto.SosUserInfoDto;
import com.example.musubi.presenter.contract.SosRequestContract;
import com.example.musubi.presenter.implementation.SosRequestPresenter;

import java.io.IOException;
import java.net.URL;

public class SosRequestActivity extends AppCompatActivity implements SosRequestContract.View {
    private TextView sosUserNameTextView;
    private TextView sosUserAgeTextView;
    private TextView sosUserGenderTextView;
    private TextView sosUserHomeAddressTextView;
    private TextView sosUserPhoneNumberTextView;
    private TextView sosGuardianPhoneNumberTextView;
    private ImageView sosUserPhotoImageView;
    private ImageView sosUserMapImageView;

    private SosRequestContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sos_request);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
        presenter = new SosRequestPresenter(this);

        Bundle data = getIntent().getExtras();
        if (data != null) {
            presenter.inquirySosUserInfo(Long.parseLong(data.getString("userId")));
        }
    }

    private void initView() {
        sosUserNameTextView = findViewById(R.id.sosUserName);
        sosUserAgeTextView = findViewById(R.id.sosUserAge);
        sosUserGenderTextView = findViewById(R.id.sosUserGender);
        sosUserHomeAddressTextView = findViewById(R.id.sosUserHomeAddress);
        sosUserPhoneNumberTextView = findViewById(R.id.sosUserPhoneNumber);
        sosGuardianPhoneNumberTextView = findViewById(R.id.sosGuardianPhoneNumber);
        sosUserPhotoImageView = findViewById(R.id.sosUserPhoto);
        sosUserMapImageView = findViewById(R.id.sosUserMap);
    }

    @Override
    public void onInquirySosUserInfoSuccess(SosUserInfoDto sosUserInfoDto, String message) {
        sosUserNameTextView.setText(sosUserInfoDto.getName());
        sosUserAgeTextView.setText(String.valueOf(sosUserInfoDto.getAge()));
        sosUserGenderTextView.setText(sosUserInfoDto.getSex().equals("남") ? "남자" : "여자");
        sosUserHomeAddressTextView.setText(sosUserInfoDto.getHomeAddress());
        sosUserPhoneNumberTextView.setText(sosUserInfoDto.getPhoneNumber());
        sosGuardianPhoneNumberTextView.setText(sosUserInfoDto.getGuardianPhoneNumber());
        if (sosUserInfoDto.getProfile() == null)
            sosUserPhotoImageView.setImageResource(R.drawable.baseline_close_24);
        else
            new Thread() {
                @Override
                public void run() {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(new URL(sosUserInfoDto.getProfile()).openStream());
                        runOnUiThread(() -> sosUserPhotoImageView.setImageBitmap(bitmap));
                    } catch (IOException e) {
                        runOnUiThread(() -> sosUserPhotoImageView.setImageResource(R.drawable.baseline_close_24));
                    }
                }
            }.start();
        if (sosUserInfoDto.getMapImage() == null)
            sosUserMapImageView.setImageResource(R.drawable.baseline_close_24);
        else
            new Thread() {
                @Override
                public void run() {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(new URL(sosUserInfoDto.getMapImage()).openStream());
                        runOnUiThread(() -> sosUserMapImageView.setImageBitmap(bitmap));
                    } catch (IOException e) {
//                        runOnUiThread(() -> sosUserMapImageView.setImageResource(R.drawable.baseline_close_24));
                    }
                }
            }.start();
    }

    @Override
    public void onInquirySosUserInfoFailure(String message) {
    }
}