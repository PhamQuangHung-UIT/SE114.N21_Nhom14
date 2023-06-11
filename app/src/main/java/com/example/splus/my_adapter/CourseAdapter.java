package com.example.splus.my_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.R;
import com.example.splus.my_data.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

  private List<Course> courseList;
  private OnItemClickListener listener;

  public CourseAdapter(List<Course> courseList, OnItemClickListener listener) {
    this.courseList = courseList;
    this.listener = listener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Course course = courseList.get(position);
    holder.bind(course, listener);
  }

  @Override
  public int getItemCount() {
    return courseList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewCourseName;
    private TextView textViewCreatorName;
    private Button buttonEdit;
    private Button buttonDelete;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      textViewCourseName = itemView.findViewById(R.id.courseNameTextView);
      textViewCreatorName = itemView.findViewById(R.id.creatorTextView);
      buttonEdit = itemView.findViewById(R.id.editButton);
      buttonDelete = itemView.findViewById(R.id.deleteButton);
    }

    public void bind(Course course, OnItemClickListener listener) {
      textViewCourseName.setText(course.getCourseName());
      textViewCreatorName.setText(course.getCreatorName());

      buttonEdit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          listener.onEditButtonClick(course);
        }
      });

      buttonDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          listener.onDeleteButtonClick(course);
        }
      });
    }
  }

  public interface OnItemClickListener {
    void onItemClick(Course course);

    void onEditButtonClick(Course course);

    void onDeleteButtonClick(Course course);
  }
}
