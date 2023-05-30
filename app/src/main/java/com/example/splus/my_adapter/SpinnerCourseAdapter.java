package com.example.splus.my_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.splus.R;
import com.example.splus.my_data.Course;

import java.util.List;

public class SpinnerCourseAdapter extends ArrayAdapter<Course> {
    public SpinnerCourseAdapter(@NonNull Context context, int resource, @NonNull List<Course> objects) {
        super(context, resource, objects);
    }

    @Nullable
    @Override
    public Course getItem(int position) {
        return super.getItem(position);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_selected, parent, false);
        TextView textCourseSpinner = convertView.findViewById(R.id.textItemCourseSelected);
        Course course = this.getItem(position);
        if (course != null) {
            textCourseSpinner.setText(course.getCourseName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_spinner, parent, false);
        TextView textCourseSpinner = convertView.findViewById(R.id.textItemCourseSpinner);
        Course course = this.getItem(position);
        if (course != null) {
            textCourseSpinner.setText(course.getCourseName());
        }
        return convertView;
    }
}
