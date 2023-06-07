package com.example.splus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splus.my_class.ActivityManager;
import com.example.splus.my_class.LocaleHelper;
import com.example.splus.my_data.Account;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText editUsername, editPassword;
    Button buttonLogin;
    TextView textSuggestSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActivityManager.add(this);

        editUsername = findViewById(R.id.editUsernameLoginActivity);
        editPassword = findViewById(R.id.editPasswordLoginActivity);
        buttonLogin = findViewById(R.id.buttonLogin);
        textSuggestSignUp = findViewById(R.id.textSuggestSignUp);

        buttonLogin.setOnClickListener(v -> {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            // checkLogin(username, password.sha256())
            List<Account> accountList = checkLogin(username, password);
            if (!accountList.isEmpty()) {
                Toast.makeText(LoginActivity.this, R.string.toast_login_successful, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("account", accountList.get(0));
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, R.string.toast_login_unsuccessful, Toast.LENGTH_SHORT).show();
            }
        });

        textSuggestSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }
    private List<Account> checkLogin(String username, String password) {
        List<Account> accountList = new ArrayList<>();
        int role = password.equals("0")? 0:1;
        accountList.add(new Account(0, username, role));
        return accountList;
    }
}