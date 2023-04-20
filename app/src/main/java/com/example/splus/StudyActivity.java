package com.example.splus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.splus.my_data.ClassData;
import com.example.splus.my_data.LessonData;

public class StudyActivity extends AppCompatActivity {

    TextView lessonName;
    TextView lessonContent;
    TextView className, teacherName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        LessonData lesson = (LessonData) bundle.get("lesson_data");

        lessonName = findViewById(R.id.lessonName);
        lessonContent = findViewById(R.id.lessonContent);
        className = findViewById(R.id.className);
        teacherName = findViewById(R.id.teacherName);

        lessonName.setText(lesson.getLessonName());
        lessonContent.setText(lesson.getLessonContent());
        className.setText(lesson.getClassName());
        teacherName.setText(lesson.getTeacherName());
    }
}