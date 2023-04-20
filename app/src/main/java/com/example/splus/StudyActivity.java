package com.example.splus;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
=======
import android.annotation.SuppressLint;
>>>>>>> acd84aaf57af25db1b445cfffe11a67e171e5efc
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

<<<<<<< HEAD
public class StudyActivity extends AppCompatActivity {

    private Button classInfoButton;
    private Button contactTeacherButton;

=======
import com.example.splus.my_data.ClassData;
import com.example.splus.my_data.LessonData;

public class StudyActivity extends AppCompatActivity {

    TextView lessonName;
    TextView lessonContent;
    TextView className, teacherName;

    @SuppressLint("MissingInflatedId")
>>>>>>> acd84aaf57af25db1b445cfffe11a67e171e5efc
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

<<<<<<< HEAD
        ItemData item = (ItemData) bundle.get("object_item");

        String teacherName = item.getItemDes();
        int classSize = 20;
        ClassData mClass = new ClassData(item, teacherName, classSize);

        TextView subjectName = findViewById(R.id.subjectName);
        subjectName.setText(mClass.getItemName());

=======
        LessonData lesson = (LessonData) bundle.get("lesson_data");

        lessonName = findViewById(R.id.lessonName);
        lessonContent = findViewById(R.id.lessonContent);
        className = findViewById(R.id.className);
        teacherName = findViewById(R.id.teacherName);

        lessonName.setText(lesson.getLessonName());
        lessonContent.setText(lesson.getLessonContent());
        className.setText(lesson.getClassName());
        teacherName.setText(lesson.getTeacherName());
>>>>>>> acd84aaf57af25db1b445cfffe11a67e171e5efc
    }
}