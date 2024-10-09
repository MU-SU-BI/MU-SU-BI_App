package com.example.musubi.view.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;
import com.example.musubi.R;
import com.example.musubi.model.dto.SafeAreaDto;
import com.example.musubi.model.entity.Guardian;
import com.example.musubi.presenter.contract.MapContract;
import com.example.musubi.presenter.implementation.MapPresenter;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.CircleOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, MapContract.View {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private boolean isRunning = true;
    private boolean isSafeZoneAdding = false;
    private double safeZoneRadius = 100; // 기본 반지름 값

    private MapView mapView;
    private NaverMap naverMap;
    private FusedLocationSource locationSource;
    private MapContract.Presenter presenter;
    private ActivityResultLauncher<String[]> requestPermissionLauncher;
    private List<SafeAreaDto> safeAreas = new ArrayList<>();
    Marker marker = new Marker();

    private Button btnAddSafeZone;
    private Button btnAddSafeZoneSet;
    private androidx.appcompat.widget.AppCompatButton sosRequestButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = view.findViewById(R.id.map_view);
        btnAddSafeZone = view.findViewById(R.id.btn_add_safezone);
        btnAddSafeZoneSet = view.findViewById(R.id.btn_add_safezone_set);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // 안전구역 추가 버튼 클릭 리스너
        btnAddSafeZone.setOnClickListener(v -> showRadiusInputDialog());

        // 안전구역 설정 버튼 클릭 리스너
        btnAddSafeZoneSet.setOnClickListener(v -> {
            if (!safeAreas.isEmpty()) {
                presenter.setMyUserSafeArea(safeAreas);
                Toast.makeText(requireContext(), "안전구역 설정 완료.", Toast.LENGTH_SHORT).show();
                btnAddSafeZoneSet.setVisibility(View.INVISIBLE);
                btnAddSafeZone.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(requireContext(), "안전구역을 선택하세요.", Toast.LENGTH_SHORT).show();
            }
            isSafeZoneAdding = false;
        });

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
            Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);
        });

        sosRequestButton = view.findViewById(R.id.sosRequset);
        sosRequestButton.setOnClickListener(v -> presenter.requestSosToCommunity(Guardian.getInstance().getId()));

        presenter = new MapPresenter(this);

        return view;
    }

    private void showRadiusInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_radius_input, null);
        builder.setView(dialogView);

        EditText etRadius = dialogView.findViewById(R.id.et_radius);

        builder.setPositiveButton("확인", (dialog, which) -> {
            String radiusInput = etRadius.getText().toString();
            if (!radiusInput.isEmpty()) {
                safeZoneRadius = Double.parseDouble(radiusInput); // 반지름 값 저장
                isSafeZoneAdding = true;
                btnAddSafeZoneSet.setVisibility(View.VISIBLE);
                btnAddSafeZone.setVisibility(View.INVISIBLE);
                Toast.makeText(requireContext(), "반지름 " + safeZoneRadius + "m의 안전구역을 선택하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("취소", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void showLocationPermissionRequest() {
        requestPermissionLauncher.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        showLocationPermissionRequest();
        setupMap();

        presenter.getMyUserSafeArea(Guardian.getInstance().getId());

        naverMap.setOnMapClickListener((point, coord) -> {
            if (isSafeZoneAdding) {
                SafeAreaDto safeArea = new SafeAreaDto(Guardian.getInstance().getId(), coord.longitude, coord.latitude, safeZoneRadius);
                safeAreas.add(safeArea);
                setSafeZone(coord.latitude, coord.longitude, safeZoneRadius);
                Toast.makeText(requireContext(), "안전구역이 선택되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

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
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);
    }

    public void setUserMarker(double latitude, double longitude) {
        marker.setMap(null);
        marker.setPosition(new LatLng(latitude, longitude));
        marker.setMap(naverMap);
    }

    public void setSafeZone(double latitude, double longitude, double radius) {
        CircleOverlay circle = new CircleOverlay();
        circle.setCenter(new LatLng(latitude, longitude));
        circle.setRadius(radius); // 입력된 반지름 값 적용
        int semiTransparentGreen = ColorUtils.setAlphaComponent(Color.parseColor("#76A66F"), 128); // 128은 50% 투명도
        circle.setColor(semiTransparentGreen);
        circle.setMap(naverMap);

        Marker safeZoneMarker = new Marker();
        safeZoneMarker.setIconTintColor(Color.parseColor("#FF0000"));
        safeZoneMarker.setPosition(new LatLng(latitude, longitude));
        safeZoneMarker.setMap(naverMap);
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
        isRunning = false;
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        isRunning = false;
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSetSafeAreaSuccess(String responseMessage) {}

    @Override
    public void onSetSafeAreaFailure(String result) {}

    @Override
    public void addSafeZone(SafeAreaDto safeArea) {
        setSafeZone(safeArea.getLatitude(), safeArea.getLongitude(), safeArea.getRadius());
    }

    @Override
    public void onCallSosSuccess(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCallSosFailure(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
