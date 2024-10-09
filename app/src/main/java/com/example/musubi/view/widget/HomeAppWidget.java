package com.example.musubi.view.widget;

import static com.google.firebase.messaging.Constants.MessageNotificationKeys.CLICK_ACTION;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.musubi.R;
import com.example.musubi.model.dto.CallDto;
import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.local.SPFManager;
import com.example.musubi.model.remote.RetrofitClient;
import com.example.musubi.util.callback.ResultCallback;

/**
 * Implementation of App Widget functionality.
 */
public class HomeAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.home_app_widget);

        // 버튼 클릭 이벤트 설정
        Intent intent = new Intent(context, HomeAppWidget.class);
        intent.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.widget, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (CLICK_ACTION.equals(intent.getAction())) {
            performButtonClickAction(context);
        }
    }

    private void performButtonClickAction(Context context) {
        SPFManager spfManager = new SPFManager(context, "ACCOUNT");
        String userType = spfManager.getSharedPreferences().getString("USER_TYPE", "");

        if (userType.equals("GUARDIAN") || userType.isEmpty()) {
            Toast.makeText(context, "사용자로 로그인해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        RetrofitClient retrofitClient = new RetrofitClient();
        retrofitClient.initRetrofit();

        retrofitClient.postCallGuardianWithMessage(new CallDto(spfManager.getSharedPreferences().getLong("USER_ID", -1), "도움이 필요해요" ), new ResultCallback<Dto<Void>>() {
            @Override
            public void onSuccess(Dto<Void> result) {
                Toast.makeText(context, "보호자 호출 완료", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String result, Throwable t) {
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}