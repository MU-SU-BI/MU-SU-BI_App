package com.example.musubi.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musubi.R;
import com.example.musubi.model.dto.CommentDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private final List<CommentDto> commentList;

    public CommentAdapter(List<CommentDto> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentDto comment = commentList.get(position);
        holder.authorTextView.setText(comment.getAuthorName());
        holder.contentTextView.setText(comment.getContent());

        // 댓글 작성 시간을 포맷
        String formattedDate = formatDate(comment.getCreateAt());
        holder.dateTextView.setText(formattedDate);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView authorTextView, contentTextView, dateTextView;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            authorTextView = itemView.findViewById(R.id.tv_comment_author);
            contentTextView = itemView.findViewById(R.id.tv_comment_content);
            dateTextView = itemView.findViewById(R.id.tv_comment_date);
        }
    }

    // 댓글 작성 시간을 포맷하는 메서드
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
}
