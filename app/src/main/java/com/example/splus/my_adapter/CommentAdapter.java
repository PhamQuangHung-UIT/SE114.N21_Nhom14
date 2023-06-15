package com.example.splus.my_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    public static final int COMMENT = 0;
    public static final int REPLY = 1;
    private final List<Comment> commentList;
    private final Context context;
    private OnRepliesListener listener;

    //private String currentUserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.commentList = commentList;
        this.context = context;
    }
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        applyViewHolder(holder, comment, COMMENT);
    }

    @SuppressLint("SetTextI18n")
    public void applyViewHolder(@NonNull CommentViewHolder holder, Comment comment, int itemType) {
        holder.textView_DisplayName.setText(comment.getOwnerDisplayName());
        holder.textView_CommentText.setText(comment.getText());
        holder.textView_LikeCount.setText(Integer.toString(comment.getLikeCount()));
        holder.textView_DislikeCount.setText(Integer.toString(comment.getDislikeCount()));
        if (itemType != REPLY && comment.getReplyCount() > 0) {
            holder.button_ShowAllReplies.setVisibility(View.VISIBLE);
            if (comment.getReplyCount() > 1)
                holder.button_ShowAllReplies.setText(context.getString(R.string.n_replies, comment.getReplyCount()));
            else holder.button_ShowAllReplies.setText(context.getString(R.string.one_reply));
        }
        holder.button_ShowAllReplies.setOnClickListener(view -> listener.onShowReply(comment));
        holder.button_Reply.setOnClickListener(view -> listener.onCreateReply(comment));
        if (comment.isLike() != null) {
            if (comment.isLike())
                holder.checkBox_Like.setChecked(true);
            else holder.checkBox_Dislike.setChecked(false);
        }
        holder.checkBox_Like.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (!isChecked)
                comment.unlike();
            else {
                if (holder.checkBox_Dislike.isChecked()) {
                    holder.checkBox_Dislike.setChecked(false);
                    comment.undislike();
                }
                comment.like();
            }
        });
        holder.checkBox_Dislike.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (!isChecked)
                comment.undislike();
            else {
                if (holder.checkBox_Like.isChecked()) {
                    holder.checkBox_Like.setChecked(false);
                    comment.unlike();
                }
                comment.dislike();
            }
        });
        Calendar serverCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        serverCalendar.setTime(comment.getCreatedDate().toDate());
        Calendar localCalendar = Calendar.getInstance();
        holder.textView_CreatedDateOffset.setText(getTimeOffsetString(localCalendar, serverCalendar));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public void setOnRepliesListener(OnRepliesListener listener) {
        this.listener = listener;
    }

    private String getTimeOffsetString(Calendar current, Calendar commentDate) {
        int second = current.get(Calendar.SECOND) - commentDate.get(Calendar.SECOND);
        boolean isBorrowed = second < 0;
        int minute = current.get(Calendar.MINUTE) - commentDate.get(Calendar.MINUTE) - (isBorrowed ? 1 : 0);
        if (minute < 0) {
            minute = 60 - minute;
            isBorrowed = true;
        } else isBorrowed = false;
        int hour = current.get(Calendar.HOUR) - commentDate.get(Calendar.HOUR) - (isBorrowed ? 1 : 0);
        if (hour < 0) {
            hour = 24 - hour;
            isBorrowed = true;
        } else isBorrowed = false;
        int day = current.get(Calendar.DAY_OF_MONTH) - commentDate.get(Calendar.DAY_OF_MONTH) - (isBorrowed ? 1 : 0);
        if (day < 0) {
            day = commentDate.getActualMaximum(Calendar.DAY_OF_MONTH) - day;
            isBorrowed = true;
        } else isBorrowed = false;
        int month = current.get(Calendar.MONTH) - commentDate.get(Calendar.MONTH) - (isBorrowed ? 1 : 0);
        if (month < 0) {
            month = 12 - month;
            isBorrowed = true;
        } else isBorrowed = false;
        int year = current.get(Calendar.YEAR) - commentDate.get(Calendar.YEAR) - (isBorrowed ? 1 : 0);

        if (year > 1) return context.getString(R.string.n_years_ago, year);
        if (year == 1) return context.getString(R.string.one_year_ago);
        if (month > 1) return context.getString(R.string.n_months_ago, month);
        if (month == 1) return context.getString(R.string.one_month_ago);
        if (day > 1) return context.getString(R.string.n_days_ago, day);
        if (day == 1) return context.getString(R.string.yesterday);
        if (hour > 1) return context.getString(R.string.n_hours_ago, hour);
        if (hour == 1) return context.getString(R.string.one_hour_ago);
        if (minute > 2) return context.getString(R.string.n_mins_ago, minute);
        return context.getString(R.string.just_now);
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView textView_DisplayName;
        public TextView textView_CreatedDateOffset;

        public TextView textView_CommentText;
        public TextView textView_LikeCount, textView_DislikeCount;
        public CheckBox checkBox_Like, checkBox_Dislike;
        public Button button_ShowAllReplies, button_Reply;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_DisplayName = itemView.findViewById(R.id.textView_DisplayName);
            textView_CreatedDateOffset = itemView.findViewById(R.id.textView_CreatedDateOffset);
            textView_CommentText = itemView.findViewById(R.id.textView_CommentText);
            textView_LikeCount = itemView.findViewById(R.id.textView_LikeCount);
            textView_DislikeCount = itemView.findViewById(R.id.textView_DislikeCount);
            checkBox_Like = itemView.findViewById(R.id.checkBox_CommentLike);
            checkBox_Dislike = itemView.findViewById(R.id.checkBox_CommentDislike);
            button_ShowAllReplies = itemView.findViewById(R.id.button_showAllReplies);
            button_Reply = itemView.findViewById(R.id.button_Reply);
        }
    }

    public abstract static class OnRepliesListener {
        public void onShowReply(Comment comment) {}

        public abstract void onCreateReply(Comment comment);
    }
}
