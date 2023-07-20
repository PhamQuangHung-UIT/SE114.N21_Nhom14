package com.example.splus;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splus.my_data.Account;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;


public class SignUpActivity extends AppCompatActivity {

    EditText editPhoneNumber, editPassword, editRePassword, editFullname, editBirthday, editEmail;
    RadioGroup radioGroupGender, radioGroupRole;
    Button buttonSignUp;
    TextView textSuggestLogin;

    FirebaseAuth mAuth;
    FirebaseFirestore fireStore;

    private int gender = -1;         // 0: male, 1: female, 2: others
    private int role = -1;           // 0: student, 1: teacher

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        setContentView(R.layout.activity_sign_up);

        editPhoneNumber = findViewById(R.id.editPhoneNumberSignUpActivity);
        editPassword = findViewById(R.id.editPasswordSignUpActivity);
        editRePassword = findViewById(R.id.editRetypePasswordSignUpActivity);
        editEmail = findViewById(R.id.editEmailSignUpActivity);
        editFullname = findViewById(R.id.editFullnameSignUpActivity);
        editBirthday = findViewById(R.id.editBirthdaySignUpActivity);
        CheckBox showPasswordButton = findViewById(R.id.checkbox_showPassword);
        View layout = findViewById(R.id.layout);

        showPasswordButton.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked)
                editPassword.setTransformationMethod(null);
            else editPassword.setTransformationMethod(new PasswordTransformationMethod());
        });

        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
        buttonSignUp.setOnClickListener(view -> {
            String phoneNumber = editPhoneNumber.getText().toString();
            String birthday = editBirthday.getText().toString();
            String fullname = editFullname.getText().toString();
            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();
            String repasswd = editRePassword.getText().toString();

            if (password.length() < 6) {
                Snackbar.make(findViewById(R.id.layout), R.string.toast_password_min_length_alert, Snackbar.LENGTH_SHORT).show();
                editPassword.setSelection(0);
                return;
            }
            if (!password.equals(repasswd)) {
                Snackbar.make(findViewById(R.id.layout), R.string.toast_password_matching_alert, Snackbar.LENGTH_SHORT).show();
                editPassword.setSelection(0);
                return;
            }
            if (gender < 0) {
                Snackbar.make(findViewById(R.id.layout), R.string.toast_gender_alert, Snackbar.LENGTH_SHORT).show();
                return;
            }
            if (phoneNumber.isBlank()) {
                Snackbar.make(findViewById(R.id.layout), getString(R.string.toast_empty_alert), Snackbar.LENGTH_SHORT).show();
                editFullname.setSelection(0);
                return;
            }
            if (role < 0) {
                Toast.makeText(SignUpActivity.this, R.string.toast_role_alert, Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(runnable -> new Handler(Looper.myLooper()).post(runnable), task -> {
                        try {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                // Update to getCurrentUser
                                UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest
                                        .Builder()
                                        .setDisplayName(fullname);
                                user.updateProfile(builder.build());
                                // Update to Users collection
                                Account account = new Account();
                                account.setAccountID(user.getUid());
                                account.setEmail(email);
                                account.setFullname(fullname);
                                account.setBirthday(birthday);
                                account.setGender(gender);
                                account.setRole(role);
                                Task<?> addUserTask = fireStore.collection("Users").add(account);
                                Tasks.await(addUserTask);
                                Snackbar.make(layout, R.string.toast_sign_up_successful,
                                        Toast.LENGTH_SHORT).setAction(R.string.title_log_in, v -> finish()).show();

                            } else if (task.getException() != null)
                                throw task.getException();
                        } catch (FirebaseAuthUserCollisionException e) {
                            Snackbar.make(layout, R.string.email_already_in_use_error_msg, Snackbar.LENGTH_SHORT).show();
                        } catch (FirebaseNetworkException e) {
                            Snackbar.make(layout, R.string.connection_error_msg, Snackbar.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Snackbar.make(layout, R.string.unexpected_error_msg, Snackbar.LENGTH_SHORT).show();
                        }
                    });
            returnToLogin();
        });
        textSuggestLogin = findViewById(R.id.textSuggestionLogin);
        textSuggestLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToLogin();
            }
        });
    }

    private void returnToLogin() {
        finish();
    }
}