package com.example.splus.my_adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.R;
import com.example.splus.my_data.HomeworkData;
import com.example.splus.my_interface.IClickHomeworkListener;

import java.util.List;

public class FinishedHwAdapter extends RecyclerView.Adapter<FinishedHwAdapter.UserViewHolder> {

    private final List<HomeworkData> mListHomework;
    private final IClickHomeworkListener iClickHomeworkListener;

    public FinishedHwAdapter(List<HomeworkData> mListHomework, IClickHomeworkListener iClickHomeworkListener) {
        this.mListHomework = mListHomework;
        this.iClickHomeworkListener = iClickHomeworkListener;
    }
    @NonNull
    @Override
    public FinishedHwAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hw_finished, parent, false);
        return new FinishedHwAdapter.UserViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FinishedHwAdapter.UserViewHolder holder, int position) {
        HomeworkData homeworkData = mListHomework.get(position);
        if (homeworkData == null) {
            return;
        }
        holder.homeworkName.setText(homeworkData.getHomework_name());
        holder.className.setText(homeworkData.getClass_name());
        holder.timestampt.setText(homeworkData.getDeadline());
        holder.point.setText(Double.toString(homeworkData.getPoint()));

        holder.homeworkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickHomeworkListener.onClickHomework(homeworkData);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListHomework != null) {
            return mListHomework.size();
        }
        return 0;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout homeworkLayout;
        private final TextView homeworkName;
        private final TextView className;
        private final TextView timestampt;
        private final TextView point;

        public UserViewHolder(@NonNull View homeworkDataView) {
            super(homeworkDataView);
            homeworkLayout = homeworkDataView.findViewById(R.id.relativeItemHwFinished);
            homeworkName = homeworkDataView.findViewById(R.id.textHwNameFinishedHwFragment);
            className = homeworkDataView.findViewById(R.id.textClassNameFinishedHwFragment);
            timestampt = homeworkDataView.findViewById(R.id.textTimestampFinishedHwFragment);
            point = homeworkDataView.findViewById(R.id.textPointFinishedHwFragment);
        }
    }
}
