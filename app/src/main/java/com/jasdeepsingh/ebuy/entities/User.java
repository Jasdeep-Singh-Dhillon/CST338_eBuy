package com.jasdeepsingh.ebuy.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.jasdeepsingh.ebuy.db.AppDatabase;

@Entity(tableName = AppDatabase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int mUserId;

    private String mUsername;
    private String mPassword;
    private String mFirstName;
    private String mLastName;
    private Boolean mIsAdmin;

    public User(String username, String password, String firstName, String lastName) {
        mUsername = username;
        mPassword = password;
        mFirstName = firstName;
        mLastName = lastName;
        mIsAdmin = false;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public Boolean getIsAdmin() {
        return mIsAdmin;
    }

    public void setIsAdmin(Boolean admin) {
        mIsAdmin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUserId=" + mUserId +
                ", mUsername='" + mUsername + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mFirstName='" + mFirstName + '\'' +
                ", mLastName='" + mLastName + '\'' +
                ", mIsAdmin=" + mIsAdmin +
                '}';
    }
}
