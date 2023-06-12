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
import com.example.splus.MainActivity;
import com.example.splus.QnaActivity;
import com.example.splus.R;
import com.example.splus.StudyActivity;
import com.example.splus.SubmissionActivity;
import com.example.splus.my_adapter.NotificationAdapter;
import com.example.splus.my_data.Assignment;
import com.example.splus.my_data.Lesson;
import com.example.splus.my_data.NotificationData;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class StudyNotifFragment extends Fragment {

    private static final int NOTIFICATION_LESSON = 1;
    private static final int NOTIFICATION_HOMEWORK = 2;
    private static final int NOTIFICATION_SUBMISSION = 3;
    private static final int NOTIFICATION_TEACHER_RESPONSE = 4;

    public StudyNotifFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_unfinished_hw, container, false);
        View view = inflater.inflate(R.layout.fragment_study_notif, container, false);

        RecyclerView myStudyNotification = view.findViewById(R.id.recyclerListStudyNotifFragment);
        myStudyNotification.setLayoutManager(new LinearLayoutManager(getActivity()));

        NotificationAdapter notificationAdapter = new NotificationAdapter(getAllStudyNotification(), this::onClickGoToDetail);

        myStudyNotification.setAdapter(notificationAdapter);
        /*
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageButton imageButtonNotif = view.findViewById(R.id.buttonNotifyClassFragment);
        imageButtonNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToNotification();
            }
        });
         */
        return view;
    }

    private List<NotificationData> getAllStudyNotification() {
        List<NotificationData> notificationData = new ArrayList<>();

        // get study notifications
        // 0: other, 1: lesson, 2: homework, 3: hw result, 4: teacher response
        NotificationData newLessonNotification = new NotificationData(
                1,
                "Bạn có bài tập mới",
                "Bài kiểm tra Chương 1 - Tầm quan trọng của Toán học, Lớp Nhập môn toán học, Splus",
                "24/05/2023",
                R.drawable.splus_icon,
                NOTIFICATION_HOMEWORK,
                1
        );
        notificationData.add(newLessonNotification);

        NotificationData newHomeworkNotification = new NotificationData(
                2,
                "Bạn có bài học mới",
                "Chương 2 - Ứng dụng của Toán học trong lĩnh vực Công nghệ thông tin, Lớp Nhập môn toán học, Splus",
                "24/05/2023",
                R.drawable.splus_icon,
                NOTIFICATION_LESSON,
                1
        );
        notificationData.add(newHomeworkNotification);

        NotificationData newSubmissionNotification = new NotificationData(
                3,
                "Bạn đã nộp bài cho Bài kiểm tra Chương 1 - Mã lớp: SPLUS0001",
                "Bài kiểm tra Chương 1 - Tầm quan trọng của Toán học, Lớp Nhập môn toán học, Splus",
                "24/05/2023",
                R.drawable.splus_icon,
                NOTIFICATION_SUBMISSION,
                1
        );
        notificationData.add(newSubmissionNotification);

        return notificationData;
    }

    private void onClickGoToDetail(NotificationData notificationData) {
        Intent intent;
        Bundle bundle = new Bundle();
        switch (notificationData.getType()) {
            case NOTIFICATION_LESSON:
                intent = new Intent(this.getActivity(), StudyActivity.class);
                // get a lesson data from lesson_id = sourceId
                Lesson lesson = new Lesson(
                        "",
                        "Lesson 1",
                        "Content",
                        "Course name",
                        "Teacher name"
                );
                bundle.putSerializable("lesson", lesson);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case NOTIFICATION_HOMEWORK:
                Assignment assignment = new Assignment();
                bundle.putSerializable("assignment", assignment);
                onClickGoToOngoingAssignment(assignment);
                break;
            case NOTIFICATION_SUBMISSION:
                intent = new Intent(this.getActivity(), SubmissionActivity.class);
                Assignment submission = new Assignment();
                bundle.putSerializable("submission", submission);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case NOTIFICATION_TEACHER_RESPONSE:
                intent = new Intent(this.getActivity(), QnaActivity.class);
                bundle.putSerializable("cmt_data", notificationData);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                intent = new Intent(this.getActivity(), MainActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
        }
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