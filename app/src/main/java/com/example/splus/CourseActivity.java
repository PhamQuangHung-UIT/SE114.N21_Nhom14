package com.example.splus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.splus.my_adapter.CourseViewPagerAdapter;
import com.example.splus.my_data.Course;
import com.example.splus.my_viewmodel.CourseViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CourseActivity extends AppCompatActivity {
    TextView className;
    ImageButton buttonBack;
    Button buttonDetailClass;
    Button buttonContactTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        CourseViewModel model = new ViewModelProvider(this).get(CourseViewModel.class);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        model.setCurrentCourse((Course)bundle.get("course"));

        TabLayout tabLayout = findViewById(R.id.tabLayout_CourseActivity);
        ViewPager2 viewPager = findViewById(R.id.viewPagerCourse);

        CourseViewPagerAdapter adapter = new CourseViewPagerAdapter(this);
        tabLayout.addOnTabSelectedListener(adapter);
        viewPager.setAdapter(adapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                adapter.onTabUnselected(tabLayout.getTabAt(positionOffset < 0.5 ? 1 - position : position));
                adapter.onTabSelected(tabLayout.getTabAt(positionOffset < 0.5 ? position : 1 - position));
            }
        });
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
           if (position == 0) {
               tab.setText(R.string.lesson_list);
           } else {
               tab.setText(R.string.comments);
               tab.setIcon(R.drawable.baseline_comment_24);
           }
        }).attach();

        className = findViewById(R.id.textNameCourseActivity);
        className.setText(model.getCurrentCourse().getCourseName());

        buttonBack = findViewById(R.id.buttonBackCourseActivity);
        buttonBack.setOnClickListener(v -> onBackPressed());

        buttonDetailClass = findViewById(R.id.buttonInfoCourseActivity);
        buttonDetailClass.setOnClickListener(view -> {
            Intent intent = new Intent(CourseActivity.this, DetailCourseActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("detail_course", model.getCurrentCourse());
            intent.putExtras(b);
            startActivity(intent);
        });

        buttonContactTeacher = findViewById(R.id.buttonContactCourseActivity);
        buttonContactTeacher.setOnClickListener(view
                -> Toast.makeText(CourseActivity.this, "Contact button is pressed", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}