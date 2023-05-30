package com.example.splus.my_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.R;
import com.example.splus.my_data.Course;
import com.example.splus.my_interface.IClickCourseListener;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.UserViewHolder> {

    private final List<Course> courseList;
    private final IClickCourseListener iClickCourseListener;

    public CourseAdapter(List<Course> courseList, IClickCourseListener iClickCourseListener) {
        this.courseList = courseList;
        this.iClickCourseListener = iClickCourseListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Course course = courseList.get(position);
        if (course == null) {
            return;
        }
        holder.courseName.setText(course.getCourseName());
        holder.teacherName.setText(course.getTeacherName());

        holder.courseItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickCourseListener.onClickCourse(course);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (courseList != null) {
            return courseList.size();
        }
        return 0;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public IClickCourseListener getiClickCourseListener() {
        return iClickCourseListener;
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout courseItem;
        private final TextView courseName;
        private final TextView teacherName;

        public UserViewHolder(@NonNull View view) {
            super(view);
            courseItem = view.findViewById(R.id.relativeItemCourse);
            courseName = view.findViewById(R.id.textClassNameItemCourse);
            teacherName = view.findViewById(R.id.textTeacherNameItemCourse);
        }
    }
}
