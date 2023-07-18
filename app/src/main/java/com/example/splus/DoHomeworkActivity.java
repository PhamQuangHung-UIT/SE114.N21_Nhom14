package com.example.splus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.splus.my_adapter.QuestionAdapter;
import com.example.splus.my_data.Assignment;
import com.example.splus.my_data.Question;

import java.util.ArrayList;
import java.util.List;

public class DoHomeworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_homework);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        Assignment assignment = (Assignment) bundle.get("assignment");

        int number = assignment.getQuantity();
        List<Question> questionList = new ArrayList<>();
        for (int i=0; i<number; i++) {
            try {
                questionList.add(assignment.getQuestion(i));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println(questionList.get(i).getQuestion());
        }

        RecyclerView recyclerQuestion = findViewById(R.id.recyclerQuestionDoHomeworkActivity);
        recyclerQuestion.setLayoutManager(new LinearLayoutManager(DoHomeworkActivity.this));
        QuestionAdapter questionAdapter = new QuestionAdapter(questionList);
        recyclerQuestion.setAdapter(questionAdapter);

        Button buttonSubmit = findViewById(R.id.buttonSubmitDoHomeworkActivity);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DoHomeworkActivity.this)
                        .setTitle("Confirm submit")
                        .setMessage("Are you sure to submit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(DoHomeworkActivity.this, "Submit successful", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        ImageButton buttonBack = findViewById(R.id.buttonBackDoHomeworkActivity);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DoHomeworkActivity.this)
                        .setTitle("Confirm exit")
                        .setMessage("Are you sure to exit without submit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(DoHomeworkActivity.this, "Exit successful", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        finish();
    }
}