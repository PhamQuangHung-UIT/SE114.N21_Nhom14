package com.example.splus.my_data;

import com.example.splus.R;
import com.google.firebase.firestore.DocumentId;
import com.google.gson.Gson;

import java.io.Serializable;

public class Account implements Serializable {

    @DocumentId
    private String accountID;  // user.getUID()

    private String avatarUrl;  // user.getPhotoUrl()
    private int role;           // 0: student, 1: teacher
    private String fullname;    // user.getDisplayName();
    private String birthday;
    private int gender;         // 0: male, 1: female, 2: others
    private String email;       // user.getEmail()
    private String phone;       // user.getPhoneNumber();

    public Account() {
        this.accountID = "123";
        //this.avatarID = R.drawable.account_box;
        this.role = 0;
        this.fullname = "Nguyễn Văn B";
        this.birthday = "01/01/2001";
        this.gender = 0;
        this.email = "todo@splus.edu.vn";
        this.phone = "0987654321";
    }

    public Account(String accountID, String fullname, int role) {
        this.accountID = accountID;;
        this.role = role;
        this.fullname = fullname;
        this.birthday = "01/01/2001";
        this.gender = 0;
        this.email = "todo@splus.edu.vn";
        this.phone = "0987654321";
    }

    public Account(String accountID, int role, String fullname, String birthday,
                   int gender, String email) {
        this.accountID = accountID;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
