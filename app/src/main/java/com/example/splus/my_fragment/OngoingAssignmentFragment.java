package com.example.splus.my_fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.splus.DoHomeworkActivity;
import com.example.splus.R;
import com.example.splus.my_adapter.AssignmentAdapter;
import com.example.splus.my_data.Assignment;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class OngoingAssignmentFragment extends Fragment {

    public OngoingAssignmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ongoing_assignment, container, false);
        RecyclerView list = view.findViewById(R.id.recyclerOngoingAssignmentFragment);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));

        AssignmentAdapter adapter = new AssignmentAdapter(getListOngoingAssignment(), this::onClickGoToOngoingAssignment);
        list.setAdapter(adapter);

        return view;
    }

    private List<Assignment> getListOngoingAssignment() {
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

    private void onClickGoToOngoingAssignment(Assignment assignment) {
        View viewDialog = getLayoutInflater().inflate(R.layout.assignment_bottom_sheet, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this.getContext());
        bottomSheetDialog.setContentView(viewDialog);
        bottomSheetDialog.show();

        TextView assignmentName = viewDialog.findViewById(R.id.textNameAssignmentBottomSheet);
        assignmentName.setText(assignment.getAssignName());

        TextView assignmentFormat = viewDialog.findViewById(R.id.textFormatAssignmentBottomSheet);
        assignmentFormat.setText(assignment.getAssignFormat()==0? "Trắc nghiệm":"Tự luận");

        TextView assignmentQuantity = viewDialog.findViewById(R.id.textQuantityAssignmentBottomSheet);
        assignmentQuantity.setText(assignment.getQuantity());

        TextView assignmentTime = viewDialog.findViewById(R.id.textTimeAssignmentBottomSheet);
        assignmentTime.setText(assignment.getAssignTime());

        TextView assignmentDeadline = viewDialog.findViewById(R.id.textDeadlineAssignmentBottomSheet);
        assignmentDeadline.setText(assignment.getAssignDeadline());

        Button buttonCancel = viewDialog.findViewById(R.id.buttonCancelAssignmentBottomSheet);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        Button buttonEnter = viewDialog.findViewById(R.id.buttonEnterAssignmentBottomSheet);
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToDoAssignment(assignment);
            }
        });
    }

    private void onClickGoToDoAssignment(Assignment assignment) {
        Intent intent = new Intent(this.getActivity(), DoHomeworkActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("assignment", assignment);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}