package com.example.musubi.view.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.musubi.R;
import com.example.musubi.presenter.contract.MyPageContract;
import com.example.musubi.presenter.implementation.MyPagePresenter;
import com.example.musubi.model.entity.Guardian;
import com.example.musubi.model.remote.RetrofitClient;

public class GuardianMyPageFragment extends Fragment implements MyPageContract.View {

    private MyPageContract.Presenter presenter;
    private AlertDialog dialog;

    private TextView tvName, tvEmail, tvPhoneNumber, tvHomeAddress, tvDistrict;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guardian_my_page, container, false);
        presenter = new MyPagePresenter(this, new RetrofitClient());

        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
        tvHomeAddress = view.findViewById(R.id.tvHomeAddress);
        tvDistrict = view.findViewById(R.id.tvDistrict);

        loadGuardianInfo();

        Button button = view.findViewById(R.id.btnRegisterUser);
        button.setOnClickListener(v -> showInputDialog());

        return view;
    }

    private void loadGuardianInfo() {
        Guardian guardian = Guardian.getInstance();
        tvName.setText("이름: " + guardian.getName());
        tvEmail.setText("이메일: " + guardian.getEmail());
        tvPhoneNumber.setText("전화번호: " + guardian.getPhone());
        tvHomeAddress.setText("주소: " + guardian.getAddress());
        tvDistrict.setText("구역: " + guardian.getDistrict());
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = getLayoutInflater().inflate(R.layout.connect_user_dialog, null);
        builder.setView(dialogView);

        EditText nameEditText = dialogView.findViewById(R.id.editTextName);
        EditText phoneEditText = dialogView.findViewById(R.id.editTextPhone);
        Button submitButton = dialogView.findViewById(R.id.submitButton);

        dialog = builder.create();
        submitButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            presenter.connectUser(name, phone);
        });

        dialog.show();
    }

    @Override
    public void onConnectSuccess(String message) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Toast.makeText(getActivity(), "연결 성공: " + message, Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GuardianMyPageFragment()).commit();
    }

    @Override
    public void onConnectFailure(String message) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Toast.makeText(getActivity(), "연결 실패: " + message, Toast.LENGTH_SHORT).show();
    }
}
