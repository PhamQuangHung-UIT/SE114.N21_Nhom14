package com.example.splus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splus.my_class.ActivityManager;

public class LoginActivity extends AppCompatActivity {

    final int numOfPair = 4;
    final String[] username = {
            "duongtd0109",
            "admin",
            "ad",
            ""
    };
    final String[] password = {
            "12345679",
            "abc123",
            "1",
            ""
    };

    EditText editTextUsername, editTextPassword;
    Button loginButton;
    TextView leadToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActivityManager.add(this);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        leadToSignUp = findViewById(R.id.textLeadToSignUp);

        loginButton.setOnClickListener(v -> {
                int index = 0;
                while (index < numOfPair) {
                    if (editTextUsername.getText().toString().equals(username[index])) {
                        if (editTextPassword.getText().toString().equals(password[index])) {
                            Toast.makeText(LoginActivity.this, R.string.loginSuccessful, Toast.LENGTH_SHORT).show();
                            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();
                            break;
                        }
                    }
                    index++;
                }
                if (index == numOfPair) {
                    Toast.makeText(LoginActivity.this, R.string.loginUnsuccessful, Toast.LENGTH_SHORT).show();
                }
        });

        leadToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}