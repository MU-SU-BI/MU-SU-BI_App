package com.example.musubi.view.dialog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.musubi.R;
import com.example.musubi.util.callback.OnActionEnrollListener;


public class CustomActionDialog extends DialogFragment {
    View view;

    private OnActionEnrollListener listener;
    private EditText actionNameEditText;
    private Button actionEnrollButton;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        initView();
    }

    public void setOnActionEnrollListener(OnActionEnrollListener listener) {
        this.listener = listener;
    }

    private void initView() {
        actionNameEditText = view.findViewById(R.id.actionName);
        actionEnrollButton = view.findViewById(R.id.actionEnroll);

        actionEnrollButton.setOnClickListener(v -> {
            String actionName = actionNameEditText.getText().toString();
            if (!actionName.isEmpty()) {
                listener.onActionEnroll(actionName);
                dismiss();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_custom_action, container, false);
    }
}