package com.example.musubi.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.musubi.R;
import com.example.musubi.presenter.contract.MainContract;
import com.example.musubi.presenter.implementation.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private MainPresenter presenter;

    private Button redirectSignupButton;
    private Button redirectLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
        presenter = new MainPresenter(this);
    }

    private void initView() {
        redirectSignupButton = findViewById(R.id.redirectSignup);
        redirectLoginButton = findViewById(R.id.redirectLogin);

        redirectSignupButton.setOnClickListener(v -> presenter.redirectToSignup());
        redirectLoginButton.setOnClickListener(v -> presenter.redirectToLogin());
    }

    @Override
    public void redirectToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void redirectToSignup() {
        Intent intent = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(intent);
    }
}