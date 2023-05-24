package com.example.splus.my_fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.splus.DetailNotificationActivity;
import com.example.splus.R;
import com.example.splus.my_adapter.NotificationAdapter;
import com.example.splus.my_data.NotificationData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OtherNotifFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OtherNotifFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OtherNotifFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OtherNotifFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OtherNotifFragment newInstance(String param1, String param2) {
        OtherNotifFragment fragment = new OtherNotifFragment();
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
        View view = inflater.inflate(R.layout.fragment_other_notif, container, false);

        RecyclerView myStudyNotification = view.findViewById(R.id.recyclerListOtherNotifFragment);
        myStudyNotification.setLayoutManager(new LinearLayoutManager(getActivity()));

        NotificationAdapter notificationAdapter = new NotificationAdapter(getAllOtherNotification(), this::onClickGoToDetail);

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

    private List<NotificationData> getAllOtherNotification() {
        List<NotificationData> notificationData = new ArrayList<>();

        // get other notifications
        NotificationData newSubmissionNotification = new NotificationData(
                4,
                "Thông báo cập nhật ứng dụng",
                "Anh/Chị vui lòng cập nhật ứng dụng trên Android, để gửi thắc mắc đến giảng viên"
                        + " vui lòng vào trang nội dung bài học hoặc trang chi tiết bài làm và nhấn vào nút"
                        + " Đặt câu hỏi;",
                "02/05/2023",
                R.drawable.splus_icon,
                0,
                1
        );
        notificationData.add(newSubmissionNotification);

        return notificationData;
    }

    private void onClickGoToDetail(NotificationData notificationData) {
        Intent intent = new Intent(this.getActivity(), DetailNotificationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("notif_data", notificationData);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}