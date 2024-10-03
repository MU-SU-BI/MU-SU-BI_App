package com.example.musubi.view.activity;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.musubi.R;
import com.example.musubi.model.dto.GuardianDto;
import com.example.musubi.model.dto.UserDto;
import com.example.musubi.model.entity.Gender;
import com.example.musubi.presenter.contract.SignupContract;
import com.example.musubi.presenter.implementation.SignupPresenter;
import com.example.musubi.view.fragment.CompletedSignupFragment;

public class SignupActivity extends AppCompatActivity implements SignupContract.View {
    private SignupPresenter presenter;

    // view
    private EditText nicknameEditText;
    private EditText phoneEditText;
    private EditText addressEditText;
    private EditText ageEditText;
    private RadioButton userRadioButton;
    private RadioButton guardianRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
        presenter = new SignupPresenter(this);
    }

    private void initView() {
        nicknameEditText = findViewById(R.id.nickname);
        phoneEditText = findViewById(R.id.phone);
        addressEditText = findViewById(R.id.address);
        ageEditText = findViewById(R.id.age);
        userRadioButton = findViewById(R.id.userRadioButton);
        guardianRadioButton = findViewById(R.id.guardianRadioButton);

        Button signupButton = findViewById(R.id.signup);
        signupButton.setOnClickListener(v -> {
            UserDto userDto = readUserSignupData();

            if (isInputWrongSignupData(userDto)) {
                onSignupFailure("작성하지 않은 항목이 존재합니다.");
                return;
            }
            if (userRadioButton.isChecked())
                presenter.userSignup(readUserSignupData());
            else if (guardianRadioButton.isChecked())
                presenter.guardianSignup(readGuardianSignupData());
            else
                onSignupFailure("사용자 또는 보호자 항목을 선택하세요.");
        });

        // 휴대폰 번호 자동 하이픈(-) 설정
        phoneEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

    }

    private UserDto readUserSignupData() {
        String email = "";
        String name = "";
        String nickname = nicknameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String address = addressEditText.getText().toString();
        Gender gender = getGender();
        String ageStr = ageEditText.getText().toString();
        int age = ageStr.isEmpty() ? 0 : Integer.parseInt(ageStr);

        return new UserDto(-1, email, name, gender, age, nickname, phone, address, null);
    }

    private boolean isInputWrongSignupData(UserDto user) {
        return user.getEmail().isEmpty() || user.getName().isEmpty()
                || user.getNickname().isEmpty() || user.getPhoneNumber().isEmpty() || user.getHomeAddress().isEmpty()
                || user.getAge() == 0;
    }

    private GuardianDto readGuardianSignupData() {
        String email = "";
        String name = "";
        String nickname = nicknameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String address = addressEditText.getText().toString();
        Gender gender = getGender();
        int age = Integer.parseInt(ageEditText.getText().toString());

        return new GuardianDto(-1, email, name, gender, age, nickname, phone, address, null);
    }

    public Gender getGender() {
        RadioButton male = findViewById(R.id.maleRadioButton);
        RadioButton female = findViewById(R.id.femaleRadioButton);
        Gender gender = null;

        if (male.isChecked()) {
            gender = Gender.MALE;
        } else if (female.isChecked()) {
            gender = Gender.FEMALE;
        }
        return gender;
    }

    @Override
    public void onSignupSuccess(String message) {
        FrameLayout frameLayout = findViewById(R.id.completedFragment);
        frameLayout.setVisibility(View.VISIBLE);
        // Fragment 교체
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.completedFragment, new CompletedSignupFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onSignupFailure(String message) {
        TextView signupMessage = findViewById(R.id.signupMessage);
        signupMessage.setText(message);
    }
}