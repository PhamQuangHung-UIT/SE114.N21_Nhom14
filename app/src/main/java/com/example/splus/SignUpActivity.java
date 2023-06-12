package com.example.splus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText editUsername, editPassword, editRePassword, editFullname, editBirthday,editEmail;
    RadioGroup radioGroupGender, radioGroupRole;
    Button buttonSignUp;
    TextView textSuggestLogin;

    FirebaseAuth mAuth ;
    FirebaseFirestore fireStore;
   // DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://se114-n21-default-rtdb.firebaseio.com/");
    private int gender = -1;         // 0: male, 1: female, 2: others
    private int role = -1;           // 0: student, 1: teacher

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        setContentView(R.layout.activity_sign_up);

        editUsername = findViewById(R.id.editUsernameSignUpActivity);
        editPassword = findViewById(R.id.editPasswordSignUpActivity);
        editRePassword = findViewById(R.id.editRetypePasswordSignUpActivity);
        editEmail=findViewById(R.id.editEmailSignUpActivity);
        editFullname = findViewById(R.id.editFullnameSignUpActivity);
        editBirthday = findViewById(R.id.editBirthdaySignUpActivity);

        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioMaleSignUpActivity:
                        gender = 0;
                        break;
                    case R.id.radioFemaleSignUpActivity:
                        gender = 1;
                        break;
                    case R.id.radioOtherSignUpActivity:
                        gender = 2;
                        break;
                    default:
                        gender = -1;
                }
            }
        });
        radioGroupRole = findViewById(R.id.radioGroupRole);
        radioGroupRole.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioStudentRoleSignUpActivity:
                        role = 0;
                        break;
                    case R.id.radioTeacherRoleSignUpActivity:
                        role = 1;
                        break;
                    default:
                        role = -1;
                }
            }
        });
        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString();
                String birthday = editBirthday.getText().toString();
                String fullname = editFullname.getText().toString();
                String email = editEmail.getText().toString();
                // local checking
                if (username.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, R.string.toast_empty_alert, Toast.LENGTH_SHORT).show();
                    editUsername.setSelection(0);
                    return;
                }
                String password = editPassword.getText().toString();
                String repasswd = editRePassword.getText().toString();

                if (!password.equals(repasswd)) {
                    Toast.makeText(SignUpActivity.this, R.string.toast_password_matching_alert, Toast.LENGTH_SHORT).show();
                    editPassword.setText("");
                    editRePassword.setText("");
                    editPassword.setSelection(0);
                    return;
                }
                if (password.length()<6) {
                    Toast.makeText(SignUpActivity.this, R.string.toast_password_min_length_alert, Toast.LENGTH_SHORT).show();
                    editPassword.setSelection(0);
                    return;
                }
                if (gender < 0) {
                    Toast.makeText(SignUpActivity.this, R.string.toast_gender_alert, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (role < 0) {
                    Toast.makeText(SignUpActivity.this, R.string.toast_role_alert, Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String userId = mAuth.getCurrentUser().getUid();
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("email", email);
                                    user.put("username", username);
                                    user.put("fullname", fullname);
                                    user.put("password", password);
                                    user.put("birthday", birthday);
                                    user.put("gender", gender);
                                    user.put("role", role);
                                    fireStore.collection("Users").document(userId).set(user);
                                    Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                nextActivity();
            }
        });
        textSuggestLogin = findViewById(R.id.textSuggestionLogin);
        textSuggestLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity();
            }
        });
    }
    private void nextActivity() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}