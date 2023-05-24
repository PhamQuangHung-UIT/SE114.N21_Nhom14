package com.example.splus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SubmissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);

        Button buttonMakingQuestion = findViewById(R.id.buttonMakingQuestionSubmissionActivity);
        buttonMakingQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToQnA();
            }
        });

        ImageButton buttonBack = findViewById(R.id.buttonBackSubmissionActivity);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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