package com.example.splus.my_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.CreateAssignmentActivity;
import com.example.splus.MainActivity;
import com.example.splus.NotificationActivity;
import com.example.splus.R;
import com.example.splus.SummaryActivity;
import com.example.splus.my_adapter.AssignAdapter;
import com.example.splus.my_adapter.SpinnerCourseAdapter;
import com.example.splus.my_data.Account;
import com.example.splus.my_data.Assignment;
import com.example.splus.my_data.Course;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AssignFragment extends Fragment {

    private Spinner spinnerChooseClass;
    private RecyclerView listAssignment;
    private AssignAdapter assignAdapter;
    private SpinnerCourseAdapter spinnerCourseAdapter;

    private Button buttonCreateAssignment;

    private MainActivity activity;
    public AssignFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_assign, container, false);

        activity = (MainActivity) getActivity();

        Account account = MainActivity.getAccount();

        ImageButton imageButtonNotif = view.findViewById(R.id.buttonNotificationAssignFragment);
        imageButtonNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToNotification();
            }
        });

        spinnerChooseClass = view.findViewById(R.id.spinnerAssignFragment);
        listAssignment = view.findViewById(R.id.recyclerListAssignFragment);
        listAssignment.setLayoutManager(new LinearLayoutManager(getActivity()));
        assignAdapter = new AssignAdapter(getAllAssignment(), this::onClickGoToSummary);

        spinnerCourseAdapter = new SpinnerCourseAdapter(requireActivity(), R.layout.item_course_selected, getListCourse());
        spinnerChooseClass.setAdapter(spinnerCourseAdapter);

        spinnerChooseClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                assignAdapter.setData(getAssignment(spinnerCourseAdapter.getItem(position).getCourseId()));
                Toast.makeText(parent.getContext(), spinnerCourseAdapter.getItem(position).getCourseName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                assignAdapter.setData(getAllAssignment());
            }
        });

        buttonCreateAssignment = view.findViewById(R.id.buttonAssignFragment);
        buttonCreateAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateAssignmentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("teacher_account", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        activity = (MainActivity) requireActivity();

        return view;
    }

    private void onClickGoToNotification() {
        Intent intent = new Intent(this.getActivity(), NotificationActivity.class);
        startActivity(intent);
    }

    @NonNull
    private List<Assignment> getAssignment(String courseId) {
        List<Assignment> listAssignment = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("assignments")
                .whereEqualTo("courseId", courseId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listAssignment.add((Assignment) document.getData());
                            }
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                            Snackbar.make(getView(), R.string.unexpected_error_msg, Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
        return listAssignment;
    }

    @NonNull
    private List<Assignment> getAllAssignment() {
        List<Assignment> listAssignment = new ArrayList<>();
        List<String> listLessonId = activity.getAllLessonId();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        for (int index=0; index<listLessonId.size(); index++) {
            db.collection("assignments")
                    .whereEqualTo("lessonId", listLessonId.get(index))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                listAssignment.addAll(task.getResult().toObjects(Assignment.class));
                            } else {
                                Log.d("Error", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }

        return listAssignment;
    }

    @NonNull
    private List<Course> getListCourse() {
        List<Course> courseList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("courses").whereEqualTo("creatorId", user).get().addOnCompleteListener(task -> {
            if (task.isSuccessful())
                courseList.addAll(task.getResult().toObjects(Course.class));
            else {
                Log.e("Error", "Error in get course list", task.getException());
                Snackbar.make(getView(), R.string.unexpected_error_msg, Snackbar.LENGTH_SHORT).show();
            }
        });
        return courseList;
    }

    private void onClickGoToSummary(Assignment assignment) {
        Intent intent = new Intent(this.getActivity(), SummaryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("assignment", assignment);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}