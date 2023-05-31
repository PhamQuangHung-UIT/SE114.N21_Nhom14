package com.example.splus.my_fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.splus.CourseActivity;
import com.example.splus.R;
import com.example.splus.my_adapter.CourseAdapter;
import com.example.splus.my_data.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);

        RecyclerView recyclerList = view.findViewById(R.id.recyclerListCourseFragment);
        recyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        CourseAdapter courseAdapter = new CourseAdapter(getAllCourses(), this::onClickGoToCourseActivity);
        recyclerList.setAdapter(courseAdapter);

        ImageButton imageButtonNotif = view.findViewById(R.id.buttonNotificationCourseFragment);
        imageButtonNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToNotification();
            }
        });

        EditText editSearchBox = view.findViewById(R.id.searchBoxCourseFragment);
        // handle searching
        return view;
    }

    private void onClickGoToNotification() {
    }

    private void onClickGoToCourseActivity(Course course) {
        Intent intent = new Intent(this.getActivity(), CourseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("course", course);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @NonNull
    private List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();

        courseList.add(new Course(
                0,
                "Nhập môn Toán học",
                "Splus"
        ));

        courseList.add( new Course(
                0,
                "Giải tích",
                "ThS. Lê Hoàng Tuấn"
        ));

        courseList.add( new Course(
                0,
                "Đại số tuyến tính",
                "TS. Dương Ngọc Hảo"
        ));

        return courseList;
    }
}