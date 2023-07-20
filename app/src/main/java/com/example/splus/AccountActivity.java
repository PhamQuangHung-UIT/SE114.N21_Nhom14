package com.example.splus;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.splus.my_data.Account;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class AccountActivity extends AppCompatActivity {

    ImageButton buttonBack;
    int gender;
    Button buttonUpdate;
    EditText editFullname, editBirthday, editPhone;
    RadioGroup radioGroupGender;
    RadioButton maleRadioButton;
    RadioButton femaleRadioButton;
    RadioButton otherGenderRadioButton;
    ImageView avatar;
    FirebaseAuth mAuth;
    FirebaseFirestore fireStore;

    Account user;

    Uri imageUrl;

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
        otherGenderRadioButton = findViewById(R.id.radioOtherAccountActivity);
        editPhone = findViewById(R.id.editPhoneAccountActivity);

        fireStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = MainActivity.getAccount();

        editFullname.setText(user.getFullname());
        editBirthday.setText(user.getBirthday());
        gender = (int) user.getGender();

        if (mAuth.getCurrentUser().getPhotoUrl() != null) {
            Picasso.get()
                    .load(mAuth.getCurrentUser().getPhotoUrl())
                    .into(avatar);
        }
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                imageUrl = result.getData().getData();
                Picasso.get().load(imageUrl).into(avatar);
            }
        });
        ActivityResultLauncher<String> permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
            if (!granted)
                Snackbar.make(findViewById(R.id.layout), R.string.permission_denied_msg, Snackbar.LENGTH_SHORT).show();
        });
        avatar.setOnClickListener(view -> {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permissionCheck == PackageManager.PERMISSION_DENIED)
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            launcher.launch(intent);
        });

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
                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
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
        buttonUpdate.setOnClickListener(view -> {
            String phone = editPhone.getText().toString();
            String birthday = editBirthday.getText().toString();
            String fullname = editFullname.getText().toString();
            String UserID = mAuth.getCurrentUser().getUid();
            Map<String, Object> newUser = new HashMap<>();
            newUser.put("phone", phone);
            newUser.put("fullname", fullname);
            newUser.put("birthday", birthday);
            newUser.put("gender", gender);
            Task<?> newTask = fireStore.collection("Users").document(UserID).update(newUser);
            if (imageUrl != null)
                    newTask.continueWithTask(task -> {
                        StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("usersAvatar/" + user.getAccountID());
                        return imageRef.putFile(imageUrl);
                    }).continueWithTask(task -> task.getResult().getMetadata().getReference().getDownloadUrl())
                    .continueWithTask(task -> {
                        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder()
                                .setDisplayName(fullname)
                                .setPhotoUri(task.getResult());
                        mAuth.getCurrentUser().updateProfile(builder.build());
                        return fireStore.collection("Users").document(mAuth.getCurrentUser().getUid())
                                .update("avatarUrl", task.getResult().toString());
                    });
            newTask.addOnSuccessListener(aVoid -> {
                        setResult(RESULT_OK);
                        finish();
                    }).addOnFailureListener(exception -> {
                        try {
                            throw exception;
                        } catch (FirebaseNetworkException e) {
                            Snackbar.make(findViewById(R.id.layout), R.string.connection_error_msg, Snackbar.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Snackbar.make(findViewById(R.id.layout), R.string.unexpected_error_msg, Snackbar.LENGTH_SHORT).show();
                        }
                    });
            user.setFullname(fullname);
            user.setBirthday(birthday);
            user.setGender(gender);
            MainActivity.setAccount(user);
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
