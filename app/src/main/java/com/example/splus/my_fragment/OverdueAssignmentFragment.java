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
import android.widget.Toast;

import com.example.splus.MainActivity;
import com.example.splus.R;
import com.example.splus.SubmissionActivity;
import com.example.splus.my_adapter.AssignmentAdapter;
import com.example.splus.my_data.Account;
import com.example.splus.my_data.Assignment;
import com.example.splus.my_data.Submission;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
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

    public List<Assignment> listAssignment;

    public OverdueAssignmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overdue_assignment, container, false);

        activity = (MainActivity) getActivity();

        account = activity.account;
        listAssignment = new ArrayList<>();

        for (int index=0; index<activity.listAssignId.size(); index++) {
            activity.db.collection("assignments").document(activity.listAssignId.get(index))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                System.out.println(document.getData());
                                if (document != null) {
                                    try {
                                        listAssignment.add(new Assignment(
                                                document.getId(),
                                                document.getString("name"),
                                                document.getString("details"),
                                                document.getString("lessonId"),
                                                document.getString("courseName")
                                        ));
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }
                    });
        }

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

        List<Assignment> overdueAsmList = new ArrayList<>();

        int quantity = this.listAssignment.size();
        if (quantity != 0) {
            for (int index=0; index<quantity; index++) {
                if (this.listAssignment.get(index).isExpired()) {
                    overdueAsmList.add(this.listAssignment.get(index));
                }
            }
        }
        System.out.print("Overdue Assignment: ");
        System.out.println(overdueAsmList.size());
        return overdueAsmList;
    }

    private void getListAssignment(List<String> listAssignmentId) {
        int quantity = listAssignmentId.size();
        System.out.print("Get List Assignment: ");
        System.out.println(quantity);
        for (int index=0; index<quantity; index++) {
            System.out.print("Get Assignment with ID: ");
            System.out.println(listAssignmentId.get(index));
            activity.db.collection("assignments").document(listAssignmentId.get(index)).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    try {
                                        listAssignment.add(new Assignment(
                                                document.getId(),
                                                document.getString("name"),
                                                document.getString("details"),
                                                document.getString("lessonId"),
                                                document.getString("courseName")
                                        ));
                                        listAssignment.get(listAssignment.size()-1).setStatus(false);
                                        Toast.makeText(activity, "Yes", Toast.LENGTH_SHORT).show();
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
            activity.db.collection("submission")
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
                                    System.out.print("An assignment is submitted: ID = ");
                                    System.out.println(listAssignment.get(listAssignment.size()-1).getAssignID());
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
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