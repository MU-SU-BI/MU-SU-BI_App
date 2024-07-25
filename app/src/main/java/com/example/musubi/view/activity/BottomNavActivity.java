package com.example.musubi.view.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.musubi.R;
import com.example.musubi.view.fragment.MapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

public class BottomNavActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                if (item.getItemId() == R.id.navigation_map) {
                    selectedFragment = new MapFragment();
                }
//                switch (item.getItemId()) {
//                    case R.id.navigation_map:
//                        selectedFragment = new MapFragment();
//                        break;
////                    case R.id.navigation_dashboard:
////                        selectedFragment = new MapFragment();
////                        break;
////                    case R.id.navigation_notifications:
////                        selectedFragment = new NotificationsFragment();
////                        break;
//                }
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
                return false;
            }
        });

        // 초기 프래그먼트 설정
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapFragment()).commit();
        }
    }
}
