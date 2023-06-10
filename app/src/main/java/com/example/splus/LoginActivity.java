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
import com.example.splus.my_class.LocaleHelper;
import com.example.splus.my_data.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
    int currentLoginAccountIndex;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        ActivityManager.add(this);

        editUsername = findViewById(R.id.editUsernameLoginActivity);
        editPassword = findViewById(R.id.editPasswordLoginActivity);
        buttonLogin = findViewById(R.id.buttonLogin);
        textSuggestSignUp = findViewById(R.id.textSuggestSignUp);

        buttonLogin.setOnClickListener(v -> {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            // checkLogin(username, password.sha256())
            List<Account> accountList = new ArrayList<>();
             //  List<Account> accountList = checkLogin(username,password);
           // reference = FirebaseDatabase.getInstance().getReference("Users");
           // Query query =reference;
              firestore.collection("Users")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Toast.makeText(LoginActivity.this, document.get("username").toString(), Toast.LENGTH_SHORT).show();
                                    //int role =par;
                                      account = new Account(
                                          document.getId(),
                                          document.getString("username"),
                                          Integer.parseInt(document.get("role").toString()),
                                          document.getString("fullname"),
                                          document.getString("birthday"),
                                          Integer.parseInt( document.get("gender").toString()),
                                          document.getString("email")
                                    );
                                    if(document.getString("email").equals(username)){
                                    currentLoginAccountIndex= accountList.size()-1;
                                       // Toast.makeText(LoginActivity.this,document.getString("username"), Toast.LENGTH_SHORT).show();

                                    }
                                    accountList.add(account);
                                }

                            } else {

                            }
                        }
                    });
            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {


                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {


                                Toast.makeText(LoginActivity.this, R.string.toast_login_successful, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("account", accountList.get(currentLoginAccountIndex));
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }
    private List<Account> checkLogin(String username, String password) {
        List<Account> accountList = new ArrayList<>();
        int role = password.equals("0")? 0:1;
        accountList.add(new Account("", username, role));
        return accountList;
    }
}