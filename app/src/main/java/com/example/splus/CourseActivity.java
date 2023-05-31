package com.example.splus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splus.my_adapter.LessonAdapter;
import com.example.splus.my_data.Course;
import com.example.splus.my_data.Lesson;

import java.util.List;

public class CourseActivity extends AppCompatActivity {
    TextView className;
    ImageButton buttonBack;
    Button buttonDetailClass;
    Button buttonContactTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        Course myClass = (Course) bundle.get("course");

        className = findViewById(R.id.textNameCourseActivity);
        className.setText(myClass.getCourseName());

        RecyclerView recyclerList = findViewById(R.id.listCourseActivity);
        recyclerList.setLayoutManager(new LinearLayoutManager(CourseActivity.this));

        LessonAdapter lessonAdapter = new LessonAdapter(getAllLesson(myClass), this::onClickGoToLesson);

        recyclerList.setAdapter(lessonAdapter);

        buttonBack = findViewById(R.id.buttonBackCourseActivity);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonDetailClass = findViewById(R.id.buttonInfoCourseActivity);
        buttonDetailClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToDetailClass(myClass);
            }
        });

        buttonContactTeacher = findViewById(R.id.buttonContactCourseActivity);
        buttonContactTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CourseActivity.this, "Contact button is pressed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickGoToDetailClass(Course course) {
        Intent intent = new Intent(CourseActivity.this, DetailCourseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("detail_course", course);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void onClickGoToLesson(Lesson lesson) {
        Intent intent = new Intent(CourseActivity.this, StudyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("lesson", lesson);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @NonNull
    private List<Lesson> getAllLesson(@NonNull Course course) {
        return course.getListLesson();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        finish();
    }
}