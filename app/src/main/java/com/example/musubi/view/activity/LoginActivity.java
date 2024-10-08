package com.example.musubi.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.musubi.R;
import com.example.musubi.presenter.contract.LoginContract;
import com.example.musubi.presenter.implementation.LoginPresenter;
import com.example.musubi.util.service.ForegroundService;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    private static final int USER_PERMISSION_REQUEST_CODE = 1;
    private static final int GUARDIAN_PERMISSION_REQUEST_CODE = 2;

    LoginPresenter presenter;

    EditText emailEditText;
    EditText passEditText;
    Button loginButton;
    TextView signupButton;
    RadioButton userRadioButton;
    RadioButton guardianRadioButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        presenter = new LoginPresenter(this, getApplicationContext());
        presenter.loginByAuto();
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

            if (isInputWrongLoginData(email, password)) {
                onLoginFailure("이메일 또는 비밀번호를 작성하세요.");
                return;
            }
            if (userRadioButton.isChecked())
                presenter.loginUser(email, password);
            else if (guardianRadioButton.isChecked())
                presenter.loginGuardian(email, password);
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

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override
    public void onLoginSuccess(String userType, String message) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.FOREGROUND_SERVICE_LOCATION,
                    Manifest.permission.POST_NOTIFICATIONS
            }, userType.equals("USER") ? USER_PERMISSION_REQUEST_CODE : GUARDIAN_PERMISSION_REQUEST_CODE);
        }
        else
            startServiceAndNavigate(userType);
    }

    private void startServiceAndNavigate(String userType) {
        Intent intent;
        Intent serviceIntent = new Intent(this, ForegroundService.class);

        if (userRadioButton.isChecked() || userType.equals("USER"))
            intent = new Intent(LoginActivity.this, UserNavActivity.class);
        else
            intent = new Intent(LoginActivity.this, GuardianNavActivity.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startForegroundService(serviceIntent);
        else
            startService(serviceIntent);

        setResult(Activity.RESULT_OK, intent);  // MainActivity 종료
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == USER_PERMISSION_REQUEST_CODE || requestCode == GUARDIAN_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startServiceAndNavigate(requestCode == USER_PERMISSION_REQUEST_CODE ? "USER" : "GUARDIAN");
            } else {
                onLoginFailure("권한이 필요합니다. 다시 로그인해주세요.");
            }
        }
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