package com.example.musubi.util.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.musubi.model.local.SPFManager;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

public class FCMService extends FirebaseMessagingService {
    SPFManager spfManager;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String fcmToken = task.getResult();
                        Log.d("FCMTextService", "FCM Token: " + fcmToken);
                        spfManager = new SPFManager(getApplicationContext(), "ACCOUNT");
                        spfManager.getEditor().putString("FCM_TOKEN", fcmToken).apply();
                    } else {
                        // 토큰 생성 실패
                        Log.e("FCMTextService", "FCM Error:" + Objects.requireNonNull(task.getException()).getMessage());
                    }
                });
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {// lets create a notification with these data
        super.onMessageReceived(remoteMessage);
    }
}