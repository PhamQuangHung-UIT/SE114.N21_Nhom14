package com.example.splus;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.splus.my_class.ActivityManager;
import com.example.splus.my_data.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText editUsername, editPassword;
    Button buttonLogin;
    TextView textSuggestSignUp;
    FirebaseAuth mAuth ;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        MyCallback myCallback;
        ActivityManager.add(this);

        editUsername = findViewById(R.id.editUsernameLoginActivity);
        editPassword = findViewById(R.id.editPasswordLoginActivity);
        buttonLogin = findViewById(R.id.buttonLogin);
        textSuggestSignUp = findViewById(R.id.textSuggestSignUp);

        buttonLogin.setOnClickListener(v -> {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();

            if (username.isEmpty()) {
                Toast.makeText(LoginActivity.this, R.string.toast_email_empty_alert, Toast.LENGTH_SHORT).show();
                editUsername.setSelection(0);
                return;
            }
            if (password.isEmpty()) {
                Toast.makeText(LoginActivity.this, R.string.toast_password_empty_alert, Toast.LENGTH_SHORT).show();
                editPassword.setSelection(0);
                return;
            }
            if (password.length()<6) {
                Toast.makeText(LoginActivity.this, R.string.toast_password_min_length_alert, Toast.LENGTH_SHORT).show();
                editPassword.setSelection(0);
                return;
            }
            // checkLogin(username, password.sha256())
            List<Account> accountList = new ArrayList<>();
            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull  Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, R.string.toast_login_successful, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                Bundle bundle = new Bundle();
                                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(mAuth.getCurrentUser().getUid());
                                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if (documentSnapshot.exists()) {
                                            Account account = documentSnapshot.toObject(Account.class);
                                            bundle.putSerializable("account",account);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "signInWithEmail:failure", task.getException()
                                Toast.makeText(LoginActivity.this, R.string.toast_login_unsuccessful, Toast.LENGTH_SHORT).show();
                            }
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

    private List<Account> checkLogin(String username, String password) {
        List<Account> accountList = new ArrayList<>();
        int role = password.equals("0")? 0:1;
        accountList.add(new Account("", username, role));
        return accountList;
    }
    public interface MyCallback {
        void onCallback(Account account);
    }
}