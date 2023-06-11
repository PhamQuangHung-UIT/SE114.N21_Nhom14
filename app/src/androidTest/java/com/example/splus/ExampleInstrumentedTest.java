package com.example.splus;

import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    public FirebaseUser signIn() {
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
        return auth.getCurrentUser();
    }
}