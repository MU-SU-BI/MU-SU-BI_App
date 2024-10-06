package com.example.musubi.presenter.contract;

import com.example.musubi.model.dto.CommentDto;
import com.example.musubi.model.dto.PostDetailDto;
import com.example.musubi.model.dto.PostDto;
import java.util.List;

public interface CommunityContract {
    interface View {
        void onPostsLoaded(List<PostDto> data);
        void onPostDetailLoaded(PostDetailDto postDetail);
        void onSetDistrictSuccess(String responseMessage);
        void onSetDistrictFailure(String result);
        void onPostCreated(String responseMessage);
        void onPostCreateFailure(String result);

        void onCommentsLoaded(List<CommentDto> data);
    }
}
