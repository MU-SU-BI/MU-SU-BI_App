package com.example.musubi.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musubi.R;
import com.example.musubi.model.dto.PostDto;
import com.example.musubi.view.adapter.PostAdapter;

import java.util.List;

public class CommunityFragment extends Fragment {
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<PostDto> postList;  // 서버로부터 받아온 게시물 리스트

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        // 지역 정보를 표시하는 TextView
        TextView locationTextView = view.findViewById(R.id.location_text);
        locationTextView.setText("압량읍"); // 서버에서 받아온 데이터를 여기에 설정

        // RecyclerView 설정
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        postAdapter = new PostAdapter(postList);  // 서버에서 받은 게시물 리스트로 어댑터 초기화
        recyclerView.setAdapter(postAdapter);

        // 글 쓰기 버튼 설정
        Button writePostButton = view.findViewById(R.id.btn_write_post);
        writePostButton.setOnClickListener(v -> {
            // 글 쓰기 화면으로 이동하는 코드 추가
        });

        return view;
    }

    // 서버로부터 게시물 데이터를 받아오는 메서드 (예시)
    private void fetchPostsFromServer() {
        // Retrofit 등을 사용하여 서버로부터 게시물 리스트를 받아오는 코드 작성
        // 받아온 게시물 리스트를 postList에 설정하고, 어댑터에 변경 사항 알림
        postAdapter.notifyDataSetChanged();
    }
}
