package com.example.splus.my_fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.splus.MainActivity;
import com.example.splus.R;
import com.example.splus.SubmissionActivity;
import com.example.splus.my_adapter.AssignmentAdapter;
import com.example.splus.my_data.Account;
import com.example.splus.my_data.Assignment;
import com.example.splus.my_data.Submission;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class OverdueAssignmentFragment extends Fragment {

    MainActivity activity;
    Account account;

    FirebaseFirestore db;

    AssignmentFragment parent_fragment;

    public Submission submission;

    public OverdueAssignmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overdue_assignment, container, false);

        activity = (MainActivity) getActivity();
        parent_fragment = (AssignmentFragment) getParentFragment();
        account = activity.getAccount();
        db = activity.getDb();

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
        bundle.putSerializable("assignment", assignment);
        bundle.putSerializable("account", account);
        getSubmission(assignment.isSubmitted(), assignment.getAssignID(), account.getAccountID());
        bundle.putSerializable("submission", submission);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    private List<Assignment> getListOverdueAssignment() {

        List<Assignment> assignmentList = this.parent_fragment.listAssignment;
        List<Assignment> overdueAsmList = new ArrayList<>();

        int quantity = assignmentList.size();
        for (int index=0; index<quantity; index++) {
            if (assignmentList.get(index).isExpired()) {
                overdueAsmList.add(assignmentList.get(index));
            }
        }

        return overdueAsmList;

        /*
        List<String> listAssignmentId = activity.listAssignmentId;

        db.collection("assignment")
                .whereArrayContainsAny("course_id", activity.getCourseId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                assignmentList.add((Assignment) document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
         */

    }

    private void getSubmission(boolean isSubmitted, String assignmentId, String accountId) {
        if (isSubmitted) {
            db.collection("submission")
                    .whereEqualTo("assignmentId", assignmentId)
                    .whereEqualTo("accountId", accountId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    try {
                                        submission = new Submission(
                                                accountId,
                                                assignmentId,
                                                document.getString("content"),
                                                document.getTimestamp("timestamp").toString()
                                        );
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }
}