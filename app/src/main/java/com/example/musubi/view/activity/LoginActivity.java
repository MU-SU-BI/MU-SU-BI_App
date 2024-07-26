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
import com.example.musubi.presenter.contract.LoginContract;
import com.example.musubi.presenter.implementation.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
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

        initView();
        presenter = new LoginPresenter(this);
    }

    private void initView() {
        emailEditText = findViewById(R.id.email);
        passEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        signupButton = findViewById(R.id.login_go_signup);
        userRadioButton = findViewById(R.id.userRadioButton);
        guardianRadioButton = findViewById(R.id.guardianRadioButton);

        loginButton.setOnClickListener(v -> {
            if (userRadioButton.isChecked())
                presenter.loginUser(emailEditText.getText().toString(), passEditText.getText().toString());
            else if (guardianRadioButton.isChecked())
                presenter.loginGuardian(emailEditText.getText().toString(), passEditText.getText().toString());
            else
                onLoginFailure("사용자 또는 보호자 구분을 필요합니다.");
        });

        signupButton.setOnClickListener(v -> {
            presenter.redirectToSignup();
        });
    }

    @Override
    public void onLoginSuccess(String message) {
        Intent intent = new Intent(LoginActivity.this, UserNavActivity.class);
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