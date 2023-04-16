package com.example.splus.my_data;

import java.io.Serializable;

public class AccountData implements Serializable {
    private int avatar;
    private String accountID;
    private String username;
    private String fullname;

    public AccountData(int avatar, String accountID, String username, String fullname) {
        this.avatar = avatar;
        this.accountID = accountID;
        this.username = username;
        this.fullname = fullname;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
