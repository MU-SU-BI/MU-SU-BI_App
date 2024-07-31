package com.example.musubi.view.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musubi.R;
import com.example.musubi.model.entity.Guardian;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.CircleOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.LocationTrackingMode;

import com.example.musubi.presenter.contract.MapContract;
import com.example.musubi.presenter.implementation.MapPresenter;

public class MapFragment extends Fragment implements OnMapReadyCallback, MapContract.View {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private MapView mapView;
    private NaverMap naverMap;
    private FusedLocationSource locationSource;
    private MapContract.Presenter presenter;
    private ActivityResultLauncher<String[]> requestPermissionLauncher;

    Marker marker = new Marker();
    private boolean isRunning = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
            Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);
        });

        presenter = new MapPresenter(this);

        return view;
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        showLocationPermissionRequest();
        setupMap();

        new Thread(() -> {
            while (isRunning) {
                if (isAdded() && !isDetached() && !isRemoving() && Guardian.getInstance().getUser() != null) {
                    double latitude = Guardian.getInstance().getUser().getLatitude();
                    double longitude = Guardian.getInstance().getUser().getLongitude();

                    requireActivity().runOnUiThread(() -> setUserMarker(latitude, longitude));
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void setupMap() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
//        naverMap.setOnMapClickListener((point, coord) ->
//                setSafeZone(coord.latitude, coord.longitude));
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);
    }

    private void showLocationPermissionRequest() {
        requestPermissionLauncher.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }
    public void setUserMarker(double latitude, double longitude) {
        marker.setMap(null);
        marker.setPosition(new LatLng(latitude, longitude));
        marker.setMap(naverMap);
    }

    public void setSafeZone(double latitude, double longitude) {
        CircleOverlay circle = new CircleOverlay();
        circle.setCenter(new LatLng(latitude, longitude));
        circle.setRadius(100);
        int semiTransparentGreen = ColorUtils.setAlphaComponent(Color.GREEN, 50); // 128은 50% 투명도를 의미함 (0~255)
        circle.setColor(semiTransparentGreen);
        circle.setMap(naverMap);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        isRunning = false; // Stop the thread when the fragment is paused
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        isRunning = false; // Stop the thread when the fragment is stopped
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false; // Stop the thread when the fragment is destroyed
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
