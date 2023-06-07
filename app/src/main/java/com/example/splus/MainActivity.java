package com.example.splus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.splus.my_adapter.MainViewPagerAdapter;
import com.example.splus.my_class.ActivityManager;
import com.example.splus.my_class.LocaleHelper;
import com.example.splus.my_data.Account;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private ViewPager2 pager;
    private Account account;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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