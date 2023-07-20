package com.example.splus;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.splus.my_data.Course;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateCourseActivity extends AppCompatActivity {
    private EditText editTextCourseName;
    private EditText editTextCourseDescription;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        firestore = FirebaseFirestore.getInstance();

        editTextCourseName = findViewById(R.id.editTextCourseName);
        editTextCourseDescription = findViewById(R.id.editTextCourseDescription);
        Button buttonCreateCourse = findViewById(R.id.buttonCreateCourse);
        ImageButton backButton = findViewById(R.id.buttonBackCreateCourseActivity);
        backButton.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        buttonCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCourse();
            }
        });
    }

    private void createCourse() {
        String courseName = editTextCourseName.getText().toString().trim();
        String creatorName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        String courseDescription = editTextCourseDescription.getText().toString();

        if (!TextUtils.isEmpty(courseName)) {
            String courseId = firestore.collection("courses").document().getId(); // Generate ID

            Course course = new Course();
            course.setCourseId(courseId);
            course.setCourseName(courseName);
            course.setCreatorId(FirebaseAuth.getInstance().getCurrentUser().getUid());
            course.setCreatorName(creatorName);
            course.setCourseDescription(courseDescription);

            firestore.collection("courses").document(courseId).set(course)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(CreateCourseActivity.this, "Course created successfully", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
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
            Toast.makeText(this, R.string.empty_course_name, Toast.LENGTH_SHORT).show();
        }
    }
}
