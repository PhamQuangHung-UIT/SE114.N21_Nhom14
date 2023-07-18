package com.example.splus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.splus.my_class.ActivityManager;
import com.example.splus.my_class.LocaleHelper;
import com.example.splus.my_data.Account;
import com.example.splus.my_dialog.LoadingDialog;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    EditText editUsername, editPassword;
    Button buttonLogin;
    TextView textSuggestSignUp;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        ActivityManager.add(this);

        editUsername = findViewById(R.id.editUsernameLoginActivity);
        editPassword = findViewById(R.id.editPasswordLoginActivity);
        buttonLogin = findViewById(R.id.buttonLogin);
        textSuggestSignUp = findViewById(R.id.textSuggestSignUp);

        textSuggestSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

        buttonLogin.setOnClickListener(v -> {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            // Check username or password blank
            if (username.isBlank() || password.isBlank()) {
                if (username.isBlank())
                    editUsername.setError(getString(R.string.empty_username_msg));
                if (password.isBlank())
                    editPassword.setError(getString(R.string.empty_password_msg));
                return;
            }

            startLoading();
            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            try {
                                String id = mAuth.getCurrentUser().getUid();
                                Task<DocumentSnapshot> findAccountTask = db.collection("Users").document(id).get();
                                Account account = Tasks.await(findAccountTask).toObject(Account.class);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("account", account);
                                intent.putExtras(bundle);
                                stopLoading();
                                startActivity(intent);
                                finish();
                            } catch (InterruptedException | ExecutionException e) {
                                Log.wtf("ERROR", "Error in get account data", e);
                                Snackbar.make(findViewById(R.id.layout), R.string.unexpected_error_msg, Snackbar.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException()
                            Snackbar.make(findViewById(R.id.layout), R.string.toast_login_unsuccessful, Snackbar.LENGTH_SHORT).show();
                        }
                    });
        });

        textSuggestSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    private void startLoading() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("loading");
        if (fragment != null)
            transaction.remove(fragment);
        LoadingDialog.getInstance().show(transaction, "loading");
    }

    private void stopLoading() {
        LoadingDialog.getInstance().dismiss();
    }
}