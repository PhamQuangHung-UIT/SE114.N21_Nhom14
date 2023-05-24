package com.example.splus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splus.my_data.HomeworkData;

public class DetailHomeworkActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_homework);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        HomeworkData myHomework = (HomeworkData) bundle.get("hw_data");

        TextView hw_name = findViewById(R.id.textHomeworkNameDetailHomeworkActivity);
        hw_name.setText(myHomework.getHomework_name());

        TextView hw_format = findViewById(R.id.textFormatDetailHomeworkActivity);
        if (myHomework.getFormat()==0) {
            hw_format.setText(R.string.homework_format);
        } else {
            hw_format.setText(R.string.homework_format_2);
        }

        TextView hw_quantity = findViewById(R.id.textNumberDetailHomeworkActivity);
        hw_quantity.setText(Integer.toString(10));

        TextView hw_time = findViewById(R.id.textTimeDetailHomeworkActivity);
        hw_time.setText(myHomework.getTime());

        TextView hw_deadline = findViewById(R.id.textDeadlineDetailHomeworkActivity);
        hw_deadline.setText(myHomework.getDeadline());

        Button enter = findViewById(R.id.buttonEnterDetailHomeworkActivity);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailHomeworkActivity.this, "Start putton is pressed", Toast.LENGTH_SHORT).show();
                onClickGoToDoHomework();
            }
        });
        ImageButton buttonBack = findViewById(R.id.buttonBackDetailHomeworkActivity);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageButton imageButtonNotif = findViewById(R.id.buttonNotificationDetailHomeworkActivity);
        imageButtonNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToNotification();
            }
        });

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        finish();
    }

    private void onClickGoToNotification() {
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }
    private void onClickGoToDoHomework() {
        Intent intent = new Intent(this, DoHomeworkActivity.class);
        startActivity(intent);
    }
}