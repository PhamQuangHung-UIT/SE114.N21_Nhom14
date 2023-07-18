package com.example.splus;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.splus.my_data.Course;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateCourseActivity extends Fragment {
  private EditText editTextCourseName;
  private EditText editTextCreatorName;
  private FirebaseFirestore firestore;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.activity_create_course, container, false);

    firestore = FirebaseFirestore.getInstance();

    editTextCourseName = view.findViewById(R.id.editTextCourseName);
    editTextCreatorName = view.findViewById(R.id.editTextCreatorName);
    Button buttonCreateCourse = view.findViewById(R.id.buttonCreateCourse);

    buttonCreateCourse.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        createCourse();
      }
    });

    return view;
  }

  private void createCourse() {
    String courseName = editTextCourseName.getText().toString().trim();
    String creatorName = editTextCreatorName.getText().toString().trim();

    if (!TextUtils.isEmpty(courseName) && !TextUtils.isEmpty(creatorName)) {
      String courseId = firestore.collection("courses").document().getId(); // Generate ID

      Course course = new Course();
      course.setCourseId(courseId);
      course.setCourseName(courseName);
      course.setCreatorName(creatorName);

      firestore.collection("courses").document(courseId).set(course)
          .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
              Toast.makeText(requireContext(), "Course created successfully", Toast.LENGTH_SHORT).show();
              // Navigate back to the CoursesFragment
              requireActivity().onBackPressed();
            }
          })
          .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              Toast.makeText(requireContext(), "Failed to create course", Toast.LENGTH_SHORT).show();
            }
          });
    } else {
      Toast.makeText(requireContext(), "Please enter all the information", Toast.LENGTH_SHORT).show();
    }
  }
}
