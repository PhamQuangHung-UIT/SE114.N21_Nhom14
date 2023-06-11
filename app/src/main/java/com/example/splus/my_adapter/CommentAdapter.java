package com.example.splus.my_adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.R;
import com.example.splus.my_data.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    public List<Comment> commentList;
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.textView_LikeCount.setText(Integer.toString(comment.getLikeCount()));
        holder.textView_DislikeCount.setText(Integer.toString(comment.getDislikeCount()));

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView textView_DisplayName;
        public TextView textView_CreatedDateOffset;
        public TextView textView_LikeCount, textView_DislikeCount;
        public CheckBox checkBox_Like, checkBox_Dislike;
        public Button button_ShowAllReplies;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_DisplayName = itemView.findViewById(R.id.textView_DisplayName);
            textView_CreatedDateOffset = itemView.findViewById(R.id.textView_CreatedDateOffset);
            textView_LikeCount = itemView.findViewById(R.id.textView_LikeCount);
            textView_DislikeCount = itemView.findViewById(R.id.textView_DislikeCount);
            checkBox_Like = itemView.findViewById(R.id.checkBox_Like);
            checkBox_Dislike = itemView.findViewById(R.id.checkBox_Dislike);
            button_ShowAllReplies = itemView.findViewById(R.id.button_showAllReplies);
        }
    }
}
