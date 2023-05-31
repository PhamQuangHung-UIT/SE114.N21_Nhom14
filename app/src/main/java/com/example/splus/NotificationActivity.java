package com.example.splus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.splus.my_adapter.NotifViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class NotificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        TabLayout tabLayout = findViewById(R.id.tabLayoutNotificationActivity);
        ViewPager viewPager = findViewById(R.id.viewPagerNotificationActivity);

        NotifViewPagerAdapter notifViewPagerAdapter = new NotifViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(notifViewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        ImageButton buttonClose = findViewById(R.id.buttonCloseNotificationActivity);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}