package com.jasdeepsingh.ebuy.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.jasdeepsingh.ebuy.R;
import com.jasdeepsingh.ebuy.Util;
import com.jasdeepsingh.ebuy.db.BuyDAO;
import com.jasdeepsingh.ebuy.entities.Product;
import com.jasdeepsingh.ebuy.entities.User;

public class MainActivity extends AppCompatActivity {

    private Button mSignUp;
    private Button mSignIn;

    private BuyDAO mBuyDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectButtons();

        mBuyDao = Util.getDatabase(this);
        checkForPredefinedUsers();
        checkForPredefinedProducts();

        mSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Sign_Up_Page.class);
            startActivity(intent);
        });

        mSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Sign_In_Page.class);
            startActivity(intent);
        });
    }

    private void checkForPredefinedProducts() {
        if(mBuyDao.getProductsList().size() > 0) {
            return;
        }
        addPredefinedProducts();
    }

    private void addPredefinedProducts() {
        Product product;

        product = new Product("RTX 3090", 3000.00, 10, "GPU for crypto miners and scalpers");
        product.setImage(R.mipmap.rtx_3090_foreground);
        mBuyDao.insert(product);

        product = new Product("NFT", 10000.00, 50, "Useless Investment");
        product.setImage(R.mipmap.nft_foreground);
        mBuyDao.insert(product);

        product = new Product("DogeCoin", 0.55, 1000, "To the Moon");
        product.setImage(R.mipmap.doge_coin_foreground);
        mBuyDao.insert(product);

        product = new Product("DaBaby Car", 100000.00, 1, "Lesss go");
        product.setImage(R.mipmap.dababy_car_foreground);
        mBuyDao.insert(product);

        product = new Product("Thanos Car", 100000.00, 1, "What did it cost");
        product.setImage(R.mipmap.thanos_car_foreground);
        mBuyDao.insert(product);

        product = new Product("$19 Fortnite Card", 19.99, 10, "Who wants it?");
        product.setImage(R.mipmap.fortnite_card_foreground);
        mBuyDao.insert(product);

        product = new Product("Bottle of Water", 4.99, 100, "Bo'oh'w'o'wo'er");
        product.setImage(R.mipmap.water_bottle_foreground);
        mBuyDao.insert(product);

        product = new Product("Soap", 4.99, 1000, "Cleanse, moisturize and soothe your skin with\nall natural handmade soap");
        product.setImage(R.mipmap.soap_foreground);
        mBuyDao.insert(product);

        product = new Product("Shampoo", 7.99, 1000, "A shampoo that serves to condition and\nbeautify hair");
        product.setImage(R.mipmap.shampoo_foreground);
        mBuyDao.insert(product);

        product = new Product("Socks", 3.99, 500, "Socks for your comfort");
        product.setImage(R.mipmap.socks_foreground);
        mBuyDao.insert(product);

        product = new Product("Cereal", 4.99, 5000, "Free from all kinds of preservatives, artificial\nflavors, and colors");
        product.setImage(R.mipmap.cereal_foreground);
        mBuyDao.insert(product);
    }

    private void checkForPredefinedUsers() {
        User user = mBuyDao.getUserByUsername("testuser1");
        if(mBuyDao.getUserByUsername("testuser1") == null && mBuyDao.getUserByUsername("admin2") == null) {
            addPredefinedUsers();
        }
    }

    private void addPredefinedUsers() {
        User user = new User("testuser1", "testuser1", "test", "user");
        mBuyDao.insert(user);
        user = new User("admin2", "admin2", "admin", "user");
        user.setIsAdmin(true);
        mBuyDao.insert(user);
    }

    private void connectButtons() {
        mSignUp = findViewById(R.id.button_sign_up);
        mSignIn = findViewById(R.id.button_sign_in);
    }
}