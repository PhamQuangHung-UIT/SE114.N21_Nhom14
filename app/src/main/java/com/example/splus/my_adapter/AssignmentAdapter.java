package com.example.splus.my_adapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
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

import org.json.JSONException;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ASSIGNMENT_OVERDUE = 1;
    private static final int TYPE_ASSIGNMENT_ONGOING = 2;

    private List<Assignment> listAssignment;

    private final IClickAssignmentListener iClickAssignmentListener;

    public AssignmentAdapter(IClickAssignmentListener iClickAssignmentListener) {
        this.iClickAssignmentListener = iClickAssignmentListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Assignment> listAssignment) {
        this.listAssignment = listAssignment;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (TYPE_ASSIGNMENT_OVERDUE == viewType) {
            // TAB Assignment finished
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assignment_overdue, parent, false);
            return new OverdueAssignmentViewHolder(view);
        } else if (TYPE_ASSIGNMENT_ONGOING == viewType) {
            // TAB Assignment unfinished
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assignment_ongoing, parent, false);
            return new OngoingAssignmentViewHolder(view);
        } else {
            return null;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Assignment assignment = listAssignment.get(position);
        if (assignment == null) {
            return;
        }

        if (TYPE_ASSIGNMENT_OVERDUE == holder.getItemViewType()) {
            // bind view holder for Assignment finished
            OverdueAssignmentViewHolder ongoingAssignmentViewHolder = (OverdueAssignmentViewHolder) holder;

            ongoingAssignmentViewHolder.assignmentName.setText(assignment.getAssignName());
            ongoingAssignmentViewHolder.courseName.setText(assignment.getCourseName());

            if (assignment.isSubmitted()) {
                ongoingAssignmentViewHolder.status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_check_circle_24, 0, 0, 0);
                ongoingAssignmentViewHolder.status.setText(String.valueOf(R.string.text_submitted));
            } else {
                ongoingAssignmentViewHolder.status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_cancel_24, 0, 0, 0);
                ongoingAssignmentViewHolder.status.setText(String.valueOf(R.string.text_unsubmitted));
            }

            ongoingAssignmentViewHolder.assignmentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        iClickAssignmentListener.onClickAssignment(assignment);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } else if (TYPE_ASSIGNMENT_ONGOING == holder.getItemViewType()) {
            // bind view holder for Assignment unfinished
            OngoingAssignmentViewHolder overdueAssignmentViewHolder = (OngoingAssignmentViewHolder) holder;

            overdueAssignmentViewHolder.assignmentName.setText(assignment.getAssignName());
            overdueAssignmentViewHolder.courseName.setText(assignment.getCourseName());
            overdueAssignmentViewHolder.remainingTime.setText(assignment.getAssignTime());

            overdueAssignmentViewHolder.assignmentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        iClickAssignmentListener.onClickAssignment(assignment);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
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
        if (assignment.isExpired()) {
            return TYPE_ASSIGNMENT_OVERDUE;
        } else {
            return TYPE_ASSIGNMENT_ONGOING;
        }
    }

    public static class OverdueAssignmentViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout assignmentLayout;
        private final TextView assignmentName;
        private final TextView courseName;
        private final TextView status;

        public OverdueAssignmentViewHolder(@NonNull View view) {
            super(view);
            assignmentLayout = view.findViewById(R.id.relativeItemAssignmentOverdue);
            assignmentName = view.findViewById(R.id.textNameItemAssignmentOverdue);
            courseName = view.findViewById(R.id.textClassItemAssignmentOverdue);
            status = view.findViewById(R.id.textStatusItemAssignmentOverdue);
        }
    }

    public static class OngoingAssignmentViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout assignmentLayout;
        private final TextView assignmentName;
        private final TextView courseName;
        private final TextView remainingTime;
        public OngoingAssignmentViewHolder(@NonNull View view) {
            super(view);
            assignmentLayout = view.findViewById(R.id.relativeItemAssignmentOngoing);
            assignmentName = view.findViewById(R.id.textNameItemAssignmentOngoing);
            courseName = view.findViewById(R.id.textClassItemAssignmentOngoing);
            remainingTime = view.findViewById(R.id.textTimeItemAssignmentOngoing);
        }
    }
}
