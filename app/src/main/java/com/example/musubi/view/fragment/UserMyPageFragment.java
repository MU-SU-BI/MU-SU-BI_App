package com.example.musubi.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.musubi.R;
import com.example.musubi.model.entity.Gender;
import com.example.musubi.model.entity.Guardian;
import com.example.musubi.model.entity.User;
import com.example.musubi.presenter.contract.UserMyPageContract;
import com.example.musubi.presenter.implementation.UserMyPagePresenter;
import com.example.musubi.util.service.ForegroundService;
import com.example.musubi.view.activity.LoginActivity;
import com.example.musubi.view.activity.MainActivity;
import com.example.musubi.view.activity.SignupActivity;

public class UserMyPageFragment extends Fragment implements UserMyPageContract.View {
    private View view;
    private UserMyPagePresenter presenter;

    private Button logoutButton;
    private TextView nameTextView, emailTextView, phoneNumberTextView, homeAddressTextView, districtTextView;
    private TextView linkedGuardianNameTextView, linkedGuardianAgeTextView, linkedGuardianHomeAddressTextView, linkedGuardianGenderTextView;
    private CardView linkedGuardianCardView;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        presenter = new UserMyPagePresenter(this, getContext());

        logoutButton = view.findViewById(R.id.logout);
        logoutButton.setOnClickListener(v -> {
            presenter.logoutUser();
        });

        nameTextView = view.findViewById(R.id.name);
        emailTextView = view.findViewById(R.id.email);
        phoneNumberTextView = view.findViewById(R.id.phoneNumber);
        homeAddressTextView = view.findViewById(R.id.homeAddress);
        districtTextView = view.findViewById(R.id.district);
        linkedGuardianCardView = view.findViewById(R.id.linkedGuardianCardView);
        showUserInfo();

        linkedGuardianNameTextView = view.findViewById(R.id.linkedGuardianName);
        linkedGuardianAgeTextView = view.findViewById(R.id.linkedGuardianAge);
        linkedGuardianHomeAddressTextView = view.findViewById(R.id.linkedGuardianHomeAddress);
        linkedGuardianGenderTextView = view.findViewById(R.id.linkedGuardianGender);
        showGuardianInfo();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_my_page, container, false);
    }

    private void showUserInfo() {
        User user = User.getInstance();

        nameTextView.setText(user.getName());
        emailTextView.setText(user.getEmail());
        phoneNumberTextView.setText(user.getPhone());
        homeAddressTextView.setText(user.getAddress());
        districtTextView.setText(user.getDistrict());
    }

    private void showGuardianInfo() {
        Guardian guardian = (Guardian) User.getInstance().getGuardian();

        if (guardian.getId() == -1) {
            linkedGuardianCardView.setVisibility(View.GONE);
            return;
        }
        linkedGuardianNameTextView.setText(guardian.getName());
        linkedGuardianAgeTextView.setText(String.valueOf(guardian.getAge()));
        linkedGuardianHomeAddressTextView.setText(guardian.getAddress());
        linkedGuardianGenderTextView.setText(guardian.getGender() == Gender.MALE ? "남성" : "여성");
    }

    @Override
    public void onLogoutSuccess() {
        requireActivity().finish();
        requireActivity().stopService(new Intent(requireActivity(), ForegroundService.class));
        startActivity(new Intent(requireActivity(), MainActivity.class));
    }

    @Override
    public void onLogoutFailure() {

    }
}
