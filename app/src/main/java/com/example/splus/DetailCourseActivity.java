package com.example.splus;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splus.my_adapter.AccountAdapter;
import com.example.splus.my_data.Account;
import com.example.splus.my_data.Course;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DetailCourseActivity extends AppCompatActivity {
    ImageButton buttonBack;
    TextView textCourseName;
    TextView textTeacherName;
    public List<Account> listStudent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_course);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        Course course = (Course) bundle.get("detail_course");
        getListStudentId(course.getCourseId());
        getListStudent();

        textCourseName = findViewById(R.id.textNameDetailCourse);
        textCourseName.setText(course.getCourseName());

        textTeacherName = findViewById(R.id.textTeacherDetailCourse);
        textTeacherName.setText(course.getCreaterName());

        RecyclerView recyclerListStudent = findViewById(R.id.recyclerListStudentDetailCourse);
        recyclerListStudent.setLayoutManager(new LinearLayoutManager(DetailCourseActivity.this));

        AccountAdapter accountAdapter = new AccountAdapter(listStudent, this::onClickGoToAccount);

        recyclerListStudent.setAdapter(accountAdapter);

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

    public List<String> listStudentId = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private void getListStudentId(String courseId) {
        db.collection("enrollment")
                .whereEqualTo("courseId", courseId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                listStudentId.add((String) document.getData().get("accountId"));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private void getListStudent() {
        for (int index=0; index<listStudentId.size(); index++) {
            db.collection("Users")
                    .document(listStudentId.get(index))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + documentSnapshot.getData());
                                    listStudent.add((Account) documentSnapshot.getData());
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}