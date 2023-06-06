package com.example.splus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class CreateCourseActivity extends AppCompatActivity {

    TextView teacherName;
    EditText courseName, courseCode, courseSize;
    Button createButton;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        teacherName = findViewById(R.id.textTeacherNameCreateCourseActivity);

        courseName = findViewById(R.id.editNameCreateCourseActivity);
        courseCode = findViewById(R.id.editCodeCreateCourseActivity);
        courseSize = findViewById(R.id.editSizeCreateCourseActivity);

        createButton = findViewById(R.id.buttonCreateCourseActivity);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_name = courseName.getText().toString();
                String str_code = courseCode.getText().toString();
                String str_size = courseSize.getText().toString();
            }
        });

        backButton = findViewById(R.id.buttonBackCreateCourseActivity);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        finish();
    }
}