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
import com.example.musubi.model.entity.User;
import com.example.musubi.presenter.contract.UserMyPageContract;

public class UserMyPageFragment extends Fragment implements UserMyPageContract.View {
    private View view;

    private TextView nameTextView, emailTextView, phoneNumberTextView, homeAddressTextView, districtTextView;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        nameTextView = view.findViewById(R.id.name);
        emailTextView = view.findViewById(R.id.email);
        phoneNumberTextView = view.findViewById(R.id.phoneNumber);
        homeAddressTextView = view.findViewById(R.id.homeAddress);
        districtTextView = view.findViewById(R.id.district);
        showUserInfo();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guardian_my_page, container, false);
    }

    private void showUserInfo() {
        User user = User.getInstance();

        nameTextView.setText(user.getName());
        emailTextView.setText(user.getEmail());
        phoneNumberTextView.setText(user.getPhone());
        homeAddressTextView.setText(user.getAddress());
        districtTextView.setText(user.getDistrict());
    }
}
