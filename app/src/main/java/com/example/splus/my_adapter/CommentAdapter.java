package com.example.splus.my_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.R;
import com.example.splus.my_class.ReplyToOwnerSpan;
import com.example.splus.my_data.Comment;
import com.example.splus.my_firestore_helper.CommentFirestoreHelper;
import com.example.splus.my_firestore_helper.FirebaseStorageHelper;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class CommentAdapter extends ListAdapter<Comment, CommentAdapter.CommentViewHolder> {

    public static final int COMMENT = 0;
    public static final int REPLY = 1;

    public static final DiffUtil.ItemCallback<Comment> DIFF_CALLBACK = new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
            return oldItem.equals(newItem);
        }
    };
    private final Context context;
    private CommentOptionListener listener;

    public CommentAdapter(Context context) {
        super(DIFF_CALLBACK);
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
        Comment comment = getCurrentList().get(position);
        applyViewHolder(holder, comment, COMMENT);
    }

    @SuppressLint("SetTextI18n")
    public void applyViewHolder(@NonNull CommentViewHolder holder, Comment comment, int itemType) {
        // previous: state[0], current: state[1]
        int[] state = new int[2];
        // Display name
        FirebaseStorageHelper.updateAvatar(comment.getOwnerId(), holder.imageView_Avatar);
        holder.textView_DisplayName.setText(comment.getOwnerDisplayName());
        // Comment text
        Editable editable = new Editable.Factory().newEditable(comment.getText());
        if (comment.getReplyOwnerId() != null) {
            ReplyToOwnerSpan span = new ReplyToOwnerSpan(comment.getReplyOwnerId());
            editable.setSpan(span, 0 , comment.getReplyOwnerName().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        holder.textView_CommentText.setText(editable);
        // Like count
        holder.textView_LikeCount.setText(Integer.toString(comment.getLikeCount()));
        // Dislike count
        holder.textView_DislikeCount.setText(Integer.toString(comment.getDislikeCount()));
        // Show all replies button
        if (itemType != REPLY && comment.getReplyCount() > 0) {
            holder.button_ShowAllReplies.setVisibility(View.VISIBLE);
            if (comment.getReplyCount() > 1)
                holder.button_ShowAllReplies.setText(context.getString(R.string.n_replies, comment.getReplyCount()));
            else holder.button_ShowAllReplies.setText(context.getString(R.string.one_reply));
        }
        holder.button_ShowAllReplies.setOnClickListener(view -> listener.onShowReply(comment));
        holder.button_Reply.setOnClickListener(view -> listener.onCreateReply(comment));

        if (comment.getLike() != null) {
            if (comment.getLike()) {
                holder.checkBox_Like.setChecked(true);
                state[1] = CommentFirestoreHelper.LIKE;
            } else {
                holder.checkBox_Dislike.setChecked(true);
                state[1] = CommentFirestoreHelper.DISLIKE;
            }
        }

        holder.checkBox_Like.setOnClickListener(view -> {
            state[0] = state[1];
            if (!holder.checkBox_Like.isChecked()) {
                comment.unlike();
                state[1] = CommentFirestoreHelper.NULL;
            }
            else {
                if (holder.checkBox_Dislike.isChecked()) {
                    holder.checkBox_Dislike.setChecked(false);
                    comment.undislike();
                }
                comment.like();
                state[1] = CommentFirestoreHelper.LIKE;
            }
            holder.textView_LikeCount.setText(Integer.toString(comment.getLikeCount()));
            holder.textView_DislikeCount.setText(Integer.toString(comment.getDislikeCount()));
            listener.onLikeChange(comment, state[1], state[0]);
        });

        holder.checkBox_Dislike.setOnClickListener(view -> {
            state[0] = state[1];
            if (!holder.checkBox_Dislike.isChecked()) {
                comment.undislike();
                state[1] = CommentFirestoreHelper.NULL;
            }
            else {
                if (holder.checkBox_Like.isChecked()) {
                    holder.checkBox_Like.setChecked(false);
                    comment.unlike();
                }
                comment.dislike();
                state[1] = CommentFirestoreHelper.DISLIKE;
            }
            holder.textView_LikeCount.setText(Integer.toString(comment.getLikeCount()));
            holder.textView_DislikeCount.setText(Integer.toString(comment.getDislikeCount()));
            listener.onDislikeChange(comment, state[1], state[0]);
        });

        // Menu option
        MenuItem.OnMenuItemClickListener mListener = menuItem -> {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.edit)
                listener.onEdit(comment);
            else if (itemId == R.id.delete)
                listener.onDelete(comment);
            else return false;
            return true;
        };

        if (comment.getOwnerId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
            holder.button_CommentOption.setOnCreateContextMenuListener((contextMenu, view, contextMenuInfo) -> {
                MenuInflater inflater = new MenuInflater(context);
                inflater.inflate(R.menu.menu_comment_owner, contextMenu);
                for (int i = 0; i < contextMenu.size(); i++)
                    contextMenu.getItem(i).setOnMenuItemClickListener(mListener);
            });
        else holder.button_CommentOption.setVisibility(View.GONE);

        holder.button_CommentOption.setOnClickListener(View::showContextMenu);

        // Set last comment date
        Calendar serverCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        serverCalendar.setTime(comment.getCreatedDate().toDate());
        Calendar localCalendar = Calendar.getInstance();
        serverCalendar.setTimeZone(localCalendar.getTimeZone());
        holder.textView_CreatedDateOffset.setText(getTimeOffsetString(localCalendar, serverCalendar));
    }

    public void setCommentOptionListener(CommentOptionListener listener) {
        this.listener = listener;
    }

    private String getTimeOffsetString(Calendar current, Calendar commentDate) {
        int second = current.get(Calendar.SECOND) - commentDate.get(Calendar.SECOND);
        boolean isBorrowed = second < 0;
        int minute = current.get(Calendar.MINUTE) - commentDate.get(Calendar.MINUTE) - (isBorrowed ? 1 : 0);
        if (minute < 0) {
            minute = 60 + minute;
            isBorrowed = true;
        } else isBorrowed = false;
        int hour = current.get(Calendar.HOUR_OF_DAY) - commentDate.get(Calendar.HOUR_OF_DAY) - (isBorrowed ? 1 : 0);
        if (hour < 0) {
            hour = 24 + hour;
            isBorrowed = true;
        } else isBorrowed = false;
        int day = current.get(Calendar.DAY_OF_MONTH) - commentDate.get(Calendar.DAY_OF_MONTH) - (isBorrowed ? 1 : 0);
        if (day < 0) {
            day = commentDate.getActualMaximum(Calendar.DAY_OF_MONTH) + day;
            isBorrowed = true;
        } else isBorrowed = false;
        Log.d("DEBUG", isBorrowed ? "month_true" : "month_false");
        int month = current.get(Calendar.MONTH) - commentDate.get(Calendar.MONTH) - (isBorrowed ? 1 : 0);
        if (month < 0) {
            month = 12 + month;
            isBorrowed = true;
        } else isBorrowed = false;
        Log.d("DEBUG", isBorrowed ? "year_true" : "year_false");
        int year = current.get(Calendar.YEAR) - commentDate.get(Calendar.YEAR) - (isBorrowed ? 1 : 0);

        Log.d("DEBUG", String.format("%1$d %2$d %3$d %4$d %5$d %6$d", year, month, day, hour, minute, second));
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

    public interface CommentOptionListener {
        default void onShowReply(Comment comment) {}

        void onCreateReply(Comment comment);

        void onEdit(Comment comment);

        void onDelete(Comment comment);

        void onLikeChange(Comment comment, int state, int previousState);

        void onDislikeChange(Comment comment, int state, int previousState);
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView textView_DisplayName;
        public TextView textView_CreatedDateOffset;

        public TextView textView_CommentText;
        public TextView textView_LikeCount, textView_DislikeCount;
        public CheckBox checkBox_Like, checkBox_Dislike;
        public Button button_ShowAllReplies;
        public Button button_Reply;
        public ImageButton button_CommentOption;

        public ImageView imageView_Avatar;

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
            button_CommentOption = itemView.findViewById(R.id.imageButton_CommentOption);
            imageView_Avatar = itemView.findViewById(R.id.imageView_Avatar);
        }
    }
}
