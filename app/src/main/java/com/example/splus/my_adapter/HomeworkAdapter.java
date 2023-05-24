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

public class HomeworkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HOMEWORK_FINISHED = 1;
    private static final int TYPE_HOMEWORK_UNFINISHED = 2;

    private List<HomeworkData> listHomeworkData;

    private IClickHomeworkListener iClickHomeworkListener;

    public HomeworkAdapter(List<HomeworkData> list, IClickHomeworkListener iClickHomeworkListener) {
        this.listHomeworkData = list;
        this.iClickHomeworkListener = iClickHomeworkListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (TYPE_HOMEWORK_FINISHED == viewType) {
            // TAB homework finished
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hw_finished, parent, false);
            return new FinishedHomeworkViewHolder(view);
        } else if (TYPE_HOMEWORK_UNFINISHED == viewType) {
            // TAB homework unfinished
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hw_unfinished, parent, false);
            return new UnfinishedHomeworkViewHolder(view);
        }
        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HomeworkData homeworkData = listHomeworkData.get(position);
        if (homeworkData == null) {
            return;
        }

        if (TYPE_HOMEWORK_FINISHED == holder.getItemViewType()) {
            // bind view holder for homework finished
            FinishedHomeworkViewHolder finishedHomeworkViewHolder = (FinishedHomeworkViewHolder) holder;

            finishedHomeworkViewHolder.homeworkName.setText(homeworkData.getHomework_name());
            finishedHomeworkViewHolder.className.setText(homeworkData.getClass_name());
            finishedHomeworkViewHolder.timestampt.setText(homeworkData.getDeadline());
            finishedHomeworkViewHolder.point.setText(Double.toString(homeworkData.getPoint()));

            finishedHomeworkViewHolder.homeworkLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickHomeworkListener.onClickHomework(homeworkData);
                }
            });
        } else if (TYPE_HOMEWORK_UNFINISHED == holder.getItemViewType()) {
            // bind view holder for homework unfinished
            UnfinishedHomeworkViewHolder unfinishedHomeworkViewHolder = (UnfinishedHomeworkViewHolder) holder;

            unfinishedHomeworkViewHolder.homeworkName.setText(homeworkData.getHomework_name());
            unfinishedHomeworkViewHolder.className.setText(homeworkData.getClass_name());
            unfinishedHomeworkViewHolder.remainingTime.setText(homeworkData.getTime());

            unfinishedHomeworkViewHolder.homeworkLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickHomeworkListener.onClickHomework(homeworkData);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (listHomeworkData != null) {
            return listHomeworkData.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        HomeworkData homeworkData = this.listHomeworkData.get(position);
        if (homeworkData.isFinished()) {
            return TYPE_HOMEWORK_FINISHED;
        } else {
            return TYPE_HOMEWORK_UNFINISHED;
        }
    }

    public static class FinishedHomeworkViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout homeworkLayout;
        private final TextView homeworkName;
        private final TextView className;
        private final TextView timestampt;
        private final TextView point;

        public FinishedHomeworkViewHolder(@NonNull View viewFinishedHomework) {
            super(viewFinishedHomework);
            homeworkLayout = viewFinishedHomework.findViewById(R.id.relativeItemHwFinished);
            homeworkName = viewFinishedHomework.findViewById(R.id.textHwNameFinishedHwFragment);
            className = viewFinishedHomework.findViewById(R.id.textClassNameFinishedHwFragment);
            timestampt = viewFinishedHomework.findViewById(R.id.textTimestampFinishedHwFragment);
            point = viewFinishedHomework.findViewById(R.id.textPointFinishedHwFragment);
        }
    }
    
    public static class UnfinishedHomeworkViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout homeworkLayout;
        private final TextView homeworkName;
        private final TextView className;
        private final TextView remainingTime;
        public UnfinishedHomeworkViewHolder(@NonNull View viewUnfinishedHomework) {
            super(viewUnfinishedHomework);
            homeworkLayout = viewUnfinishedHomework.findViewById(R.id.relativeItemHwUnfinished);
            homeworkName = viewUnfinishedHomework.findViewById(R.id.textHwNameUnfinishedHwFragment);
            className = viewUnfinishedHomework.findViewById(R.id.textClassNameUnfinishedHwFragment);
            remainingTime = viewUnfinishedHomework.findViewById(R.id.textRemainingTimeUnfinishedHwFragment);
        }
    }
}
