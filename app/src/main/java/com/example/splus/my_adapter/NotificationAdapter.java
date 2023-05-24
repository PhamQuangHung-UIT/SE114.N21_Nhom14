package com.example.splus.my_adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.R;
import com.example.splus.my_data.NotificationData;
import com.example.splus.my_interface.IClickNotificationListener;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_NOTIFICATION_STUDY = 1;
    private static final int TYPE_NOTIFICATION_OTHER = 2;

    private List<NotificationData> listNotification;

    private IClickNotificationListener iClickNotificationListener;

    public NotificationAdapter(List<NotificationData> list, IClickNotificationListener iClickNotificationListener) {
        this.listNotification = list;
        this.iClickNotificationListener = iClickNotificationListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (TYPE_NOTIFICATION_STUDY == viewType) {
            // TAB homework finished
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
            return new StudyNotificationViewHolder(view);
        } else if (TYPE_NOTIFICATION_OTHER == viewType) {
            // TAB homework unfinished
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
            return new OtherNotificationViewHolder(view);
        }
        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NotificationData notificationData = listNotification.get(position);
        if (notificationData == null) {
            return;
        }

        if (TYPE_NOTIFICATION_STUDY == holder.getItemViewType()) {
            // bind view holder for notification relate to study
            StudyNotificationViewHolder studyNotificationViewHolder = (StudyNotificationViewHolder) holder;

            studyNotificationViewHolder.notifImage.setImageResource(R.drawable.splus_icon);
            studyNotificationViewHolder.notifTitle.setText(notificationData.getTitle());
            studyNotificationViewHolder.notifContent.setText(notificationData.getContent());
            studyNotificationViewHolder.notifTime.setText(notificationData.getTimestampt());

            studyNotificationViewHolder.notifLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickNotificationListener.onClickNotification(notificationData);
                }
            });
        } else if (TYPE_NOTIFICATION_OTHER == holder.getItemViewType()) {
            // bind view holder for other notifications
            OtherNotificationViewHolder otherNotificationViewHolder = (OtherNotificationViewHolder) holder;

            otherNotificationViewHolder.notifImage.setImageResource(R.drawable.splus_icon);
            otherNotificationViewHolder.notifTitle.setText(notificationData.getTitle());
            otherNotificationViewHolder.notifContent.setText(notificationData.getContent());
            otherNotificationViewHolder.notifTime.setText(notificationData.getTimestampt());

            otherNotificationViewHolder.notifLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickNotificationListener.onClickNotification(notificationData);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (listNotification != null) {
            return listNotification.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        NotificationData notificationData = this.listNotification.get(position);
        if (notificationData.isStudyNotification()) {
            return TYPE_NOTIFICATION_STUDY;
        } else {
            return TYPE_NOTIFICATION_OTHER;
        }
    }
    public static class StudyNotificationViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout notifLayout;
        private final ImageView notifImage;
        private final TextView notifTitle;
        private final TextView notifContent;
        private final TextView notifTime;

        public StudyNotificationViewHolder(@NonNull View viewNotification) {
            super(viewNotification);
            notifLayout = viewNotification.findViewById(R.id.relativeItemNotification);
            notifImage = viewNotification.findViewById(R.id.imageNotificationActivity);
            notifTitle = viewNotification.findViewById(R.id.textTitleNotificationAct);
            notifContent = viewNotification.findViewById(R.id.textContentNotificationAct);
            notifTime = viewNotification.findViewById(R.id.textTimeNotificationAct);
        }
    }

    public static class OtherNotificationViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout notifLayout;
        private final ImageView notifImage;
        private final TextView notifTitle;
        private final TextView notifContent;
        private final TextView notifTime;
        public OtherNotificationViewHolder(@NonNull View viewNotification) {
            super(viewNotification);
            notifLayout = viewNotification.findViewById(R.id.relativeItemNotification);
            notifImage = viewNotification.findViewById(R.id.imageNotificationActivity);
            notifTitle = viewNotification.findViewById(R.id.textTitleNotificationAct);
            notifContent = viewNotification.findViewById(R.id.textContentNotificationAct);
            notifTime = viewNotification.findViewById(R.id.textTimeNotificationAct);
        }
    }
}
