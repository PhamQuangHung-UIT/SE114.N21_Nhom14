package com.example.splus;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class DetailCourseActivity extends AppCompatActivity {
    ImageButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_course);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        String detail = bundle.getString("courseDetail");

        TextView textView = findViewById(R.id.textView_courseDescription);
        textView.setText(detail);


        buttonBack = findViewById(R.id.buttonBackDetailCourse);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}