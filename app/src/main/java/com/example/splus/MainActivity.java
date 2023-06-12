package com.example.splus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.splus.my_adapter.MainViewPagerAdapter;
import com.example.splus.my_class.ActivityManager;
import com.example.splus.my_class.LocaleHelper;
import com.example.splus.my_data.Account;

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
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public List<String> listCourseId, listLessonId, listAssignId;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get data from login activity
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        } else {
            account = (Account) bundle.get("account");
        }

        listCourseId = new ArrayList<>();
        listLessonId = new ArrayList<>();
        listAssignId = new ArrayList<>();

        if (account.getRole() == STUDENT_ROLE) {

            db.collection("enrollment")
                    .whereEqualTo("accountId", account.getAccountID())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot documentCourses : task.getResult()) {
                                    listCourseId.add((String) documentCourses.getData().get("courseId"));
            db.collection("lessons")
                    .whereEqualTo("courseId", listCourseId.get(listCourseId.size()-1))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot documentLesson : task.getResult()) {
                                    listLessonId.add((String) documentLesson.getId());
            db.collection("assignments")
                    .whereEqualTo("lessonId", listLessonId.get(listLessonId.size()-1))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot documentAssignment : task.getResult()) {
                                    listAssignId.add((String) documentAssignment.getId());
                                }
                            }
                        }
                    });
                                }
                            }
                        }
                    });
                                }
                            }
                        }
                    });
        } else if (account.getRole() == TEACHER_ROLE) {
            listCourseId = new ArrayList<>();
            db.collection("courses")
                    .whereEqualTo("accountId", account.getAccountID())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    listCourseId.add((String) document.getId());
                                }
                            }
                        }
                    });
        } else {
            return;
        }

        ActivityManager.add(this);

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
                super.onPageSelected(position);
                navigation.getMenu().getItem(position).setChecked(true);
            }
        });
    }
}