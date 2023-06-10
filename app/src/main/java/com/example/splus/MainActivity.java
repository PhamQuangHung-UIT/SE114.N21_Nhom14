package com.example.splus;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.splus.my_adapter.MainViewPagerAdapter;
import com.example.splus.my_class.ActivityManager;
import com.example.splus.my_class.LocaleHelper;
import com.example.splus.my_data.Account;
import com.example.splus.my_data.Assignment;
import com.example.splus.my_data.Lesson;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int STUDENT_ROLE = 0;
    public static final int TEACHER_ROLE = 1;
    private BottomNavigationView navigation;
    private ViewPager2 pager;
    public Account account;
    public FirebaseFirestore db;

    public List<String> listCourseId;
    public List<String> listLessonId;
    public List<String> listAssignmentId;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ActivityManager.add(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        account = (Account) bundle.get("account");

        navigation = findViewById(R.id.bottomNavigationMainActivity);
        pager = findViewById(R.id.pagerMainActivity);

        setUpViewPager();

        navigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigationHomeFragment:
                    pager.setCurrentItem(0);
                    // Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigationCourseFragment:
                    pager.setCurrentItem(1);
                    // Toast.makeText(MainActivity.this, "Courses", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigationAssignmentFragment:
                    pager.setCurrentItem(2);
                    if (account.getRole() == 1) {
                        item.setTitle(R.string.title_fragment_assign);
                    }
                    // Toast.makeText(MainActivity.this, "Assignments", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigationSettingFragment:
                    pager.setCurrentItem(3);
                    // Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });

        listCourseId = getCourseId();
        listLessonId = getAllLessonId();
        listAssignmentId = getAllAssignmentId();

    }

    public List<String> getCourseId() {
        List<String> listCourse = new ArrayList<>();
        if (account.getRole() == STUDENT_ROLE) {
            db.collection("enrollment")
                    .whereEqualTo("accountId", this.account.getAccountID())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    listCourse.add((String) document.getData().get("courseId"));
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        } else {
            db.collection("courses")
                    .whereEqualTo("ownerId", this.account.getAccountID())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    listCourse.add((String) document.getData().get("courseId"));
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
        return listCourse;
    }

    public List<String> getLessonId(String courseId) {
        List<String> listLessonId = new ArrayList<>();

        db.collection("lesson")
                .whereEqualTo("courseId", courseId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                listLessonId.add(document.getId());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return listLessonId;
    }

    public List<String> getAllLessonId() {
        List<String> listLessonId = new ArrayList<>();
        int quantity = this.listCourseId.size();
        for (int index=0; index < quantity; index++) {
            db.collection("lessons")
                    .whereEqualTo("courseId", this.listCourseId.get(index))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    listLessonId.add(document.getId());
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
        return listLessonId;
    }

    public List<String> getAssignmentId(String lessonId) {
        List<String> listAssignmentId = new ArrayList<>();

        db.collection("assignment")
                .whereEqualTo("lessonId", lessonId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                listAssignmentId.add(document.getId());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return listAssignmentId;
    }

    public List<String> getAllAssignmentId() {
        List<String> listAssignmentId = new ArrayList<>();
        int quantity = this.listLessonId.size();
        for (int index=0; index < quantity; index++) {
            db.collection("lessons")
                    .whereEqualTo("courseId", this.listLessonId.get(index))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    listAssignmentId.add(document.getId());
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
        return listAssignmentId;
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setUpViewPager() {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), this.account.getRole());

        pager.setAdapter(adapter);

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                navigation.getMenu().getItem(position).setChecked(true);
            }
        });
    }

    public Account getAccount() {
        return this.account;
    }

    public FirebaseFirestore getDb() {
        return this.db;
    }

}