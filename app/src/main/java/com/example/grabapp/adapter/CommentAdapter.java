package com.example.grabapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grabapp.R;
import com.example.grabapp.model.Comment;

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
        holder.tvUserName.setText(comment.getUserName());
        holder.tvContent.setText(comment.getContent());
        holder.ratingBar.setRating(comment.getRating());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvContent;
        RatingBar ratingBar;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.txt_user_name);
            tvContent = itemView.findViewById(R.id.txt_comment);
            ratingBar = itemView.findViewById(R.id.rating_bar);
        }
    }
    public void updateComments(List<Comment> newComments) {
        this.commentList.clear();
        this.commentList.addAll(newComments);
        notifyDataSetChanged();
    }
}
