package com.example.splus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splus.my_adapter.AccountAdapter;
import com.example.splus.my_data.Account;
import com.example.splus.my_data.Course;

public class DetailCourseActivity extends AppCompatActivity {
    ImageButton buttonBack;
    TextView textCourseName;
    TextView textTeacherName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_course);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        Course course = (Course) bundle.get("detail_course");

        textCourseName = findViewById(R.id.textNameDetailCourse);
        textCourseName.setText(course.getCourseName());

        textTeacherName = findViewById(R.id.textTeacherDetailCourse);
        textTeacherName.setText(course.getCreatorName());

        //RecyclerView recyclerListStudent = findViewById(R.id.recyclerListStudentDetailCourse);
        //recyclerListStudent.setLayoutManager(new LinearLayoutManager(DetailCourseActivity.this));

        //AccountAdapter accountAdapter = new AccountAdapter(getListStudent(course), this::onClickGoToAccount);

        //recyclerListStudent.setAdapter(accountAdapter);

        buttonBack = findViewById(R.id.buttonBackDetailCourse);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void onClickGoToAccount(Account account) {
        Toast.makeText(this, account.getFullname(), Toast.LENGTH_SHORT).show();
    }

    private int getStudenyCount(Course course) {
        return course.getStudentCount();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}