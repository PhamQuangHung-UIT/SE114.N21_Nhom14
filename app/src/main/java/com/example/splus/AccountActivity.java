package com.example.splus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.splus.my_data.Account;
import com.example.splus.my_fragment.AccountFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;


public class AccountActivity extends AppCompatActivity {

    ImageButton buttonBack;
    int gender;
    Button buttonUpdate;
    EditText editUsername, editFullname, editBirthday;
    RadioGroup radioGroupGender;
    RadioButton maleRadioButton;
    RadioButton femaleRadioButton;
    RadioButton otherGenderRadioButton;
    ImageView avatar;
    FirebaseAuth mAuth;
    FirebaseFirestore fireStore;
    Account user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        avatar = findViewById(R.id.imageAvatarAccountActivity);
        editFullname = findViewById(R.id.editFullNameAccountActivity);
        editBirthday = findViewById(R.id.editBirthdayAccountActivity);
        radioGroupGender = findViewById(R.id.radioGroupGenderAccountActivity);
        maleRadioButton = findViewById(R.id.radioMaleAccountActivity);
        femaleRadioButton = findViewById(R.id.radioFemaleAccountActivity);
        otherGenderRadioButton =findViewById(R.id.radioOtherAccountActivity);
        editUsername = findViewById(R.id.editUserNameAccountActivity);

        fireStore= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = MainActivity.getAccount();

        editUsername.setText(user.getUsername());
        editFullname.setText(user.getFullname());
        editBirthday.setText(user.getBirthday());
        gender = (int) user.getGender();

        if (mAuth.getCurrentUser().getPhotoUrl() != null) {
            Glide.with(this)
                    .load(mAuth.getCurrentUser().getPhotoUrl())
                    .into(avatar);
        }

        switch (gender) {
            case 0:
                maleRadioButton.toggle();
                break;
            case 1:
                femaleRadioButton.toggle();
                break;
            case 2:
                otherGenderRadioButton.toggle();
                break;
            default:
                Toast.makeText(this,"failed" , Toast.LENGTH_SHORT).show();
        }
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioMaleAccountActivity:
                        // Toast.makeText(SignUpActivity.this, R.string.text_gender_male, Toast.LENGTH_SHORT).show();
                        gender = 0;
                        break;
                    case R.id.radioFemaleAccountActivity:
                        // Toast.makeText(SignUpActivity.this, R.string.text_gender_female, Toast.LENGTH_SHORT).show();
                        gender = 1;
                        break;
                    case R.id.radioOtherAccountActivity:
                        // Toast.makeText(SignUpActivity.this, R.string.text_gender_others, Toast.LENGTH_SHORT).show();
                        gender = 2;
                        break;
                    default:
                        gender = -1;
                }
            }
        });
        buttonBack = findViewById(R.id.buttonBackAccountAct);


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonUpdate = findViewById(R.id.buttonUpdateAccountActivity);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString();
                String birthday = editBirthday.getText().toString();
                String fullname = editFullname.getText().toString();
                String UserID =mAuth.getCurrentUser().getUid();
                Map<String, Object> newUser = new HashMap<>();
                newUser.put("username", username);
                newUser.put("fullname", fullname);
                newUser.put("birthday", birthday);
                newUser.put("gender", gender);
                fireStore.collection("Users").document(UserID).set(user, SetOptions.merge()).
                        addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AccountActivity.this, "Update successful", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AccountActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                user.setUsername(username);
                user.setFullname(fullname);
                user.setBirthday(birthday);
                user.setGender(gender);
                MainActivity.setAccount(user);
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        /*
        Intent intent = new Intent(AccountActActivity.this, MainActivity.class);
        intent.putExtra("Check",1);
        startActivity(intent);
         */
        finish();
    }
}
