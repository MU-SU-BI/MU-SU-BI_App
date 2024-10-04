package com.example.musubi.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musubi.R;
import com.example.musubi.model.dto.PostDto;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<PostDto> postList;

    public PostAdapter(List<PostDto> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostDto post = postList.get(position);
        holder.titleTextView.setText(post.getTitle());
        holder.contentTextView.setText(post.getContent());
        holder.commentCountTextView.setText(String.valueOf(post.getCommentCount()));

        if (post.hasImage()) {
            holder.imageView.setVisibility(View.VISIBLE);
            // 이미지 로딩 처리 (예: Glide 사용)
        } else {
            holder.imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, contentTextView, commentCountTextView;
        ImageView imageView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text);
            contentTextView = itemView.findViewById(R.id.content_text);
            commentCountTextView = itemView.findViewById(R.id.comment_count_text);
            imageView = itemView.findViewById(R.id.post_image);
        }
    }
}
