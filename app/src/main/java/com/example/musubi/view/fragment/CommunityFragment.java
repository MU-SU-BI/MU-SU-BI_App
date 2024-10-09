package com.example.musubi.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musubi.R;
import com.example.musubi.model.dto.CommentDto;
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
    private TextView locationText;

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

        locationText = view.findViewById(R.id.location_text);

        Button writePostButton = view.findViewById(R.id.btn_write_post);
        writePostButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WritePostActivity.class);
            startActivityForResult(intent, 1001);
        });

        fetchLocationData();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            fetchLocationData();
        }
    }

    private void fetchLocationData() {
        String type = (User.getInstance().getId() == -1) ? "guardian" : "user";
        presenter.setMyDistrict(type);
    }

    @Override
    public void onSetDistrictSuccess(String data) {
        String[] locationParts = data.split(" ");
        String lastLocation = locationParts[locationParts.length - 1];
        setLocationText(lastLocation);
        fetchPostsFromServer();
    }

    private void fetchPostsFromServer() {
        String type = (User.getInstance().getId() == -1) ? "guardian" : "user";
        long userId = (User.getInstance().getId() == -1) ? Guardian.getInstance().getId() : User.getInstance().getId();
        presenter.getGuardianPosts(type, userId);
    }

    @Override
    public void onSetDistrictFailure(String result) {
        Toast.makeText(getContext(), "지역 설정 실패: " + result, Toast.LENGTH_SHORT).show();
    }

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

    @Override
    public void onPostCreated(String responseMessage) {}

    @Override
    public void onPostCreateFailure(String result) {}

    @Override
    public void onPostDetailLoaded(PostDetailDto postDetail) {}

    @Override
    public void onCommentsLoaded(List<CommentDto> comments) {}

    @Override
    public void setLocationText(String district) {
        locationText.setText(district);
    }
}
