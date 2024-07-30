package com.example.musubi.view.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.musubi.R;
import com.example.musubi.model.entity.Gender;
import com.example.musubi.model.entity.User;
import com.example.musubi.presenter.contract.GuardianMyPageContract;
import com.example.musubi.model.entity.Guardian;
import com.example.musubi.presenter.implementation.GuardianMyPagePresenter;
import com.example.musubi.util.service.ForegroundService;
import com.example.musubi.view.activity.MainActivity;

public class GuardianMyPageFragment extends Fragment implements GuardianMyPageContract.View {
    private View view;
    private GuardianMyPageContract.Presenter presenter;

    private AlertDialog dialog;
    private Button connectUserButton, logoutButton;
    private TextView nameTextView, emailTextView, phoneNumberTextView, homeAddressTextView, districtTextView;
    private TextView linkedUserNameTextView, linkedUserAgeTextView, linkedUserHomeAddressTextView, linkedUserGenderTextView;
    private CardView linkedUserCardView;


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        presenter = new GuardianMyPagePresenter(this, getContext());
        nameTextView = view.findViewById(R.id.name);
        emailTextView = view.findViewById(R.id.email);
        phoneNumberTextView = view.findViewById(R.id.phoneNumber);
        homeAddressTextView = view.findViewById(R.id.homeAddress);
        districtTextView = view.findViewById(R.id.district);
        connectUserButton = view.findViewById(R.id.btnRegisterUser);
        connectUserButton.setOnClickListener(v -> showInputDialog());
        logoutButton = view.findViewById(R.id.logout);
        logoutButton.setOnClickListener(v -> presenter.logoutGuardian());
        showGuardianInfo();

        linkedUserNameTextView = view.findViewById(R.id.linkedUserName);
        linkedUserAgeTextView = view.findViewById(R.id.linkedUserAge);
        linkedUserHomeAddressTextView = view.findViewById(R.id.linkedUserHomeAddress);
        linkedUserGenderTextView = view.findViewById(R.id.linkedUserGender);
        linkedUserCardView = view.findViewById(R.id.linkedUserCardView);
        showLinkedUserInfo();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guardian_my_page, container, false);
    }

    private void showGuardianInfo() {
        Guardian guardian = Guardian.getInstance();

        nameTextView.setText(guardian.getName());
        emailTextView.setText(guardian.getEmail());
        phoneNumberTextView.setText(guardian.getPhone());
        homeAddressTextView.setText(guardian.getAddress());
        districtTextView.setText(guardian.getDistrict());
    }

    private void showLinkedUserInfo() {
        User user = (User) Guardian.getInstance().getUser();

        if (user.getId() == -1) {
            linkedUserCardView.setVisibility(View.GONE);
            return;
        }
        linkedUserNameTextView.setText(user.getName());
        linkedUserAgeTextView.setText(String.valueOf(user.getAge()));
        linkedUserHomeAddressTextView.setText(user.getAddress());
        linkedUserGenderTextView.setText(user.getGender() == Gender.MALE ? "남성" : "여성");
        connectUserButton.setVisibility(View.GONE);
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_connect_user, null);
        builder.setView(dialogView);

        EditText nameEditText = dialogView.findViewById(R.id.editTextName);
        EditText phoneEditText = dialogView.findViewById(R.id.editTextPhone);
        Button submitButton = dialogView.findViewById(R.id.submitButton);

        phoneEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        dialog = builder.create();
        submitButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();

            String phone = phoneEditText.getText().toString();
            presenter.connectUser(name, phone);
        });
        dialog.show();
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

    @Override
    public void onConnectSuccess(String message) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Toast.makeText(getActivity(), "연결 성공: " + message, Toast.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GuardianMyPageFragment()).commit();
    }

    @Override
    public void onConnectFailure(String message) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Toast.makeText(getActivity(), "연결 실패: " + message, Toast.LENGTH_SHORT).show();
    }
}
