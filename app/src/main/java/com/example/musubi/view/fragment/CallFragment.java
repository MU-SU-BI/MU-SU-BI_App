package com.example.musubi.view.fragment;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.musubi.R;
import com.example.musubi.model.entity.User;
import com.example.musubi.model.local.SPFManager;
import com.example.musubi.presenter.contract.CallContract;
import com.example.musubi.presenter.implementation.CallPresenter;
import com.example.musubi.util.callback.OnActionEnrollListener;
import com.example.musubi.view.dialog.CustomActionDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CallFragment extends Fragment implements CallContract.View, OnActionEnrollListener {
    View view;
    CallPresenter presenter;

    RelativeLayout layout;
    RelativeLayout button1;
    RelativeLayout button2;
    RelativeLayout button3;
    Button addCustomActionButton;

    SPFManager spfManager;
    ArrayList<Integer> buttonIdArray;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        presenter = new CallPresenter(this);
        spfManager = new SPFManager(requireContext(), "ACCOUNT");
//        spfManager.getEditor().clear().apply();
        buttonIdArray = new ArrayList<>();
        initView();
        initButtonList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_call, container, false);
    }

    private void initView() {
        layout = this.view.findViewById(R.id.main);
        button1 = this.view.findViewById(R.id.button1);
        button2 = this.view.findViewById(R.id.button2);
        button3 = this.view.findViewById(R.id.button3);
        addCustomActionButton = this.view.findViewById(R.id.addCustomAction);

        button1.setOnClickListener(v -> {
            String actionName = ((TextView)((RelativeLayout)v).getChildAt(0)).getText().toString();
            presenter.callGuardian(User.getInstance().getId(), actionName);
        });
        button2.setOnClickListener(v -> {
            String actionName = ((TextView)((RelativeLayout)v).getChildAt(0)).getText().toString();
            presenter.callGuardian(User.getInstance().getId(), actionName);
        });
        button3.setOnClickListener(v -> {
            String actionName = ((TextView)((RelativeLayout)v).getChildAt(0)).getText().toString();
            presenter.callGuardian(User.getInstance().getId(), actionName);
        });
        addCustomActionButton.setOnClickListener(v -> {
            CustomActionDialog customActionDialog = new CustomActionDialog();

            customActionDialog.setOnActionEnrollListener(this);
            customActionDialog.show(getChildFragmentManager(), "CustomActionDialog");
        });
    }

    private int getPixelToDp(int pixel) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (pixel * density);
    }

    private void createNewButton(String actionName) {
        RelativeLayout newButtonLayout = new RelativeLayout(requireContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                getPixelToDp(75)
        );
        layoutParams.addRule(RelativeLayout.BELOW, buttonIdArray.get(buttonIdArray.size() - 1));
        layoutParams.setMargins(0, 0, 0, getPixelToDp(30));

        newButtonLayout.setLayoutParams(layoutParams);
        newButtonLayout.setBackgroundResource(R.drawable.main_button2);

        TextView buttonText = new TextView(requireContext());
        RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        buttonText.setLayoutParams(textParams);
        buttonText.setText(actionName);
        buttonText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
        buttonText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);

        androidx.appcompat.widget.AppCompatButton closeButton = new androidx.appcompat.widget.AppCompatButton(requireContext());
        RelativeLayout.LayoutParams closeParams = new RelativeLayout.LayoutParams(
                getPixelToDp(60),
                getPixelToDp(60)
        );
        closeParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        closeParams.addRule(RelativeLayout.CENTER_VERTICAL);
        closeButton.setLayoutParams(closeParams);
        closeButton.setBackgroundResource(R.drawable.baseline_close_24);

        newButtonLayout.addView(buttonText);
        newButtonLayout.addView(closeButton);

        layout.addView(newButtonLayout);

        newButtonLayout.setOnClickListener(v -> {
            presenter.callGuardian(User.getInstance().getId(), actionName);
        });

        closeButton.setOnClickListener(v -> {
            removeButton((RelativeLayout) v.getParent());
            updateButtonPositions();
        });

        newButtonLayout.setId(View.generateViewId());
        buttonIdArray.add(newButtonLayout.getId());
    }

    private void removeButton(RelativeLayout buttonLayout) {
        int removedIndex = buttonIdArray.indexOf(buttonLayout.getId());

        layout.removeView(buttonLayout);
        buttonIdArray.remove(Integer.valueOf(buttonLayout.getId()));

        // SharedPreferences에서 버튼 정보 삭제
        String removedKey = "newBtn" + removedIndex; // 기본 버튼 3개를 고려
        spfManager.getEditor().remove(removedKey).apply();

        updateButtonPositions();
    }

    private void updateAddButtonPosition(int lastButtonId) {
        RelativeLayout.LayoutParams addButtonParams = (RelativeLayout.LayoutParams) addCustomActionButton.getLayoutParams();
        addButtonParams.addRule(RelativeLayout.BELOW, lastButtonId);
        addButtonParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, lastButtonId == layout.getId() ? RelativeLayout.TRUE : 0);
        addCustomActionButton.setLayoutParams(addButtonParams);
    }

    private void updateButtonPositions() {
        for (int i = 3; i < buttonIdArray.size(); i++) {
            View button = layout.findViewById(buttonIdArray.get(i));
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) button.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, buttonIdArray.get(i - 1));
            button.setLayoutParams(params);
        }
        updateAddButtonPosition(buttonIdArray.get(buttonIdArray.size() - 1));
    }

    private void initButtonList() {
        // 기존 추가된 버튼들 모두 제거
        for (int i = buttonIdArray.size() - 1; i >= 3; i--) {
            layout.removeView(layout.findViewById(buttonIdArray.get(i)));
        }

        buttonIdArray.clear();
        buttonIdArray.add(button1.getId());
        buttonIdArray.add(button2.getId());
        buttonIdArray.add(button3.getId());

        Map<String, ?> allEntries = spfManager.getSharedPreferences().getAll();
        List<Map.Entry<String, ?>> sortedEntries = new ArrayList<>(allEntries.entrySet());

        Collections.sort(sortedEntries, (e1, e2) -> e1.getKey().compareTo(e2.getKey()));

        for (Map.Entry<String, ?> entry : sortedEntries) {
            String key = entry.getKey();
            if (key.contains("newBtn")) {
                String actionName = (String) entry.getValue();
                if (actionName != null) {
                    createNewButton(actionName);
                }
            }
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
