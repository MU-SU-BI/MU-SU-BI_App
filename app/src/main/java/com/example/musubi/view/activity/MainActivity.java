package com.example.musubi.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.musubi.R;
import com.example.musubi.model.entity.Guardian;
import com.example.musubi.model.entity.User;
import com.example.musubi.model.local.SPFManager;
import com.example.musubi.presenter.contract.MainContract;
import com.example.musubi.presenter.implementation.LoginPresenter;
import com.example.musubi.presenter.implementation.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private final ActivityResultLauncher<Intent> mainLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // 로그인 성공 시 메인 액티비티 종료
                    finish();
                }
            }
    );
    private MainPresenter presenter;

    private Button redirectLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        presenter = new MainPresenter(this, this);
        checkAutoLogin();
        initView();
    }

    private void initView() {
        redirectLoginButton = findViewById(R.id.redirectLogin);
        redirectLoginButton.setOnClickListener(v -> mainLauncher.launch(new Intent(MainActivity.this, LoginActivity.class)));
    }

    private void checkAutoLogin() {
        presenter.autoLogin();
    }

    @Override
    public void onAutoLoginSuccess(String message, String userType) {
        Intent intent;
        if (userType.equals("USER"))
            intent = new Intent(MainActivity.this, UserNavActivity.class);
        else
            intent = new Intent(MainActivity.this, GuardianNavActivity.class);

        startActivity(intent);
        finish();
    }

    @Override
    public void onAutoLoginFailure(String message) {

    }
}