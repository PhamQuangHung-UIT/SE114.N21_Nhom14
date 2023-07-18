package com.example.splus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.splus.my_data.Account;


public class AccountActivity extends AppCompatActivity {

    ImageButton buttonBack;

    Button buttonUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Account account = (Account)bundle.getSerializable("account");

        buttonBack = findViewById(R.id.buttonBackAccountAct);
        EditText editText_fullName = findViewById(R.id.editFullNameAccountActivity);
        RadioGroup genderRadioGroup = findViewById(R.id.radioGroupGender);
        EditText editText_birthday = findViewById(R.id.editBirthdayAccountActivity);
        EditText editText_phoneNumber = findViewById(R.id.editPhoneAccountActivity);
        buttonBack.setOnClickListener(view -> onBackPressed());

        buttonUpdate = findViewById(R.id.buttonUpdateAccountActivity);
        buttonUpdate.setOnClickListener(view -> {

        });
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }
}
