package com.example.musubi.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musubi.R;
import com.example.musubi.model.dto.PostDto;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private final List<PostDto> postList;
    private final OnPostClickListener listener;

    public PostAdapter(List<PostDto> postList, OnPostClickListener listener) {
        this.postList = postList;
        this.listener = listener;
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
        holder.authorTextView.setText(post.getAuthorName());
        holder.dateTextView.setText(post.getCreateAt());

        holder.itemView.setOnClickListener(v -> listener.onPostClick(post));
    }

    @Override
    public int getItemCount() {
        return postList != null ? postList.size() : 0;
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, authorTextView, dateTextView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text);
            authorTextView = itemView.findViewById(R.id.author_text);
            dateTextView = itemView.findViewById(R.id.date_text);
        }
    }

    public interface OnPostClickListener {
        void onPostClick(PostDto post);
    }
}
