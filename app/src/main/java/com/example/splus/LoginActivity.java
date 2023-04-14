package com.example.splus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    final int numOfPair = 2;
    final String[] username = {
            "duongtd0109",
            "admin"
    };
    final String[] password = {
            "12345679",
            "abc123"
    };

    EditText editTextUsername, editTextPassword;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = 0;
                while (index < numOfPair) {
                    if (editTextUsername.getText().toString().equals(username[index])) {
                        if (editTextPassword.getText().toString().equals(password[index])) {
                            Toast.makeText(LoginActivity.this, "R.string.loginSuccessful", Toast.LENGTH_SHORT).show();
                            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            break;
                        }
                    }
                    index++;
                }
                if (index == numOfPair) {
                    Toast.makeText(LoginActivity.this, "R.stirng.loginUnsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}