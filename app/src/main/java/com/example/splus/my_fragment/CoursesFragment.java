package com.example.splus.my_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.R;
import com.example.splus.my_adapter.CourseAdapter;
import com.example.splus.my_data.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CoursesFragment extends Fragment {

  private RecyclerView recyclerView;
  private CourseAdapter courseAdapter;
  private List<Course> courseList;

  private DatabaseReference databaseReference;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_courses, container, false);

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    databaseReference = firebaseDatabase.getReference("courses");

    recyclerView = view.findViewById(R.id.recyclerListCoursesFragment);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    courseList = new ArrayList<>();
    courseAdapter = new CourseAdapter(courseList);
    recyclerView.setAdapter(courseAdapter);

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    //load courses list
    loadCourses();
  }

  private void loadCourses() {
    databaseReference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        courseList.clear();

        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
          Course course = snapshot.getValue(Course.class);
          courseList.add(course);
        }

        courseAdapter.notifyDataSetChanged();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {
        // when error
      }
    });
  }
}