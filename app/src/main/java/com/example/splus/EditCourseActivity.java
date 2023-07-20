package com.example.splus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.splus.my_data.Course;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditCourseActivity extends AppCompatActivity {

    private EditText courseNameEditText, editTextCourseDescription;

    private Course course;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        course = getIntent().getParcelableExtra("course");

        courseNameEditText = findViewById(R.id.editCourseNameEditText);
        editTextCourseDescription = findViewById(R.id.editTextCourseDescription);
        Button saveButton = findViewById(R.id.saveButton);
        ImageButton backButton = findViewById(R.id.buttonBackEditCourseActivity);
        backButton.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        // Get the selected course from the intent
        course = getIntent().getParcelableExtra("course");

        if (course != null) {
            // Populate the EditText fields with the course details
            courseNameEditText.setText(course.getCourseName());
            editTextCourseDescription.setText(course.getCreatorName());
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
        String courseDescription = editTextCourseDescription.getText().toString().trim();

        if (TextUtils.isEmpty(courseName)) {
            Toast.makeText(this, R.string.empty_course_name, Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the course object with the new values
        Map<String, Object> map = new HashMap<>();
        map.put("courseName", courseDescription);
        map.put("courseDescription", editTextCourseDescription);

        // Save the updated course to Firestore
        FirebaseFirestore.getInstance().collection("courses")
                .document(course.getCourseId())
                .update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditCourseActivity.this, "Course updated successfully", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
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
