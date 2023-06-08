package com.example.splus.my_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.ClassModel;
import com.example.splus.R;


import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private List<ClassModel> classList;

    public CourseAdapter(List<ClassModel> classList) {
        this.classList = classList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassModel classModel = classList.get(position);

        // Set the class data to the TextViews
        holder.textViewClassId.setText(classModel.getClassId());
        holder.textViewClassName.setText(classModel.getClassName());
        holder.textViewInstructorName.setText(classModel.getInstructorName());
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewClassId;
        private TextView textViewClassName;
        private TextView textViewInstructorName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewClassId = itemView.findViewById(R.id.textItemCourseSelected);
            textViewClassName = itemView.findViewById(R.id.textViewClassName);
            textViewInstructorName = itemView.findViewById(R.id.textViewInstructorName);
        }
    }
}
