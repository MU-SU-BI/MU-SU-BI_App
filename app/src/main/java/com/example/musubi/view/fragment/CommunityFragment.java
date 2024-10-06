package com.example.musubi.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musubi.R;
import com.example.musubi.model.dto.CommentDto;  // CommentDto import 추가
import com.example.musubi.model.dto.PostDetailDto;
import com.example.musubi.model.dto.PostDto;
import com.example.musubi.model.entity.Guardian;
import com.example.musubi.model.entity.User;
import com.example.musubi.presenter.contract.CommunityContract;
import com.example.musubi.presenter.implementation.CommunityPresenter;
import com.example.musubi.view.activity.PostDetailActivity;
import com.example.musubi.view.activity.WritePostActivity;
import com.example.musubi.view.adapter.PostAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment implements CommunityContract.View, PostAdapter.OnPostClickListener {
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<PostDto> postList;
    private CommunityPresenter presenter;
    private TextView locationText;  // 지역 정보를 표시할 TextView

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        presenter = new CommunityPresenter(this, requireContext());
        postList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        postAdapter = new PostAdapter(postList, this);
        recyclerView.setAdapter(postAdapter);

        locationText = view.findViewById(R.id.location_text);  // 지역 정보를 표시할 TextView 연결

        Button writePostButton = view.findViewById(R.id.btn_write_post);
        writePostButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WritePostActivity.class);
            startActivityForResult(intent, 1001);
        });

        // 지역 정보 가져오기
        fetchLocationData();
        fetchPostsFromServer();

        return view;
    }

    // 지역 정보를 가져오는 메서드
    private void fetchLocationData() {
        String type = (User.getInstance().getId() == -1) ? "guardian" : "user";
        presenter.setMyDistrict(type);  // 위치 정보를 설정하는 메서드 호출
    }

    // 서버로부터 게시물 목록을 가져오는 메서드
    private void fetchPostsFromServer() {
        String type = (User.getInstance().getId() == -1) ? "guardian" : "user";
        long userId = (User.getInstance().getId() == -1) ? Guardian.getInstance().getId() : User.getInstance().getId();
        presenter.getGuardianPosts(type, userId);
    }

    @Override
    public void onSetDistrictSuccess(String data) {
        // 받아온 지역 좌표를 쉼표 또는 공백 기준으로 나누어 마지막 부분을 추출
        String[] locationParts = data.split(" ");
        String lastLocation = locationParts[locationParts.length - 1];  // 마지막 지역 정보만 추출

        // TextView 업데이트
        locationText.setText(lastLocation);
    }


    @Override
    public void onSetDistrictFailure(String result) {
        Toast.makeText(getContext(), "지역 설정 실패: " + result, Toast.LENGTH_SHORT).show();
    }

    // 게시물 목록 처리
    @Override
    public void onPostsLoaded(List<PostDto> data) {
        postList.clear();
        postList.addAll(data);
        postAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPostClick(PostDto post) {
        Intent intent = new Intent(getContext(), PostDetailActivity.class);
        intent.putExtra("postId", post.getPostId());
        startActivity(intent);
    }

    // 필수 메서드들 빈 처리
    @Override
    public void onPostCreated(String responseMessage) {}

    @Override
    public void onPostCreateFailure(String result) {}

    @Override
    public void onPostDetailLoaded(PostDetailDto postDetail) {}

    @Override
    public void onCommentsLoaded(List<CommentDto> comments) {}
}
