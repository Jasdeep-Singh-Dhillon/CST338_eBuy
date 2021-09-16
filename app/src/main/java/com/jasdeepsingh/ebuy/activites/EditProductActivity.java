package com.jasdeepsingh.ebuy.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jasdeepsingh.ebuy.R;
import com.jasdeepsingh.ebuy.Util;
import com.jasdeepsingh.ebuy.db.BuyDAO;
import com.jasdeepsingh.ebuy.entities.Product;
import com.jasdeepsingh.ebuy.entities.User;

public class EditProductActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mNameTextView;
    private TextView mDescTextView;
    private TextView mPriceTextView;
    private TextView mQuantityTextView;

    private EditText mNameEditText;
    private EditText mDescEditText;
    private EditText mPriceEditText;
    private EditText mQuantityEditText;
    private Button mChangeButton;
    private Button mExitButton;

    private BuyDAO mBuyDao;
    private Product mProduct;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        connectView();

        mBuyDao = Util.getDatabase(this);

        getUser();
        checkForUser();

        getProduct();
        checkForProduct();

        setTextViews();
        setClickListeners();
    }

    private void setClickListeners() {
        mChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = getProductWithUpdatedValues();
                if(product!=null) {
                    mBuyDao.update(product);
                    mProduct = product;
                    setTextViews();
                }
            }
        });

        mExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Admin_Controls.class);
                intent.putExtra(Util.USER_KEY, mUser.getUserId());
                startActivity(intent);
            }
        });
    }

    private Product getProductWithUpdatedValues() {
        String name, desc;
        double price=0.0;
        int quantity=0;

        name = mNameEditText.getText().toString();
        desc = mDescEditText.getText().toString();

        try {
            price = Double.parseDouble(mPriceEditText.getText().toString());
        } catch(Exception e) {
            Toast.makeText(this, getString(R.string.enter_valid) + " " + getString(R.string.price_only), Toast.LENGTH_SHORT).show();
            return null;
        }

        try {
            quantity = Integer.parseInt(mQuantityEditText.getText().toString());
        } catch(Exception e) {
            Toast.makeText(this, getString(R.string.enter_valid) + " " + getString(R.string.quantity_only), Toast.LENGTH_SHORT).show();
            return null;
        }

        Product product = new Product(name, price, quantity, desc);
        product.setProductId(mProduct.getProductId());
        product.setImage(mProduct.getImage());
        return product;
    }

    private void setTextViews() {
        mImageView.setImageResource(mProduct.getImage());
        mNameTextView.setText(mProduct.getName());
        mDescTextView.setText(mProduct.getDescription());
        mPriceTextView.setText(mProduct.getPrice().toString());
        mQuantityTextView.setText(mProduct.getQuantity().toString());

        mNameEditText.setText(mProduct.getName());
        mDescEditText.setText(mProduct.getDescription());
        mPriceEditText.setText(mProduct.getPrice().toString());
        mQuantityEditText.setText(mProduct.getQuantity().toString());
    }

    private void connectView() {
        mImageView = findViewById(R.id.product_image);
        mNameTextView = findViewById(R.id.product_name);
        mDescTextView = findViewById(R.id.product_description);
        mPriceTextView = findViewById(R.id.product_price);
        mQuantityTextView = findViewById(R.id.product_quantity);

        mNameEditText = findViewById(R.id.product_name_edittext);
        mDescEditText = findViewById(R.id.product_description_edittext);
        mPriceEditText = findViewById(R.id.product_price_edittext);
        mQuantityEditText = findViewById(R.id.product_quantity_edittext);
        mChangeButton = findViewById(R.id.product_change_button);
        mExitButton = findViewById(R.id.product_change_exit_button);
    }

    private void checkForUser() {
        if (mUser == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void getUser() {
        int userId = getIntent().getIntExtra(Util.USER_KEY, -1);
        if (userId == -1) {
            Intent intent = new Intent(this, Sign_In_Page.class);
            startActivity(intent);
        }
        mUser = mBuyDao.getUserById(userId);
    }

    private void getProduct() {
        int productId = getIntent().getIntExtra(Util.PRODUCT_KEY, -1);
        if(productId == -1) {
            Intent intent = new Intent(this, Admin_Controls.class);
            intent.putExtra(Util.USER_KEY, mUser.getUserId());
            startActivity(intent);
        }
        mProduct = mBuyDao.getProductById(productId);
    }

    private void checkForProduct() {
        if(mProduct == null) {
            Intent intent = new Intent(this, Admin_Controls.class);
            startActivity(intent);
        }
    }


}