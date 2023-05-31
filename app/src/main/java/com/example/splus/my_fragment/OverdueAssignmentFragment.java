package com.example.splus.my_fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.splus.DoHomeworkActivity;
import com.example.splus.R;
import com.example.splus.SubmissionActivity;
import com.example.splus.my_adapter.AssignmentAdapter;
import com.example.splus.my_data.Assignment;

import java.util.ArrayList;
import java.util.List;

public class OverdueAssignmentFragment extends Fragment {

    public OverdueAssignmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overdue_assignment, container, false);

        RecyclerView list = view.findViewById(R.id.recyclerOverdueAssignmentFragment);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));

        AssignmentAdapter finishedHwAdapter = new AssignmentAdapter(this::onClickGoToOverdueAssignment);
        finishedHwAdapter.setData(getListOverdueAssignment());

        list.setAdapter(finishedHwAdapter);
        return view;
    }

    private void onClickGoToOverdueAssignment(Assignment assignment) {
        Intent intent = new Intent(this.getActivity(), SubmissionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("submission", assignment);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private List<Assignment> getListOverdueAssignment() {
        List<Assignment> assignmentList = new ArrayList<>();

        Assignment example = new Assignment(
                1,
                "Kiểm tra giữa khoá",
                10,
                "30m",
                "2023-05-10 00:00:00",
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
}