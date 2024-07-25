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
        redirectSignupButton.setOnClickListener(v -> presenter.redirectToSignup());
    }

    @Override
    public void redirectToLogin() {

    }

    @Override
    public void redirectToSignup() {
        Intent intent = new Intent(MainActivity.this, BottomNavActivity.class);
        startActivity(intent);
    }
}