package com.example.splus;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CreateAssignmentActivity extends AppCompatActivity {

    private EditText editNameTopic;
    private EditText editNumberOfQuestion;
    private DatePicker datePickerAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assignment);

        // Ánh xạ các thành phần từ layout XML
        editNameTopic = findViewById(R.id.editNameTopic);
        editNumberOfQuestion = findViewById(R.id.editNumberOfQuestion);
        datePickerAction = findViewById(R.id.datePickerAction);
        Button btnCreateAssignment = findViewById(R.id.btnCreateAssignment);
        Button btnCancelCreateAssign = findViewById(R.id.btnCancelCreateAssign);

        // Click button Create or "Tạo"
        btnCreateAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTopic = editNameTopic.getText().toString();
                int numberOfQuestions = Integer.parseInt(editNumberOfQuestion.getText().toString());
                int day = datePickerAction.getDayOfMonth();
                int month = datePickerAction.getMonth();
                int year = datePickerAction.getYear();


            }
        });

        // Click button Cancel or "Hủy"
        btnCancelCreateAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display confirmation dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateAssignmentActivity.this);
                builder.setTitle("Xác nhận hủy tạo bài tập");
                builder.setMessage("Bạn có chắc muốn hủy tạo bài tập không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle when the user agrees to cancel the assignment creation
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle when the user does not want to cancel the assignment creation
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
