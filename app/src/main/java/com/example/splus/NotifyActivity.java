package com.example.splus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.splus.my_adapter.NotifViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class NotifyActivity extends AppCompatActivity {

    ImageButton buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        TabLayout tabLayout = findViewById(R.id.tabLayoutNotifyAct);
        ViewPager viewPager = findViewById(R.id.viewPagerNotifyAct);

        NotifViewPagerAdapter notifViewPagerAdapter = new NotifViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(notifViewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        buttonBack = findViewById(R.id.buttonBackNotifyAct);
        buttonBack.setOnClickListener(new View.OnClickListener() {
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