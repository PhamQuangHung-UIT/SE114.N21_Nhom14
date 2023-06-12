package com.example.splus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.splus.my_class.ActivityManager;
import com.example.splus.my_class.LocaleHelper;
import com.example.splus.my_data.Account;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    EditText editUsername, editPassword;
    Button buttonLogin;
    TextView textSuggestSignUp;
    FirebaseAuth mAuth ;
    FirebaseFirestore firestore;
    Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        ActivityManager.add(this);

        editUsername = findViewById(R.id.editUsernameLoginActivity);
        editPassword = findViewById(R.id.editPasswordLoginActivity);
        buttonLogin = findViewById(R.id.buttonLogin);
        textSuggestSignUp = findViewById(R.id.textSuggestSignUp);

        buttonLogin.setOnClickListener(v -> {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();

            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String userId = mAuth.getCurrentUser().getUid();
                                DocumentReference ref = firestore.collection("Users").document(userId);
                                ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document != null) {
                                                account = new Account(
                                                        userId,
                                                        username,
                                                        Math.toIntExact(document.getLong("role")),
                                                        document.getString("fullname"),
                                                        document.getString("birthday"),
                                                        Math.toIntExact(document.getLong("gender")),
                                                        document.getString("email")
                                                );
                                                Toast.makeText(LoginActivity.this, R.string.toast_login_successful, Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable("account", account);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(LoginActivity.this, R.string.toast_login_unsuccessful, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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

}