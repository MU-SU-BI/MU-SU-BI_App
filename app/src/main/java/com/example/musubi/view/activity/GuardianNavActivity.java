package com.example.musubi.view.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.musubi.R;
import com.example.musubi.view.fragment.CommunityFragment;
import com.example.musubi.view.fragment.GuardianMyPageFragment;
import com.example.musubi.view.fragment.MapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GuardianNavActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_guardian_nav);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_map) {
                    selectedFragment = new MapFragment();
                } else if (itemId == R.id.navigation_community) {
                    selectedFragment = new CommunityFragment();
                } else if (itemId == R.id.navigation_guardian_mypage) {
                    selectedFragment = new GuardianMyPageFragment();
                }
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
                return false;
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapFragment()).commit();
        }
    }
}
