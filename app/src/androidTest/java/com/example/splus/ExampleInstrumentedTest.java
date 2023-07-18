package com.example.splus;

import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.splus.my_data.Course;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        signIn();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection("commentReact")
                .whereEqualTo("courseId", "5HR72xCj5wk4nomk7TSQ")
                .whereEqualTo("userId", user.getUid());
        query.get().addOnCompleteListener(task -> {
            assertEquals(user.getUid(), Integer.toString(task.getResult().size()));
            synchronized (this) {
                notify();
            }
        });
        synchronized (this) {
            try {
                wait();
            } catch (Exception e) {
                Log.getStackTraceString(e);
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Test
    public void signIn() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FirebaseApp.initializeApp(appContext);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        AtomicBoolean isSuccessful = new AtomicBoolean();
        Looper.prepare();
        auth.signInWithEmailAndPassword("phamhung7102003@gmail.com", "12345678").continueWithTask(task -> {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            return storage.getReference("usersAvatar/HZrfqB3QyiR7vaW0DvifIIRBRtJ2").getDownloadUrl();
        }).continueWithTask(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                            .setPhotoUri(task.getResult())
                            .build();
                    return user.updateProfile(request);
                }
                return null;
        }).addOnCompleteListener(data -> {
            if (data.isSuccessful()) {
                Log.d("DEBUG", "UID: " + auth.getCurrentUser().getUid());
                Log.d("DEBUG", "DisplayName:" + auth.getCurrentUser().getDisplayName());
            } else throw new RuntimeException(data.getException());
            isSuccessful.set(true);
        });
        while (!isSuccessful.get());
    }

    @Test
    public void openCourse() {
        Object monitor = new Object();
        synchronized (monitor) {
            Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            FirebaseApp.initializeApp(appContext);
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword("phamhung7102003@gmail.com", "12345678").addOnCompleteListener(authResult -> {
                Course course = new Course("5HR72xCj5wk4nomk7TSQ", "Lập trình hướng lung tung", "Thành", Timestamp.now(), "");
                Intent intent = new Intent(appContext, CourseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("course", course);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                appContext.startActivity(intent);
            });
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}