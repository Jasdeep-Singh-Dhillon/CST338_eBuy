package com.jasdeepsingh.ebuy.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jasdeepsingh.ebuy.R;
import com.jasdeepsingh.ebuy.Util;
import com.jasdeepsingh.ebuy.db.BuyDAO;
import com.jasdeepsingh.ebuy.entities.User;

public class Sign_Up_Page extends AppCompatActivity {

    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mCreateButton;

    private String mFirstName;
    private String mLastName;
    private String mUsername;
    private String mPassword;

    private BuyDAO mBuyDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        connectView();

        mBuyDao = Util.getDatabase(this);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getAccountInformation()) {
                    User user = mBuyDao.getUserByUsername(mUsername);
                    Intent intent = Landing_Page.getLandingPageIntent(getApplicationContext(), user.getUserId());
                    startActivity(intent);
                }
            }
        });


    }

    private boolean getAccountInformation() {
        mFirstName = mFirstNameEditText.getText().toString();
        if (mFirstName.length() <= 0) {
            Toast.makeText(this, getString(R.string.enter_valid) + " " + getString(R.string.first_name), Toast.LENGTH_SHORT).show();
            return false;
        }

        mLastName = mLastNameEditText.getText().toString();
        if (mLastName.length() <= 0) {
            Toast.makeText(this, getString(R.string.enter_valid) + " " + getString(R.string.last_name), Toast.LENGTH_SHORT).show();
            return false;
        }

        mUsername = mUsernameEditText.getText().toString();
        if (mUsername.length() <= 0) {
            Toast.makeText(this, getString(R.string.enter_valid) + " " + getString(R.string.username), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mBuyDao.getUserByUsername(mUsername) != null) {
            Toast.makeText(this, getString(R.string.username_taken), Toast.LENGTH_LONG).show();
            mUsername = null;
            mUsernameEditText.getText().clear();
            return false;
        }

        mPassword = mPasswordEditText.getText().toString();
        if (mPassword.length() <= 0) {
            Toast.makeText(this, getString(R.string.enter_valid) + " " + getString(R.string.password), Toast.LENGTH_SHORT).show();
            return false;
        }
        createAccount();
        return true;
    }

    private void createAccount() {
        User user = new User(mUsername, mPassword, mFirstName, mLastName);
        mBuyDao.insert(user);
    }

    private void connectView() {
        mFirstNameEditText = findViewById(R.id.signup_first_name);
        mLastNameEditText = findViewById(R.id.signup_last_name);
        mUsernameEditText = findViewById(R.id.signup_username);
        mPasswordEditText = findViewById(R.id.signup_password);
        mCreateButton = findViewById(R.id.signup_create);
    }
}