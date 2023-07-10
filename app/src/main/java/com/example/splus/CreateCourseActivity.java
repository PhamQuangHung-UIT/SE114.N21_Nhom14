package com.example.splus;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.splus.my_data.Course;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateCourseActivity extends AppCompatActivity {
  private EditText editTextCourseName;
  private EditText editTextCreatorName;
  private DatabaseReference databaseReference;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_course);

    databaseReference = FirebaseDatabase.getInstance().getReference("courses");

    editTextCourseName = findViewById(R.id.editTextCourseName);
    editTextCreatorName = findViewById(R.id.editTextCreatorName);
    Button buttonCreateCourse = findViewById(R.id.buttonCreateCourse);

    buttonCreateCourse.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        createCourse();
      }
    });
  }

  private void createCourse() {
    String courseName = editTextCourseName.getText().toString().trim();
    String creatorName = editTextCreatorName.getText().toString().trim();

    if (!TextUtils.isEmpty(courseName) && !TextUtils.isEmpty(creatorName)) {
      String courseId = databaseReference.push().getKey(); // create ID

      Course course = new Course();
      course.setCourseId(courseId);
      course.setCourseName(courseName);
      course.setCreatorName(creatorName);


      databaseReference.child(courseId).setValue(course)
          .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
              Toast.makeText(CreateCourseActivity.this, "Course created successfully", Toast.LENGTH_SHORT).show();
              finish();
            }
          })
          .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              Toast.makeText(CreateCourseActivity.this, "Failed to create course", Toast.LENGTH_SHORT).show();
            }
          });
    } else {
      Toast.makeText(this, "Please enter all the information", Toast.LENGTH_SHORT).show();
    }
  }
}
