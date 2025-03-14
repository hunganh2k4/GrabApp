package com.example.grabapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grabapp.R;
import com.example.grabapp.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList) {
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
        Comment comment = commentList.get(position);
        holder.txtUserName.setText(comment.getUserName());
        holder.txtComment.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserName, txtComment;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txt_user_name);
            txtComment = itemView.findViewById(R.id.txt_comment);
        }
    }

    // Cập nhật danh sách bình luận
    public void updateComments(List<Comment> newComments) {
        if (commentList == null) {
            commentList = new ArrayList<>(); // Tránh lỗi null
        }

        commentList.clear();

        if (newComments != null) {
            commentList.addAll(newComments);
        }

        notifyDataSetChanged();
    }
}
