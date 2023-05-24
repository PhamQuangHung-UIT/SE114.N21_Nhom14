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

import com.example.splus.DetailHomeworkActivity;
import com.example.splus.R;
import com.example.splus.SubmissionActivity;
import com.example.splus.my_adapter.HomeworkAdapter;
import com.example.splus.my_data.HomeworkData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FinishedHwFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinishedHwFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FinishedHwFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinishedHwFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinishedHwFragment newInstance(String param1, String param2) {
        FinishedHwFragment fragment = new FinishedHwFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_unfinished_hw, container, false);
        View view = inflater.inflate(R.layout.fragment_finished_hw, container, false);

        RecyclerView myFinishedHw = view.findViewById(R.id.recyclerListFinishedHwFragment);
        myFinishedHw.setLayoutManager(new LinearLayoutManager(getActivity()));

        HomeworkAdapter finishedHwAdapter = new HomeworkAdapter(getListFinishedHw(), this::onClickGoToFinishedHw);

        myFinishedHw.setAdapter(finishedHwAdapter);
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

    private void onClickGoToFinishedHw(HomeworkData homeworkData) {
        Intent intent = new Intent(this.getActivity(), SubmissionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("hw_data", homeworkData);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @NonNull
    private List<HomeworkData> getListFinishedHw() {
        List<HomeworkData> homeworkData = new ArrayList<>();

        HomeworkData example = new HomeworkData(
                1,
                "Kiểm tra cuối khoá",
                "0",
                "23:59 05/04/20223",
                "SPLUS00001",
                "Nhập môn Toán học",
                0,
                9.8,
                true
        );
        homeworkData.add(example);
        homeworkData.add(example);
        homeworkData.add(example);
        homeworkData.add(example);
        homeworkData.add(example);
        return homeworkData;

    }
}