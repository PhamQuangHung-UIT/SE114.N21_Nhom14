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
import com.example.splus.my_data.Assignment;
import com.example.splus.my_interface.IClickAssignmentListener;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ASSIGNMENT_FINISHED = 1;
    private static final int TYPE_ASSIGNMENT_UNFINISHED = 2;

    private List<Assignment> listAssignment;

    private final IClickAssignmentListener iClickAssignmentListener;

    public AssignmentAdapter(List<Assignment> listAssignment, IClickAssignmentListener iClickAssignmentListener) {
        this.listAssignment = listAssignment;
        this.iClickAssignmentListener = iClickAssignmentListener;
    }

    public void setData(List<Assignment> listAssignment) {
        this.listAssignment = listAssignment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (TYPE_ASSIGNMENT_FINISHED == viewType) {
            // TAB Assignment finished
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hw_finished, parent, false);
            return new FinishedAssignmentViewHolder(view);
        } else if (TYPE_ASSIGNMENT_UNFINISHED == viewType) {
            // TAB Assignment unfinished
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hw_unfinished, parent, false);
            return new UnfinishedAssignmentViewHolder(view);
        }
        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Assignment assignment = listAssignment.get(position);
        if (assignment == null) {
            return;
        }

        if (TYPE_ASSIGNMENT_FINISHED == holder.getItemViewType()) {
            // bind view holder for Assignment finished
            FinishedAssignmentViewHolder finishedAssignmentViewHolder = (FinishedAssignmentViewHolder) holder;

            finishedAssignmentViewHolder.assignmentName.setText(assignment.getAssignName());
            finishedAssignmentViewHolder.courseName.setText(assignment.getCourseName());
            finishedAssignmentViewHolder.timestampt.setText(assignment.getAssignDeadline());
            finishedAssignmentViewHolder.point.setText(Double.toString(assignment.getResult()));

            finishedAssignmentViewHolder.assignmentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickAssignmentListener.onClickAssignment(assignment);
                }
            });
        } else if (TYPE_ASSIGNMENT_UNFINISHED == holder.getItemViewType()) {
            // bind view holder for Assignment unfinished
            UnfinishedAssignmentViewHolder unfinishedAssignmentViewHolder = (UnfinishedAssignmentViewHolder) holder;

            unfinishedAssignmentViewHolder.assignmentName.setText(assignment.getAssignName());
            unfinishedAssignmentViewHolder.courseName.setText(assignment.getCourseName());
            unfinishedAssignmentViewHolder.remainingTime.setText(assignment.getAssignTime());

            unfinishedAssignmentViewHolder.assignmentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickAssignmentListener.onClickAssignment(assignment);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (listAssignment != null) {
            return listAssignment.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        Assignment assignment = this.listAssignment.get(position);
        if (assignment.isFinish()) {
            return TYPE_ASSIGNMENT_FINISHED;
        } else {
            return TYPE_ASSIGNMENT_UNFINISHED;
        }
    }

    public static class FinishedAssignmentViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout assignmentLayout;
        private final TextView assignmentName;
        private final TextView courseName;
        private final TextView timestampt;
        private final TextView point;

        public FinishedAssignmentViewHolder(@NonNull View viewFinishedAssignment) {
            super(viewFinishedAssignment);
            assignmentLayout = viewFinishedAssignment.findViewById(R.id.relativeItemHwFinished);
            assignmentName = viewFinishedAssignment.findViewById(R.id.textHwNameFinishedHwFragment);
            courseName = viewFinishedAssignment.findViewById(R.id.textClassNameFinishedHwFragment);
            timestampt = viewFinishedAssignment.findViewById(R.id.textTimestampFinishedHwFragment);
            point = viewFinishedAssignment.findViewById(R.id.textPointFinishedHwFragment);
        }
    }

    public static class UnfinishedAssignmentViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout assignmentLayout;
        private final TextView assignmentName;
        private final TextView courseName;
        private final TextView remainingTime;
        public UnfinishedAssignmentViewHolder(@NonNull View viewUnfinishedAssignment) {
            super(viewUnfinishedAssignment);
            assignmentLayout = viewUnfinishedAssignment.findViewById(R.id.relativeItemHwUnfinished);
            assignmentName = viewUnfinishedAssignment.findViewById(R.id.textHwNameUnfinishedHwFragment);
            courseName = viewUnfinishedAssignment.findViewById(R.id.textClassNameUnfinishedHwFragment);
            remainingTime = viewUnfinishedAssignment.findViewById(R.id.textRemainingTimeUnfinishedHwFragment);
        }
    }
}
