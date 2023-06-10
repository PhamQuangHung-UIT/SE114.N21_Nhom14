package com.example.splus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.splus.my_data.Account;
import com.example.splus.my_data.Assignment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SubmissionActivity extends AppCompatActivity {

    Account account;
    Assignment assignment;
    FirebaseFirestore db;
    CollectionReference collectionReference = db.collection("submission");
    DocumentReference documentReference = db.document("submission/submit_id");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        account = (Account) bundle.get("account");
        assignment = (Assignment) bundle.get("assignment");

        db = FirebaseFirestore.getInstance();



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