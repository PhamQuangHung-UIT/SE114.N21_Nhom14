package com.example.splus.my_firestore_helper;

import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class FirebaseStorageHelper {
    private static final FirebaseStorage storage = FirebaseStorage.getInstance();

    public static void updateAvatar(String userId, ImageView imageView) {

        StorageReference imageRef = storage.getReference().child("usersAvatar/" + userId);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
            // Load the image using Picasso
            Picasso.get()
                    .load(uri)
                    .into(imageView);
        });
    }
}
