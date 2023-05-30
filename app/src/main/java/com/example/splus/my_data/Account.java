package com.example.splus.my_data;

import com.example.splus.R;

import java.io.Serializable;

public class Account implements Serializable {
    private int accountID;
    private int avatarID;
    private String username;
    private int role;           // 0: student, 1: teacher
    private String fullname;
    private String birthday;
    private int gender;         // 0: male, 1: female, 2: others
    private String email;
    private String phone;

    public Account() {
        this.accountID = 0;
        this.avatarID = R.drawable.account_box;
        this.username = "username";
        this.role = 0;
        this.fullname = "Nguyễn Văn A";
        this.birthday = "01/01/2001";
        this.gender = 0;
        this.email = "todo@splus.edu.vn";
        this.phone = "0987654321";
    }
    public Account(int accountID, String username, int role) {
        this.accountID = accountID;
        this.avatarID = R.drawable.account_box;
        this.username = username;
        this.role = role;
        this.fullname = "Nguyễn Văn A";
        this.birthday = "01/01/2001";
        this.gender = 0;
        this.email = "todo@splus.edu.vn";
        this.phone = "0987654321";
    }
    public int getAccountID() {
        return accountID;
    }
    public void setAccountID(int accountID) {
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
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
