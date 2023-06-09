package com.example.splus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.splus.my_data.Lesson;

public class StudyActivity extends AppCompatActivity {

    TextView lessonName;
    TextView lessonContent;
    TextView courseName, teacherName;

    ImageButton buttonBack;
    Button buttonMakingQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        Lesson lesson = (Lesson) bundle.get("lesson");

        lessonName = findViewById(R.id.textLessonNameStudyAct);
        lessonContent = findViewById(R.id.lessonContentStudyAct);
        courseName = findViewById(R.id.textClassNameStudyAct);
        teacherName = findViewById(R.id.textTeacherNameStudyAct);

        lessonName.setText(lesson.getLessonName());
        lessonContent.setText(lesson.getLessonContent());
        courseName.setText(lesson.getCourseName());
        teacherName.setText(lesson.getTeacherName());

        buttonBack = findViewById(R.id.buttonBackStudyAct);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonMakingQuestion = findViewById(R.id.buttonMakingQuestionStudyAct);
        buttonMakingQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToQnA();
            }
        });
    }

    private void onClickGoToQnA() {
        Intent intent = new Intent(this, QnaActivity.class);
        /*
        Bundle bundle = new Bundle();
        bundle.putSerializable("submit_id", submit);
        intent.putExtras(bundle);
         */
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}