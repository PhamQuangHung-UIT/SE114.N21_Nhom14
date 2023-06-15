package com.example.splus.my_data;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.splus.LoginActivity;
import com.example.splus.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
public class Account implements Serializable {
    private String accountID;
    private int avatarID;

    private String username;
    private int role;           // 0: student, 1: teacher
    private String fullname;

    private String birthday;
    private int gender;         // 0: male, 1: female, 2: others
    private String email;
    private String phone;


    public Account() {
        this.accountID = "123";
        this.avatarID = R.drawable.account_box;
        this.username = "username";
        this.role = 0;
        this.fullname = "Nguyễn Văn B";
        this.birthday = "01/01/2001";
        this.gender = 0;
        this.email = "todo@splus.edu.vn";
        this.phone = "0987654321";
    }
    public Account(String accountID, String username, int role) {
        this.accountID = accountID;
        this.avatarID = R.drawable.account_box;
        this.username = username;
        this.role = role;
        this.fullname = "Nguyễn Văn C";
        this.birthday = "01/01/2001";
        this.gender = 0;
        this.email = "todo@splus.edu.vn";
        this.phone = "0987654321";
    }
    public Account(String accountID,String username,int role,String fullname, String birthday,
                   int gender, String email) {
        this.accountID = accountID;
        this.avatarID = R.drawable.account_box;
        this.username = username;
        this.role = role;
        this.fullname = fullname;
        this.birthday = birthday;
        this.gender = gender;
        this.email = email;
        this.phone = "";
    }
    public String getAccountID() {
        return accountID;
    }
    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }
    public int getAvatarID() {
        return avatarID;
    }
    public void setAvatarID(int avatarID) {
        this.avatarID = avatarID;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getRole() {
        return role;
    }
    public void setRole(int role) {
        this.role = role;
    }
    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public int getGender() {
        return gender;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {this.phone = phone; }

    public String toJson(){
        Gson gson =new Gson();
        return gson.toJson(this);
    }

    public static List<Account> getListAccount(){
        List<Account> accountList = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               Account account = new Account(

                                        document.getId(),
                                        document.getString("username"),
                                        Integer.parseInt(document.get("role").toString()),
                                        document.getString("fullname"),
                                        document.getString("birthday"),
                                        Integer.parseInt( document.get("gender").toString()),
                                        document.getString("email")
                                );
                                accountList.add(account);
                            }
                        }
                    }
                });
        return accountList;
    }
}
