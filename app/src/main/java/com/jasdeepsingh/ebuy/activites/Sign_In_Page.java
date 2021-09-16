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

public class Sign_In_Page extends AppCompatActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;

    private BuyDAO mBuyDao;

    private String mUsername;
    private String mPassword;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        connectView();

        mBuyDao = Util.getDatabase(this);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformation();
                if (attemptLogin()) {
                    Intent intent = Landing_Page.getLandingPageIntent(getApplicationContext(), mUser.getUserId());
                    startActivity(intent);
                }
            }
        });
    }

    private boolean attemptLogin() {
        if (mUsername.length() <= 0) {
            Toast.makeText(this, getString(R.string.enter_valid) + " " + getString(R.string.username), Toast.LENGTH_SHORT).show();
            return false;
        }
        mUser = mBuyDao.getUserByUsername(mUsername);
        if (mUser == null) {
            Toast.makeText(this, getString(R.string.username_exist), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!mUser.getPassword().equals(mPassword)) {
            Toast.makeText(this, getString(R.string.password_incorrect), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void getInformation() {
        mUsername = mUsernameEditText.getText().toString();
        mPassword = mPasswordEditText.getText().toString();
    }

    private void connectView() {
        mUsernameEditText = findViewById(R.id.sign_in_username);
        mPasswordEditText = findViewById(R.id.sign_in_password);
        mLoginButton = findViewById(R.id.sign_in_login);
    }
}