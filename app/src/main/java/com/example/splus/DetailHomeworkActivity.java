package com.example.splus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.splus.my_data.ClassData;
import com.example.splus.my_data.HomeworkData;

public class DetailHomeworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_homework);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        HomeworkData mySubmission = (HomeworkData) bundle.get("hw_data");

        TextView hw_name = findViewById(R.id.textHomeworkNameDetailSubmission);
        hw_name.setText(mySubmission.getHomework_name());

        ImageButton buttonBack = findViewById(R.id.buttonBackDetailSubmission);
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