package com.example.splus.my_fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AssignmentFragment extends Fragment {

    MainActivity activity;
    Account account;

    List<Assignment> listAssignment = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment, container, false);

        activity = (MainActivity) getActivity();
        account = activity.getAccount();

        TabLayout tabLayout = view.findViewById(R.id.tabAssignmentFragment);
        ViewPager viewPager = view.findViewById(R.id.pagerAssignmentFragment);
        AssignmentViewPagerAdapter assignmentViewPagerAdapter = new AssignmentViewPagerAdapter(getChildFragmentManager(), this.getActivity());
        viewPager.setAdapter(assignmentViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        ImageButton imageButtonNotif = view.findViewById(R.id.buttonNotificationAssignmentFragment);
        imageButtonNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToNotification();
            }
        });

        getListAssignment(activity.listAssignmentId);
        return view;
    }

    private void getListAssignment(List<String> listAssignmentId) {
        FirebaseFirestore db = activity.db;
        int quantity = listAssignmentId.size();
        for (int index=0; index<quantity; index++) {
            db.collection("assignments").document(listAssignmentId.get(index)).get()
                    .addOnCompleteListener(new OnCompleteListener<>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    try {
                                        listAssignment.add(new Assignment(
                                                document.getId(),
                                                document.getData().get("name").toString(),
                                                document.getData().get("details").toString(),
                                                document.getData().get("lessonId").toString(),
                                                document.getData().get("courseName").toString()
                                        ));
                                        listAssignment.get(listAssignment.size() - 1).setStatus(false);
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        }
                    });
            // check if assignment is submitted
            db.collection("submission")
                    .whereEqualTo("assignmentId", listAssignmentId.get(index))
                    .whereEqualTo("accountId", account.getAccountID())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    listAssignment.get(listAssignment.size()-1).setStatus(true);
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }

    private void onClickGoToNotification() {
        Intent intent = new Intent(this.getActivity(), NotificationActivity.class);
        startActivity(intent);
    }
}
