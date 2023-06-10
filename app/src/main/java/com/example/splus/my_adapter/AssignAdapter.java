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

import org.json.JSONException;

import java.util.List;

public class AssignAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Assignment> listAssignment;

    private final IClickAssignmentListener iClickAssignmentListener;

    public AssignAdapter(List<Assignment> listAssignment, IClickAssignmentListener iClickAssignmentListener) {
        this.listAssignment = listAssignment;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assignment_overdue, parent, false);
        return new AssignAdapter.GivenAssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Assignment assignment = listAssignment.get(position);
        if (assignment == null) {
            return;
        }

        AssignAdapter.GivenAssignmentViewHolder givenAssignmentViewHolder = (AssignAdapter.GivenAssignmentViewHolder) holder;

        givenAssignmentViewHolder.assignmentName.setText(assignment.getAssignName());
        givenAssignmentViewHolder.courseName.setText(assignment.getCourseName());
        givenAssignmentViewHolder.timestampt.setText(assignment.getAssignDeadline());

        givenAssignmentViewHolder.assignmentLayout.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public int getItemCount() {
        if (listAssignment != null) {
            return listAssignment.size();
        }
        return 0;
    }

    public static class GivenAssignmentViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout assignmentLayout;
        private final TextView assignmentName;
        private final TextView courseName;
        private final TextView timestampt;

        public GivenAssignmentViewHolder(@NonNull View view) {
            super(view);
            assignmentLayout = view.findViewById(R.id.relativeItemAssignmentGiven);
            assignmentName = view.findViewById(R.id.textNameItemAssignmentGiven);
            courseName = view.findViewById(R.id.textClassItemAssignmentGiven);
            timestampt = view.findViewById(R.id.textTimestampItemAssignmentGiven);
        }
    }
}
