package com.example.splus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.splus.my_adapter.SubmissionAdapter;
import com.example.splus.my_data.Account;
import com.example.splus.my_data.Assignment;
import com.example.splus.my_data.Question;
import com.example.splus.my_data.Submission;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SubmissionActivity extends AppCompatActivity {

    public Account account;
    public Assignment assignment;
    public Submission submission;
    public List<Question> listQuestion = new ArrayList<>();

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
        submission = (Submission) bundle.get("submission");

        for (int index=0; index<assignment.getQuantity(); index++) {
            try {
                listQuestion.add(assignment.getQuestion(index));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        TextView title = findViewById(R.id.textHomeworkNameSubmissionActivity);
        title.setText(assignment.getAssignName());

        RecyclerView list = findViewById(R.id.recyclerContentSubmissionActivity);
        list.setLayoutManager(new LinearLayoutManager(this));

        SubmissionAdapter submissionAdapter = new SubmissionAdapter(listQuestion, submission.listSelection);

        list.setAdapter(submissionAdapter);

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