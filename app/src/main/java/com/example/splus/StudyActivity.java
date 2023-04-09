package com.example.splus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class StudyActivity extends AppCompatActivity {

    private Button classInfoButton;
    private Button contactTeacherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        ItemData item = (ItemData) bundle.get("object_item");

        String teacherName = item.getItemDes();
        int classSize = 20;
        ClassData mClass = new ClassData(item, teacherName, classSize);

        TextView subjectName = findViewById(R.id.subjectName);
        subjectName.setText(mClass.getItemName());

    }
}