package com.example.splus;

import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.splus.my_data.Course;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.checkerframework.checker.units.qual.C;
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
        assertEquals("com.example.splus", appContext.getPackageName());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Test
    public void signIn() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FirebaseApp.initializeApp(appContext);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        AtomicBoolean isSuccessful = new AtomicBoolean();
        Looper.prepare();
        auth.signInWithEmailAndPassword("phamhung7102003@gmail.com", "12345678")
                .addOnCompleteListener(new Activity(), authResult -> {
                    if (authResult.isSuccessful()) {
                        Toast.makeText(appContext, "Login Success", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = auth.getCurrentUser();
                        Log.d("DEBUG", "UID: " + user.getUid());
                        Log.d("DEBUG", "DisplayName:" + user.getDisplayName());
                        isSuccessful.set(true);
                    }
                });
        while (!isSuccessful.get());
    }

    @Test
    public void openCourse() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FirebaseApp.initializeApp(appContext);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword("phamhung7102003@gmail.com", "12345678");
        Course course = new Course("5HR72xCj5wk4nomk7TSQ", "Lập trình hướng lung tung", "Thành", "", 0);
        Intent intent = new Intent(appContext, CourseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("course", course);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(intent);
        while (true);
    }
}