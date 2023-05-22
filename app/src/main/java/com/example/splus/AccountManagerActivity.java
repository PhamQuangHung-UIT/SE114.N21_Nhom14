package com.example.splus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class AccountManagerActivity extends AppCompatActivity {

    ImageButton buttonBack;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manager);

        buttonBack = findViewById(R.id.buttonBackAccountManager);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        /*
        Intent intent = new Intent(AccountManagerActivity.this, MainActivity.class);
        intent.putExtra("Check",1);
        startActivity(intent);
         */
        finish();
    }
}
