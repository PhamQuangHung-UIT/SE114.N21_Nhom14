package com.example.splus.my_fragment;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.example.splus.AccountActivity;
import com.example.splus.MainActivity;
import com.example.splus.NotificationActivity;
import com.example.splus.R;
import com.example.splus.SettingActivity;
import com.example.splus.SplashActivity;
import com.example.splus.my_data.Account;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Authentication;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.InputStream;


public class AccountFragment extends Fragment {
    Account user ;
    Uri imageUri;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    ImageView avatar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        TextView fullName = view.findViewById(R.id.textFullnameAccountFragment);
        TextView email = view.findViewById(R.id.textEmailAccountFragment);
        avatar = view.findViewById(R.id.imageAvatarAccountFragment);
        ImageButton imageButtonNotif = view.findViewById(R.id.buttonNotificationAccountFragment);
        ImageView avatar = view.findViewById(R.id.imageAvatarAccountFragment);

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        MainActivity mainActivity = (MainActivity) getActivity();

            user = mainActivity.getAccount();
            fullName.setText(user.getFullname());
            if(!user.getEmail().isEmpty()){
                email.setText(user.getEmail());
            }else if(!user.getPhone().isEmpty()){
                email.setText(user.getPhone());
            }
            if(!firebaseAuth.getCurrentUser().getPhotoUrl().toString().isEmpty()) {
                String temp =
                        "https://firebasestorage.googleapis.com/v0/b/se114-n21.appspot.com/o/users%2FOAJuvuG6rQVzs5R1sTaV7Cd5Tkz2?alt=media&token=43b7811e-6cc0-45cb-8aec-33af93b737ec";
                Glide.with(this)
                        .load(firebaseAuth.getCurrentUser().getPhotoUrl())
                        .into(avatar);
            }
        imageButtonNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToNotification();
            }
        });


        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);

            }
        });

        ImageButton buttonUpdateInfo = view.findViewById(R.id.buttonUpdateAccountFragment);
        buttonUpdateInfo.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AccountActivity.class);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            startActivity(intent);
        });

        // Account manager
        View accountManagerBtn = view.findViewById(R.id.linearAccountFragment);
        accountManagerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AccountActivity.class);
            startActivity(intent);
        });

        // Passworrd
        View password = view.findViewById(R.id.textChangePasswordAccountFragment);
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Change password", Toast.LENGTH_SHORT).show();
            }
        });

        // Setting
        View setting = view.findViewById(R.id.textSettingAccountFragment);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        // Support
        View support = view.findViewById(R.id.textSupportAccountFragment);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Support", Toast.LENGTH_SHORT).show();
            }
        });

        // Log out
        View logout = view.findViewById(R.id.textLogOutAccountFragment);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Confirm")
                        .setMessage("Are you sure to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), "Log out successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), SplashActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        return view;
    }

    private void onClickGoToNotification() {
        Intent intent = new Intent(this.getActivity(), NotificationActivity.class);
        startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data!=null && data.getData()!=null)
        {
            imageUri= data.getData();
            avatar.setImageURI(imageUri);
            updatePictureProfile(imageUri);
        }
    }
    private void updatePictureProfile(Uri imageUri){

            final StorageReference storageReference1 =storageReference.child("usersAvatar/"+firebaseAuth.getCurrentUser().getUid());
            storageReference1.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(avatar);
                            updateImageUri(uri);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Cannot change your picture profile!", Toast.LENGTH_SHORT).show();
                }
            });
    }
    private void updateImageUri(Uri uri){

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse(uri.toString()))
                .build();

        firebaseAuth.getCurrentUser().updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Your picture profile has changed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Cannot change your picture profile!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @GlideModule
    public class MyAppGlideModule extends AppGlideModule {
        @Override
        public void registerComponents(Context context, Glide glide, Registry registry) {
            // Register FirebaseImageLoader to handle StorageReference
            registry.append(StorageReference.class, InputStream.class,
                    new FirebaseImageLoader.Factory());
        }
    }
}
