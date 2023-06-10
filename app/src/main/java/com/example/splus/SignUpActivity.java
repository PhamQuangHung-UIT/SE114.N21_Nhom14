package com.example.splus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splus.my_data.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioMaleSignUpActivity:
                        // Toast.makeText(SignUpActivity.this, R.string.text_gender_male, Toast.LENGTH_SHORT).show();
                        gender = 0;
                        break;
                    case R.id.radioFemaleSignUpActivity:
                        // Toast.makeText(SignUpActivity.this, R.string.text_gender_female, Toast.LENGTH_SHORT).show();
                        gender = 1;
                        break;
                    case R.id.radioOtherSignUpActivity:
                        // Toast.makeText(SignUpActivity.this, R.string.text_gender_others, Toast.LENGTH_SHORT).show();
                        gender = 2;
                        break;
                    default:
                        gender = -1;
                }
            }
        });
        radioGroupRole = findViewById(R.id.radioGroupRole);
        radioGroupRole.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioStudentRoleSignUpActivity:
                        // Toast.makeText(SignUpActivity.this, R.string.text_role_student, Toast.LENGTH_SHORT).show();
                        role = 0;
                        break;
                    case R.id.radioTeacherRoleSignUpActivity:
                        // Toast.makeText(SignUpActivity.this, R.string.text_role_teacher, Toast.LENGTH_SHORT).show();
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
                // local checking
                // empty or week username

                String username = editUsername.getText().toString();
                String birthday = editBirthday.getText().toString();
                String fullname = editFullname.getText().toString();
                String email = editEmail.getText().toString();
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

                                    Toast.makeText(SignUpActivity.this, "Authentication successfuly.",
                                            Toast.LENGTH_SHORT).show();
                                    String UserID =mAuth.getCurrentUser().getUid();
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("email", email);
                                    user.put("username", username);
                                    user.put("fullname", fullname);
                                    user.put("password", password);
                                    user.put("birthday",birthday);
                                    user.put("gender",gender);
                                    user.put("role",role);
                                   fireStore.collection("Users").document(UserID).set(user);

                                } else {
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                // query SELECT username FROM account WHERE account_username= :username
                // if result list<account> != null && list<account> is empty
                // insert a new record into account table
                // String username = editUsername.getText().toString();
                // String password = editPassword.getText().toString().sha256();
                // String fullname = editFullname.getText().toString();
                // String birthday = editBirthday.getText().toString();
                // (global variable, datatype:int) gender
                // (global variable, datatype:int) role
                nextActivity();
                // else
                // Toast.makeText(SignUpActivity.this, R.string.toast_sign_up_unsuccessful, Toast.LENGTH_SHORT).show();
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