package com.example.splus;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.splus.my_adapter.TabCourseAdapter;
import com.google.android.material.tabs.TabLayout;

public class CourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        TabCourseAdapter tabAdapter = new TabCourseAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // Tabs title
        tabLayout.getTabAt(0).setText("Bài học");
        tabLayout.getTabAt(1).setText("Bài tập");
        tabLayout.getTabAt(2).setText("Thông tin khóa học");
    }
}
