package com.example.splus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splus.my_class.ActivityManager;
import com.example.splus.my_class.LocaleHelper;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    Animation animation;
    LinearLayout slashScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.d("LocaleHelper*******: ", LocaleHelper.getLanguage(this));
        ActivityManager.add(this);

        slashScreen = findViewById(R.id.linearSplashActivity);

        animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        slashScreen.startAnimation(animation);

        Handler handler = new Handler();
        handler.postDelayed(this::nextActivity, 1000);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }
    private void nextActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}