package com.example.splus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.splus.my_dialog.LanguageOptionsDialog;

public class SettingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Switch switchReceiveNotification = findViewById(R.id.switchReceiveNotificationSetting);
        switchReceiveNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(SettingActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingActivity.this, "No", Toast.LENGTH_SHORT).show();
                }
            }
        });
        View buttonLanguage = findViewById(R.id.linearLanguageSetting);
        buttonLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog chooseLangDlg = new LanguageOptionsDialog(SettingActivity.this);
                chooseLangDlg.setOwnerActivity(SettingActivity.this);
                chooseLangDlg.show();
            }
        });

    }
}