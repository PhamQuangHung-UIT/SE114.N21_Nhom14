package com.example.splus.my_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.CourseActivity;
import com.example.splus.CreateCourseActivity;
import com.example.splus.EditCourseActivity;
import com.example.splus.R;
import com.example.splus.my_adapter.CourseAdapter;
import com.example.splus.my_data.Course;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class CoursesFragment extends Fragment implements CourseAdapter.OnItemClickListener {

  private CourseAdapter courseAdapter;
  private List<Course> courseList;

  private FirebaseFirestore firestore;

  private TextView emptyStateTextView;
  private ProgressBar progressBar;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_courses, container, false);

    Button createBtn = view.findViewById(R.id.btn_create_course);
    createBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Replace the current fragment with the CreateCourseFragment
        requireActivity().getSupportFragmentManager().beginTransaction()
            .replace(R.id.btn_create_course, new CreateCourseActivity())
            .addToBackStack(null)
            .commit();
      }
    });

    firestore = FirebaseFirestore.getInstance();

    RecyclerView recyclerView = view.findViewById(R.id.recyclerListCoursesFragment);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    courseList = new ArrayList<>();
    courseAdapter = new CourseAdapter(courseList, this);
    recyclerView.setAdapter(courseAdapter);

    emptyStateTextView = view.findViewById(R.id.emptyStateTextView);
    progressBar = view.findViewById(R.id.progressBar);

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    loadCourses();
  }

  private void loadCourses() {
    Query query = firestore.collection("courses");

    progressBar.setVisibility(View.VISIBLE);

    query.addSnapshotListener((snapshot, e) -> {
      if (e != null) {
        Toast.makeText(getActivity(), "Error loading course list: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        Log.e("CoursesFragment", "Error loading course list", e);
        progressBar.setVisibility(View.GONE);
        return;
      }

      if (snapshot != null) {
        courseList.clear();
        for (DocumentChange dc : snapshot.getDocumentChanges()) {
          DocumentSnapshot document = dc.getDocument();
          Course course = document.toObject(Course.class);
          courseList.add(course);
        }
        courseAdapter.notifyDataSetChanged();
        updateEmptyStateView();
      }

      progressBar.setVisibility(View.GONE);
    });
  }

  private void updateEmptyStateView() {
    if (courseList.isEmpty()) {
      emptyStateTextView.setVisibility(View.VISIBLE);
    } else {
      emptyStateTextView.setVisibility(View.GONE);
    }
  }

  @Override
  public void onItemClick(Course course) {
    Intent intent = new Intent(requireActivity(), CourseActivity.class);
    intent.putExtra("course", course);
    startActivity(intent);
  }

  @Override
  public void onEditButtonClick(Course course) {
    // Implement the edit course functionality
    Intent intent = new Intent(getActivity(), EditCourseActivity.class);
    intent.putExtra("course", course);
    startActivity(intent);
  }

  @Override
  public void onDeleteButtonClick(Course course) {
    // Implement the delete course functionality
    firestore.collection("courses")
        .document(course.getCourseId())
        .delete()
        .addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void aVoid) {
            Toast.makeText(getActivity(), "Course deleted successfully", Toast.LENGTH_SHORT).show();
          }
        })
        .addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
            Toast.makeText(getActivity(), "Failed to delete course", Toast.LENGTH_SHORT).show();
          }
        });
  }
}
