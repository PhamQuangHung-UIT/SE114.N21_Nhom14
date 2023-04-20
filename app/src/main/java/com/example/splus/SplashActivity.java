package com.example.splus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splus.my_class.ActivityManager;

//@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView1, textView2;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ActivityManager.add(this);

        imageView = findViewById(R.id.splusLogo);
        textView1 = findViewById(R.id.description1);
        textView2 = findViewById(R.id.description2);

        animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        imageView.startAnimation(animation);
        textView1.startAnimation(animation);
        textView2.startAnimation(animation);

        Handler handler = new Handler();
        handler.postDelayed(this::nextActivity, 1000);
    }
    private void nextActivity() {
        /*
        // if user has already loged in
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent)
        */

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}