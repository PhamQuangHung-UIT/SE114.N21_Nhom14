package com.example.splus.my_fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.splus.ClassModel;
import com.example.splus.R;
import com.example.splus.my_adapter.CourseAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment {

    private CourseAdapter courseAdapter;
    private List<ClassModel> classList;

    private FirebaseFirestore firestore;

    // ...

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerListCourseFragment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        classList = new ArrayList<>();
        courseAdapter = new CourseAdapter(classList);

        recyclerView.setAdapter(courseAdapter);

        // Load class data from Firestore
        loadClasses();

        return view;
    }

    // Load class data from Firestore
    private void loadClasses() {
        CollectionReference classesCollection = firestore.collection("classes");

        classesCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        // Extract class data from Firestore document
                        String classId = documentSnapshot.getString("classId");
                        String className = documentSnapshot.getString("className");
                        String instructorName = documentSnapshot.getString("instructorName");

                        // Create a ClassModel object
                        ClassModel classModel = new ClassModel(classId, className, instructorName);

                        // Add classModel to the classList
                        classList.add(classModel);
                    }

                    // Notify the adapter that the data has changed
                    courseAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
