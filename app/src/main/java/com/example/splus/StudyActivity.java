package com.example.splus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.splus.my_data.LessonData;

public class StudyActivity extends AppCompatActivity {

    TextView lessonName;
    TextView lessonContent;
    TextView className, teacherName;

    ImageButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        LessonData lesson = (LessonData) bundle.get("lesson_data");

        lessonName = findViewById(R.id.textLessonNameStudyAct);
        lessonContent = findViewById(R.id.lessonContentStudyAct);
        className = findViewById(R.id.textClassNameStudyAct);
        teacherName = findViewById(R.id.textTeacherNameStudyAct);

        lessonName.setText(lesson.getLessonName());
        lessonContent.setText(lesson.getLessonContent());
        className.setText(lesson.getClassName());
        teacherName.setText(lesson.getTeacherName());

        buttonBack = findViewById(R.id.buttonBackStudyAct);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}