package com.example.musubi.presenter.implementation;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.musubi.R;
import com.example.musubi.model.dto.Dto;
import com.example.musubi.model.dto.GuardianDto;
import com.example.musubi.model.dto.UserDto;
import com.example.musubi.model.entity.Guardian;
import com.example.musubi.model.entity.User;
import com.example.musubi.model.local.SPFManager;
import com.example.musubi.model.remote.RetrofitClient;
import com.example.musubi.util.callback.ResultCallback;
import com.example.musubi.presenter.contract.LoginContract;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginPresenter  implements LoginContract.Presenter {
    private final LoginContract.View view;
    private final RetrofitClient retrofitClient;
    private final SPFManager spfManager;
    private final Context context;
    private GoogleSignInClient googleSignInClient;

    public static final int RC_SIGN_IN = 9001;

    public LoginPresenter(LoginContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.retrofitClient = new RetrofitClient();
        this.retrofitClient.initRetrofit();
        this.spfManager = new SPFManager(context, "ACCOUNT");
        initGoogleSignIn();
    }

    private void initGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .build();
        googleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    private Map<String, String> getLoginData(String oauthType, String idToken, String fcmToken) {
        Map<String, String> loginData = new HashMap<>();

        loginData.put("OAUTH_TYPE", oauthType);
        loginData.put("ID_TOKEN", idToken);
        loginData.put("fcmToken", fcmToken);
        return loginData;
    }

    public void startGoogleLogin() {
        view.redirectToGoogleLogin(googleSignInClient);
    }

    @Override
    public void handleGoogleSignInResult(Intent data, String userType) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            String idToken = account.getIdToken();
            Log.d("Google", "idToken: " + idToken);
            if (userType.equals("USER"))
                loginUser("Google", idToken);
            else
                loginGuardian("Google", idToken);
        } catch (ApiException e){
            view.onLoginFailure("Google 로그인 실패");
            Log.e("GoogleLogin", String.valueOf(e));
        }
    }

    private void loginUser(String oauthType, String idToken) {
        String fcmToken = spfManager.getSharedPreferences().getString("FCM_TOKEN", "");
        Map<String, String> loginData = getLoginData(oauthType, idToken, fcmToken);

        retrofitClient.postLoginUser(loginData, new ResultCallback<Dto<UserDto>>() {
            @Override
            public void onSuccess(Dto<UserDto> result1) {
                retrofitClient.getFindMyGuardian(result1.getData().getUserId(), new ResultCallback<Dto<GuardianDto>>() {
                    @Override
                    public void onSuccess(Dto<GuardianDto> result2) {
                        User.getInstance().initUser(result1.getData(), result2.getData());
                        storeAutoLoginData(oauthType, idToken, "USER");
                        view.onLoginSuccess("사용자 로그인 성공");
                    }

                    @Override
                    public void onFailure(String result, Throwable t) {
                        User.getInstance().initUser(result1.getData(), null);
                        view.onLoginSuccess("사용자 로그인 성공");
                    }
                });
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onLoginFailure(result);
            }
        });
    }

    public void loginGuardian(String oauthType, String idToken) {
        String fcmToken = spfManager.getSharedPreferences().getString("FCM_TOKEN", "");
        Map<String, String> loginData = getLoginData(oauthType, idToken, fcmToken);

        retrofitClient.postLoginGuardian(loginData, new ResultCallback<Dto<GuardianDto>>() {
            @Override
            public void onSuccess(Dto<GuardianDto> result1) {
                retrofitClient.getFindMyUser(result1.getData().getUserId(), new ResultCallback<Dto<UserDto>>() {
                    @Override
                    public void onSuccess(Dto<UserDto> result2) {
                        Guardian.getInstance().initGuardian(result1.getData(), result2.getData());
                        storeAutoLoginData(oauthType, idToken, "GUARDIAN");
                        view.onLoginSuccess("보호자 로그인 성공");
                    }

                    @Override
                    public void onFailure(String result, Throwable t) {
                        Guardian.getInstance().initGuardian(result1.getData(), null);
                        view.onLoginSuccess("보호자 로그인 성공");
                    }
                });
            }

            @Override
            public void onFailure(String result, Throwable t) {
                view.onLoginFailure(result);
            }
        });
    }

    private void storeAutoLoginData(String oauthType, String idToken, String userType) {
        spfManager.getEditor().putString("OAUTH_TYPE", oauthType).apply();
        spfManager.getEditor().putString("ID_TOKEN", idToken).apply();
        spfManager.getEditor().putString("USER_TYPE", userType).apply();
    }
}
