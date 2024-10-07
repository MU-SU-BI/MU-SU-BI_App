package com.example.musubi.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.musubi.R;
import com.example.musubi.model.entity.Guardian;
import com.example.musubi.model.entity.User;
import com.example.musubi.presenter.contract.CommunityContract;
import com.example.musubi.presenter.implementation.CommunityPresenter;
import com.example.musubi.model.dto.PostDetailDto;
import com.example.musubi.model.dto.CommentDto;

import java.util.List;

public class WritePostActivity extends AppCompatActivity implements CommunityContract.View {
    private EditText titleEditText, contentEditText;
    private Button submitButton;
    private CommunityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        // UI 요소 초기화
        titleEditText = findViewById(R.id.edit_text_title);
        contentEditText = findViewById(R.id.edit_text_content);
        submitButton = findViewById(R.id.btn_submit_post);

        // CommunityPresenter 생성 시 this와 context를 함께 전달
        presenter = new CommunityPresenter(this, this);

        // 제출 버튼 클릭 리스너
        submitButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String content = contentEditText.getText().toString();
            long userId = (User.getInstance().getId() == -1) ? Guardian.getInstance().getId() : User.getInstance().getId();

            if (!title.isEmpty() && !content.isEmpty()) {
                presenter.createPost(title, content, userId);  // 사용자의 ID를 전달

                // 작성 성공을 알리는 인텐트를 생성하고, 결과를 설정한 후 종료
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            } else {
                Toast.makeText(WritePostActivity.this, "제목과 내용을 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPostCreated(String responseMessage) {
        Toast.makeText(this, "게시물이 작성되었습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostCreateFailure(String result) {
        Toast.makeText(this, "게시물 작성에 실패했습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSetDistrictSuccess(String responseMessage) {}

    @Override
    public void onSetDistrictFailure(String result) {}

    @Override
    public void onPostsLoaded(List<com.example.musubi.model.dto.PostDto> data) {}

    @Override
    public void onPostDetailLoaded(PostDetailDto postDetail) {}

    @Override
    public void onCommentsLoaded(List<CommentDto> comments) {}
}
