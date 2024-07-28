package com.example.musubi.view.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.musubi.R;
import com.example.musubi.presenter.contract.UserMyPageContract;
import com.example.musubi.presenter.implementation.UserMyPagePresenter;
import com.example.musubi.model.dto.UserDto;

public class UserMyPageFragment extends Fragment implements UserMyPageContract.View {

    private UserMyPageContract.Presenter presenter;

    private TextView tvName, tvEmail, tvPhoneNumber, tvHomeAddress, tvDistrict;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_my_page, container, false);
        presenter = new UserMyPagePresenter(this);

        tvName = view.findViewById(R.id.tvUserName);
        tvEmail = view.findViewById(R.id.tvUserEmail);
        tvPhoneNumber = view.findViewById(R.id.tvUserPhoneNumber);
        tvHomeAddress = view.findViewById(R.id.tvUserHomeAddress);
        tvDistrict = view.findViewById(R.id.tvUserDistrict);

        presenter.loadUserInfo();

        return view;
    }

    @Override
    public void showUserInfo(UserDto userDto) {
        tvName.setText("이름: " + userDto.getName());
        tvEmail.setText("이메일: " + userDto.getEmail());
        tvPhoneNumber.setText("전화번호: " + userDto.getPhoneNumber());
        tvHomeAddress.setText("주소: " + userDto.getHomeAddress());
        tvDistrict.setText("구역: " + userDto.getDistrict());
    }
}
