package com.example.musubi.view.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.musubi.R;
import com.example.musubi.model.local.SPFManager;
import com.example.musubi.presenter.contract.CallContract;
import com.example.musubi.presenter.implementation.CallPresenter;
import com.example.musubi.util.callback.OnActionEnrollListener;
import com.example.musubi.view.dialog.CustomActionDialog;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Objects;

public class CallFragment extends Fragment implements CallContract.View, OnActionEnrollListener {
    View view;
    CallPresenter presenter;

    RelativeLayout layout;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button addCustomActionButton;

    SPFManager spfManager;
    ArrayList<Integer> buttonIdArray;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        presenter = new CallPresenter(this);
        spfManager = new SPFManager(requireContext(), "CallFragment");
        initView();
        initButtonList();
        spfManager.getEditor().clear();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_call, container, false);
    }

    private void initView() {
        layout  = this.view.findViewById(R.id.main);
        button1 = this.view.findViewById(R.id.button1);
        button2 = this.view.findViewById(R.id.button2);
        button3 = this.view.findViewById(R.id.button3);
        button4 = this.view.findViewById(R.id.button4);
        button5 = this.view.findViewById(R.id.button5);
        addCustomActionButton = this.view.findViewById(R.id.addCustomAction);

        addCustomActionButton.setOnClickListener(v -> {
            CustomActionDialog customActionDialog = new CustomActionDialog();

            customActionDialog.setOnActionEnrollListener(this);
            customActionDialog.show(getChildFragmentManager(), "CustomActionDialog");
        });
    }

    private void createNewButton(String actionName) {
        MaterialButton newButton = new MaterialButton(requireContext());

        // new 버튼 스타일 추가
        newButton.setText(actionName);
        newButton.setId(View.generateViewId());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.BELOW, buttonIdArray.get(buttonIdArray.size() - 1)); // 가장 마지막 버튼 아래에 위치
        params.addRule(RelativeLayout.CENTER_HORIZONTAL); // 가운데 정렬
        params.setMargins(0, 16, 0, 16); // 마진 설정
        layout.addView(newButton, params);
        buttonIdArray.add(newButton.getId());
    }

    private void updateAddButtonPosition(int lasButtonId) {         // 기존의 add 버튼 위치 변경
        RelativeLayout.LayoutParams addButtonParams = (RelativeLayout.LayoutParams) addCustomActionButton.getLayoutParams();
        addButtonParams.addRule(RelativeLayout.BELOW, lasButtonId); // newButton 아래에 위치
        addCustomActionButton.setLayoutParams(addButtonParams);
    }

    private void initButtonList() {
        buttonIdArray  = new ArrayList<>();
        buttonIdArray.add(button5.getId());

        while (true) {
            String newBtnActionName = spfManager.getSharedPreferences().getString("newBtn" + buttonIdArray.size(), null);
            if (newBtnActionName == null)
                break ;
            createNewButton(newBtnActionName);
        }
        updateAddButtonPosition(buttonIdArray.get(buttonIdArray.size() - 1));
    }

    @Override
    public void onCallSuccess(String message) {

    }

    @Override
    public void onCallFailure(String message) {

    }

    @Override
    public void onActionEnroll(String actionName) {
        createNewButton(actionName);
        updateAddButtonPosition(buttonIdArray.get(buttonIdArray.size() - 1));
        spfManager.getEditor().putString("newBtn" + (buttonIdArray.size() - 1), actionName).apply();
    }
}
