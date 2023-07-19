package com.example.splus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.example.splus.my_class.ActivityManager;
import com.example.splus.my_class.LocaleHelper;
import com.example.splus.my_data.Account;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.ExecutionException;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity implements MotionLayout.TransitionListener {
    MotionLayout splashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.d("LocaleHelper", LocaleHelper.getLanguage(this));
        ActivityManager.add(this);

        splashScreen = findViewById(R.id.layoutSplashActivity);
        splashScreen.setTransitionListener(this);


        startLoading();

        Runnable runnable = this::nextActivity;
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    private void nextActivity() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            try {
                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Task<DocumentSnapshot> findAccountTask = db.collection("Users").document(id).get();
                Account account = Tasks.await(findAccountTask).toObject(Account.class);
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("account", account);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            } catch (ExecutionException exception) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setPositiveButton(R.string.retry, (dialog, i) -> recreate())
                        .setNegativeButton(R.string.cancel, (dialog, i) -> finish());
                // Check exception cause
                try {
                    throw (Exception) exception.getCause();
                } catch (FirebaseNetworkException e) {
                    builder.setMessage(R.string.connection_error_msg);
                } catch (Exception e) {
                    builder.setMessage(R.string.unexpected_error_msg);
                }
            } catch (InterruptedException e) {
                Log.e("Error", "InterruptedException in SplashActivity", e);
            }
        }
    }

    private void startLoading() {
        splashScreen.setTransition(R.id.transition_loading_screen);
    }

    @Override
    public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {

    }

    @Override
    public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {

    }

    @Override
    public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
        if (currentId == R.id.endLoadingScreen)
            motionLayout.setProgress(0);
    }

    @Override
    public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {

    }
}