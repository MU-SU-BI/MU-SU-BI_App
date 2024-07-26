package com.example.musubi.view.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.musubi.R;
import com.example.musubi.presenter.contract.CallContract;

public class CallFragment extends Fragment implements CallContract.View {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_call, container, false);
    }

    @Override
    public void onCallSuccess(String message) {

    }

    @Override
    public void onCallFailure(String message) {

    }
}
