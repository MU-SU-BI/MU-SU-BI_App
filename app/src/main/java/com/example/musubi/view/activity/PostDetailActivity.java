package com.example.musubi.view.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.musubi.R;
import com.example.musubi.model.dto.CommentDto;
import com.example.musubi.model.dto.PostDetailDto;
import com.example.musubi.model.dto.PostDto;
import com.example.musubi.model.entity.Guardian;
import com.example.musubi.model.entity.User;
import com.example.musubi.presenter.contract.CommunityContract;
import com.example.musubi.presenter.implementation.CommunityPresenter;
import com.example.musubi.view.adapter.CommentAdapter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity implements CommunityContract.View {
    private TextView authorTextView, dateTextView, titleTextView, contentTextView;
    private EditText commentEditText;
    private Button submitCommentButton;
    private RecyclerView commentsRecyclerView;
    private CommentAdapter commentAdapter;
    private List<CommentDto> commentList;
    private long postId;
    private CommunityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        // UI 요소 초기화
        authorTextView = findViewById(R.id.tv_author);
        dateTextView = findViewById(R.id.tv_date);
        titleTextView = findViewById(R.id.tv_title);
        contentTextView = findViewById(R.id.tv_content);
        commentEditText = findViewById(R.id.et_comment);
        submitCommentButton = findViewById(R.id.btn_submit_comment);
        commentsRecyclerView = findViewById(R.id.recycler_view_comments);

        // RecyclerView와 어댑터 초기화
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentsRecyclerView.setAdapter(commentAdapter);

        // 인텐트로부터 postId 가져오기
        postId = getIntent().getLongExtra("postId", -1);
        String type;
        long userId;

        // 유저 유형에 따른 ID 설정
        if (User.getInstance().getId() == -1) {
            type = "guardian";
            userId = Guardian.getInstance().getId();
        } else {
            type = "user";
            userId = User.getInstance().getId();
        }

        // Presenter 초기화
        presenter = new CommunityPresenter(this, PostDetailActivity.this);

        presenter.getPostDetail(postId, type, userId);

        // 서버로부터 댓글 목록 받아오기
        presenter.getComments(postId, userId, type);

        // 댓글 등록 버튼 클릭 리스너
        submitCommentButton.setOnClickListener(v -> {
            String commentContent = commentEditText.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String currentTime = sdf.format(new Date());  // 현재 시간을 포맷팅

            CommentDto comment;

            if (!commentContent.isEmpty()) {
                presenter.setComment(postId, commentContent, type, userId);  // 댓글 전송
                if (type.equals("user"))
                    comment = new CommentDto(User.getInstance().getNickname(), commentContent, currentTime, postId);
                else
                    comment = new CommentDto(Guardian.getInstance().getNickname(), commentContent, currentTime, postId);

                // 댓글 리스트에 추가 및 UI 업데이트
                commentList.add(comment);
                commentAdapter.notifyDataSetChanged();
                commentEditText.setText("");
            } else {
                Toast.makeText(PostDetailActivity.this, "댓글을 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // 서버에서 게시물 상세 정보를 받아서 UI에 반영
    public void onPostDetailLoaded(PostDetailDto postDetail) {
        updateUI(postDetail);
    }

    private void updateUI(PostDetailDto postDetail) {
        titleTextView.setText(postDetail.getTitle());
        contentTextView.setText(postDetail.getContent());
        authorTextView.setText(postDetail.getAuthorName());

        // "2024-10-05" 형식으로 포맷
        String formattedDate = formatDate(postDetail.getCreateAt());
        dateTextView.setText(formattedDate != null ? formattedDate : postDetail.getCreateAt());
    }

    // 날짜 포맷팅 함수
    private String formatDate(String rawDate) {
        if (rawDate == null || rawDate.isEmpty()) {
            return "날짜 없음";
        }

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date date = inputFormat.parse(rawDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "잘못된 날짜 형식";
        }
    }

    // 댓글 목록 로드 시 호출되는 메서드
    @Override
    public void onCommentsLoaded(List<CommentDto> comments) {
        commentList.clear(); // 기존 댓글 초기화
        commentList.addAll(comments); // 서버로부터 받은 댓글 추가
        commentAdapter.notifyDataSetChanged(); // 어댑터에 변경사항 반영
    }

    @Override
    public void onPostCreateFailure(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    // 다른 CommunityContract.View 인터페이스 메서드들
    public void onPostsLoaded(List<PostDto> data) {}

    @Override
    public void onPostCreated(String responseMessage) {}

    @Override
    public void onSetDistrictSuccess(String responseMessage) {}

    @Override
    public void onSetDistrictFailure(String result) {}
}
