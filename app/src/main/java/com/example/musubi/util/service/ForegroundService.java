package com.example.musubi.util.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.musubi.R;
import com.example.musubi.model.entity.Guardian;
import com.example.musubi.model.entity.User;
import com.example.musubi.presenter.implementation.CurrentLocationPresenter;
import com.example.musubi.presenter.contract.currentLocationContract;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class ForegroundService extends Service implements currentLocationContract.View {
    private static final String TAG = "ForegroundService";
    private static final String CHANNEL_ID = "ForegroundServiceChannel";
    private static final long LOCATION_UPDATE_INTERVAL = 5000L; // 5 seconds

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private CurrentLocationPresenter currentLocationPresenter;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("MUSUBI")
                .setContentText("MUSUBI 실시간 위치 정보 업데이트")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        startForeground(1, notification);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        currentLocationPresenter = new CurrentLocationPresenter(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    String type;

                    if (User.getInstance().getId() == -1) {
                        type = "guardian";
                    } else if (Guardian.getInstance().getId() == -1) {
                        type = "user";
                    } else
                        continue;

                    Log.d(TAG, "Location: " + latitude + ", " + longitude + " Type: " + type);
                    currentLocationPresenter.putCurrentLocation(type, latitude, longitude);
                }
            }
        };

        startLocationUpdates();
    }

    @SuppressWarnings("MissingPermission")
    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(LOCATION_UPDATE_INTERVAL)
                .setFastestInterval(LOCATION_UPDATE_INTERVAL / 2)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public void onCurrentLocationSuccess(String message) {
        Log.d(TAG, "Location update success: " + message);
    }

    @Override
    public void onCurrentLocationFailure(String message) {
        Log.d(TAG, "Location update failure: " + message);
    }
}
