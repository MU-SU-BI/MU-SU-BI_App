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
import android.widget.Toast;
import com.example.musubi.R;
import com.example.musubi.presenter.contract.MyPageContract;
import com.example.musubi.presenter.implementation.MyPagePresenter;
import com.example.musubi.model.http.RetrofitClient;

public class MyPageFragment extends Fragment implements MyPageContract.View {

    private MyPageContract.Presenter presenter;
    private AlertDialog dialog; // 다이얼로그 객체를 멤버 변수로 추가

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        presenter = new MyPagePresenter(this, new RetrofitClient());

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(v -> showInputDialog());

        return view;
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = getLayoutInflater().inflate(R.layout.connect_user_dialog, null);
        builder.setView(dialogView);

        EditText nameEditText = dialogView.findViewById(R.id.editTextName);
        EditText phoneEditText = dialogView.findViewById(R.id.editTextPhone);
        Button submitButton = dialogView.findViewById(R.id.submitButton);

        dialog = builder.create(); // 다이얼로그 객체를 멤버 변수로 초기화
        submitButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            presenter.connectUser(name, phone);
        });

        dialog.show();
    }

    @Override
    public void onConnectSuccess(String message) {
        // 성공 시 처리 로직
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Toast.makeText(getActivity(), "연결 성공: " + message, Toast.LENGTH_SHORT).show();
        // 마이페이지로 돌아가기 위해서 현재 프래그먼트를 재설정
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyPageFragment()).commit();
    }

    @Override
    public void onConnectFailure(String message) {
        // 실패 시 처리 로직
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Toast.makeText(getActivity(), "연결 실패: " + message, Toast.LENGTH_SHORT).show();
    }
}
