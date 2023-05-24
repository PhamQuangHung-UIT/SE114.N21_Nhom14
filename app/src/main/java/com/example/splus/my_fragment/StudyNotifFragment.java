package com.example.splus.my_fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.splus.DetailHomeworkActivity;
import com.example.splus.MainActivity;
import com.example.splus.QnaActivity;
import com.example.splus.R;
import com.example.splus.StudyActivity;
import com.example.splus.SubmissionActivity;
import com.example.splus.my_adapter.NotificationAdapter;
import com.example.splus.my_data.HomeworkData;
import com.example.splus.my_data.LessonData;
import com.example.splus.my_data.NotificationData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudyNotifFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyNotifFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final int NOTIFICATION_LESSON = 1;
    private static final int NOTIFICATION_HOMEWORK = 2;
    private static final int NOTIFICATION_SUBMISSION = 3;
    private static final int NOTIFICATION_TEACHER_RESPONSE = 4;

    public StudyNotifFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudyNotifFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudyNotifFragment newInstance(String param1, String param2) {
        StudyNotifFragment fragment = new StudyNotifFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
                LessonData lessonData = new LessonData();
                bundle.putSerializable("lesson_data", lessonData);
                break;
            case NOTIFICATION_HOMEWORK:
                intent = new Intent(this.getActivity(), DetailHomeworkActivity.class);
                HomeworkData homeworkData = new HomeworkData(false);
                bundle.putSerializable("hw_data", homeworkData);
                break;
            case NOTIFICATION_SUBMISSION:
                intent = new Intent(this.getActivity(), SubmissionActivity.class);
                HomeworkData submissionData = new HomeworkData(true);
                bundle.putSerializable("submit_data", submissionData);
                break;
            case NOTIFICATION_TEACHER_RESPONSE:
                intent = new Intent(this.getActivity(), QnaActivity.class);
                bundle.putSerializable("cmt_data", notificationData);
                break;
            default:
                intent = new Intent(this.getActivity(), MainActivity.class);
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }
}