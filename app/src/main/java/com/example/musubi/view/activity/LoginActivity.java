package com.example.musubi.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.musubi.R;
import com.example.musubi.model.local.SPFManager;
import com.example.musubi.presenter.contract.LoginContract;
import com.example.musubi.presenter.implementation.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    SPFManager spfManager;
    LoginPresenter presenter;

    EditText emailEditText;
    EditText passEditText;
    Button loginButton;
    Button signupButton;
    RadioButton userRadioButton;
    RadioButton guardianRadioButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        presenter = new LoginPresenter(this);
        spfManager = new SPFManager(getApplicationContext(), "ACCOUNT");
        initView();
    }

    private void initView() {
        emailEditText = findViewById(R.id.email);
        passEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        signupButton = findViewById(R.id.login_go_signup);
        userRadioButton = findViewById(R.id.userRadioButton);
        guardianRadioButton = findViewById(R.id.guardianRadioButton);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passEditText.getText().toString();
            String fcmToken = spfManager.getSharedPreferences().getString("FCM_TOKEN", "");

            if (isInputWrongLoginData(email, password)) {
                onLoginFailure("이메일 또는 비밀번호를 작성하세요.");
                return;
            }
            if (userRadioButton.isChecked())
                presenter.loginUser(email, password, fcmToken);
            else if (guardianRadioButton.isChecked())
                presenter.loginGuardian(email, password, fcmToken);
            else
                onLoginFailure("사용자 또는 보호자 구분을 필요합니다.");
        });

        signupButton.setOnClickListener(v -> {
            presenter.redirectToSignup();
        });
    }

    private boolean isInputWrongLoginData(String email, String password) {
        return email.isEmpty() || password.isEmpty();
    }

    @Override
    public void onLoginSuccess(String message) {
        Intent intent = null;
        if (userRadioButton.isChecked())
            intent = new Intent(LoginActivity.this, UserNavActivity.class);
        else
            intent = new Intent(LoginActivity.this, GuardianNavActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginFailure(String message) {
        TextView errorMessage = findViewById(R.id.errorMessage);
        errorMessage.setText(message);
    }

    @Override
    public void redirectToSignup() {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }
}