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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.splus.CreateAssignmentActivity;
import com.example.splus.MainActivity;
import com.example.splus.NotificationActivity;
import com.example.splus.R;
import com.example.splus.SummaryActivity;
import com.example.splus.my_adapter.AssignAdapter;
import com.example.splus.my_adapter.SpinnerCourseAdapter;
import com.example.splus.my_data.Account;
import com.example.splus.my_data.Assignment;
import com.example.splus.my_data.Course;

import java.util.ArrayList;
import java.util.List;

public class AssignFragment extends Fragment {

    private Spinner spinnerChooseClass;
    private RecyclerView listAssignment;
    private AssignAdapter assignAdapter;
    private SpinnerCourseAdapter spinnerCourseAdapter;

    private Button buttonCreateAssignment;

    public AssignFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_assign, container, false);

        MainActivity activity = (MainActivity) getActivity();

        Account account = activity.getAccount();

        ImageButton imageButtonNotif = view.findViewById(R.id.buttonNotificationAssignFragment);
        imageButtonNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToNotification();
            }
        });

        spinnerChooseClass = view.findViewById(R.id.spinnerAssignFragment);
        listAssignment = view.findViewById(R.id.recyclerListAssignFragment);
        listAssignment.setLayoutManager(new LinearLayoutManager(getActivity()));
        assignAdapter = new AssignAdapter(getAllAssignment(), this::onClickGoToSummary);

        spinnerCourseAdapter = new SpinnerCourseAdapter(requireActivity(), R.layout.item_course_selected, getListCourse());
        spinnerChooseClass.setAdapter(spinnerCourseAdapter);

        spinnerChooseClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                assignAdapter.setData(getAssignment(spinnerCourseAdapter.getItem(position).getCourseId()));
                Toast.makeText(parent.getContext(), spinnerCourseAdapter.getItem(position).getCourseName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                assignAdapter.setData(getAllAssignment());
            }
        });

        buttonCreateAssignment = view.findViewById(R.id.buttonAssignFragment);
        buttonCreateAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateAssignmentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("teacher_account", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    private void onClickGoToNotification() {
        Intent intent = new Intent(this.getActivity(), NotificationActivity.class);
        startActivity(intent);
    }

    @NonNull
    private List<Assignment> getAssignment(int courseID) {
        // Query SELECT * FROM assignment WHERE assign_id= :classID
        List<Assignment> assignmentList = new ArrayList<>();

        Assignment example = new Assignment(
                1,
                "Kiểm tra cuối khoá",
                10,
                "30m",
                "2023-06-10 00:00:00",
                "",
                courseID,
                "Nhập môn toán học",
                0,
                0
        );
        assignmentList.add(example);
        assignmentList.add(example);
        assignmentList.add(example);
        assignmentList.add(example);
        assignmentList.add(example);
        return assignmentList;
    }

    @NonNull
    private List<Assignment> getAllAssignment() {
        List<Assignment> assignmentList = new ArrayList<>();

        Assignment example = new Assignment(
                1,
                "Kiểm tra cuối khoá",
                10,
                "30m",
                "2023-06-10 00:00:00",
                "",
                0,
                "Nhập môn toán học",
                0,
                0
        );
        assignmentList.add(example);
        assignmentList.add(example);
        assignmentList.add(example);
        assignmentList.add(example);
        assignmentList.add(example);

        return assignmentList;
    }

    @NonNull
    private List<Course> getListCourse() {
        List<Course> courseList = new ArrayList<>();

        Course classExample = new Course(
                0,
                getString(R.string.text_class_name),
                getString(R.string.teacher_name_example)
        );

        courseList.add(classExample);

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

    private void onClickGoToSummary(Assignment assignment) {
        Intent intent = new Intent(this.getActivity(), SummaryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("assignment", assignment);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}