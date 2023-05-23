package com.example.splus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.splus.my_adapter.MainViewPagerAdapter;
import com.example.splus.my_class.ActivityManager;
import com.example.splus.my_class.LocaleHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mNavigationView;
    private ViewPager2 mViewPager;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityManager.add(this);

        mNavigationView = findViewById(R.id.bot_nav);
        mViewPager = findViewById(R.id.view_pager_2);

        setUpViewPager();

        mNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_home:
                    mViewPager.setCurrentItem(0);
                    // Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_class:
                    mViewPager.setCurrentItem(1);
                    // Toast.makeText(MainActivity.this, "Class", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_homework:
                    mViewPager.setCurrentItem(2);
                    // Toast.makeText(MainActivity.this, "Homework", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_setting:
                    mViewPager.setCurrentItem(3);
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
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(), getLifecycle());

        mViewPager.setAdapter(adapter);

        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });
    }
}