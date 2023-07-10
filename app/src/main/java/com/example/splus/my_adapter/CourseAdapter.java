package com.example.splus.my_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.R;
import com.example.splus.my_data.Course;


import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

  private List<Course> courseList;

  public CourseAdapter(List<Course> courseList) {
    this.courseList = courseList;
  }

  @NonNull
  @Override
  public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
    return new CourseViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
    Course course = courseList.get(position);
    holder.bindData(course);
  }

  @Override
  public int getItemCount() {
    return courseList.size();
  }

  public class CourseViewHolder extends RecyclerView.ViewHolder {
    private TextView courseIDTextView;
    private TextView courseNameTextView;
    private TextView creatorTextView;
    private TextView creationTimeTextView;
    private TextView studentCountTextView;

    public CourseViewHolder(@NonNull View itemView) {
      super(itemView);

      courseIDTextView = itemView.findViewById(R.id.courseIdTextView);
      courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
      creatorTextView = itemView.findViewById(R.id.creatorTextView);
      creationTimeTextView = itemView.findViewById(R.id.creationTimeTextView);
      studentCountTextView = itemView.findViewById(R.id.studentCountTextView);
    }

    public void bindData(Course course) {
      courseIDTextView.setText("ID: " + course.getCourseId());
      courseNameTextView.setText(course.getCourseName());
      creatorTextView.setText("Created by: " + course.getCreatorName());
      creationTimeTextView.setText("Created at: " + course.getCreationTime());
      studentCountTextView.setText("Students: " + course.getStudentCount());
    }
  }
}
