package com.example.splus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.splus.my_data.Course;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditCourseActivity extends AppCompatActivity {

  private EditText courseNameEditText;
  private EditText creatorNameEditText;

  private Course course;

  @SuppressLint("WrongViewCast")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_course);

    courseNameEditText = findViewById(R.id.editCourseNameEditText);
    creatorNameEditText = findViewById(R.id.editCourseCreatorEditText);
    Button saveButton = findViewById(R.id.saveButton);

    // Get the selected course from the intent
    course = getIntent().getParcelableExtra("course");

    if (course != null) {
      // Populate the EditText fields with the course details
      courseNameEditText.setText(course.getCourseName());
      creatorNameEditText.setText(course.getCreatorName());
    }

    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        saveCourseChanges();
      }
    });
  }

  private void saveCourseChanges() {
    String courseName = courseNameEditText.getText().toString().trim();
    String creatorName = creatorNameEditText.getText().toString().trim();

    if (TextUtils.isEmpty(courseName)) {
      courseNameEditText.setError("Course name is required");
      return;
    }

    if (TextUtils.isEmpty(creatorName)) {
      creatorNameEditText.setError("Creator name is required");
      return;
    }

    // Update the course object with the new values
    course.setCourseName(courseName);
    course.setCreatorName(creatorName);

    // Save the updated course to Firestore
    FirebaseFirestore.getInstance().collection("courses")
        .document(course.getCourseId())
        .set(course)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void aVoid) {
            Toast.makeText(EditCourseActivity.this, "Course updated successfully", Toast.LENGTH_SHORT).show();
            finish();
          }
        })
        .addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
            Toast.makeText(EditCourseActivity.this, "Failed to update course", Toast.LENGTH_SHORT).show();
            Log.e("EditCourseActivity", "Failed to update course", e);
          }
        });
  }
}
