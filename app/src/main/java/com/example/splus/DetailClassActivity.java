package com.example.splus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.splus.my_data.ClassData;

public class DetailClassActivity extends AppCompatActivity {

    ImageButton buttonBack;

    TextView textClassName;
    TextView textTeacherName;
    TextView textEmailTeacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_class);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        ClassData myClass = (ClassData) bundle.get("detail_class_data");

        textClassName = findViewById(R.id.textClassNameDetailClass);
        textClassName.setText(myClass.getClassName());

        textTeacherName = findViewById(R.id.textTeacherNameDetailClass);
        textTeacherName.setText(myClass.getTeacherName());

        textEmailTeacher = findViewById(R.id.textEmailTeacherDetailClass);
        textEmailTeacher.setText(R.string.ex_email);

        buttonBack = findViewById(R.id.buttonBackDetailClass);
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