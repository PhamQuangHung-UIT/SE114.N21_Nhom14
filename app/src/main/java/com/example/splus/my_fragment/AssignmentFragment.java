package com.example.splus.my_fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.splus.MainActivity;
import com.example.splus.NotificationActivity;
import com.example.splus.R;
import com.example.splus.my_adapter.AssignmentViewPagerAdapter;
import com.example.splus.my_data.Account;
import com.example.splus.my_data.Assignment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AssignmentFragment extends Fragment {

    MainActivity activity;
    Account account;

    public List<Assignment> listAssignment = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment, container, false);

        activity = (MainActivity) getActivity();
        account = activity.account;

        TabLayout tabLayout = view.findViewById(R.id.tabAssignmentFragment);
        ViewPager viewPager = view.findViewById(R.id.pagerAssignmentFragment);
        AssignmentViewPagerAdapter assignmentViewPagerAdapter = new AssignmentViewPagerAdapter(getParentFragmentManager(), this.getActivity());
        viewPager.setAdapter(assignmentViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        ImageButton imageButtonNotif = view.findViewById(R.id.buttonNotificationAssignmentFragment);
        imageButtonNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToNotification();
            }
        });

        return view;
    }

    private void onClickGoToNotification() {
        Intent intent = new Intent(this.getActivity(), NotificationActivity.class);
        startActivity(intent);
    }
}
