package com.example.musubi.view.activity;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.musubi.R;
import com.example.musubi.model.dto.UserDto;
import com.example.musubi.model.entity.Gender;
import com.example.musubi.model.entity.Person;
import com.example.musubi.model.entity.User;
import com.example.musubi.presenter.contract.SignupContract;
import com.example.musubi.presenter.implementation.SignupPresenter;
import com.example.musubi.view.fragment.CompletedSignupFragment;

public class SignupActivity extends AppCompatActivity implements SignupContract.View {
    private SignupPresenter presenter;

    // view
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordReEditText;
    private  EditText nameEditText;
    private EditText nicknameEditText;
    private  EditText phoneEditText;
    private EditText addressEditText;
    private EditText ageEditText;
    private RadioGroup genderRadioGroup;
    private TextView passwordMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        passwordReEditText = findViewById(R.id.passwordRe);
        nameEditText = findViewById(R.id.name);
        nicknameEditText = findViewById(R.id.nickname);
        phoneEditText = findViewById(R.id.phone);
        addressEditText = findViewById(R.id.address);
        ageEditText = findViewById(R.id.age);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        passwordMessageTextView = findViewById(R.id.passwordMessage);

        Button signupButton = findViewById(R.id.signup);
        signupButton.setOnClickListener(v -> {
            presenter.userSignup(readSignupData());
        });

        // password 일치 여부 검사
        passwordReEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String password1 = passwordEditText.getText().toString();
                String password2 = passwordReEditText.getText().toString();
                presenter.checkPasswordMatch(password1, password2);
            }
        });

        // 휴대폰 번호 자동 하이픈(-) 설정
        phoneEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

    }

    private UserDto readSignupData() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String nickname = nicknameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String address = addressEditText.getText().toString();
        Gender gender = genderRadioGroup.getCheckedRadioButtonId() == 0 ? Gender.MALE : Gender.FEMALE;
        int age = Integer.parseInt(ageEditText.getText().toString());

        return new UserDto(-1, email, password, name, gender, age, nickname, phone, address);
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

    }

    @Override
    public void onPasswordMatchSuccess() {
        passwordMessageTextView.setText("");
    }

    @Override
    public void onPasswordMatchFailure() {
        passwordMessageTextView.setText("비밀번호가 일치하지 않습니다.");
    }
}