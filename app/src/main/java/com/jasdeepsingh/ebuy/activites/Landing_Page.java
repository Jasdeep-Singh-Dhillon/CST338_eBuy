package com.jasdeepsingh.ebuy.activites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jasdeepsingh.ebuy.R;
import com.jasdeepsingh.ebuy.RecyclerViewContents.ProductAdapter;
import com.jasdeepsingh.ebuy.Util;
import com.jasdeepsingh.ebuy.db.BuyDAO;
import com.jasdeepsingh.ebuy.entities.Product;
import com.jasdeepsingh.ebuy.entities.ShoppingCart;
import com.jasdeepsingh.ebuy.entities.User;

import java.util.ArrayList;
import java.util.List;

public class Landing_Page extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ProductAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private User mUser;
    private List<Product> mProducts;
    private BuyDAO mBuyDao;

    private TextView mNameTextView;
    private Button mOrderHistoryButton;
    private Button mShoppingCartButton;
    private Button mAdminButton;
    private Button mProductsListButton;
    private Button mLogOutButton;
    private Button mCheckoutButton;
    private CardView mSearchCardView;
    private EditText mSearchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        connectView();

        mBuyDao = Util.getDatabase(this);
        mProducts = mBuyDao.getProductsList();
        getUser();
        checkForUser();
        adminButtonVisibility();
        buildRecyclerView(ProductAdapter.PRODUCT_VIEW);
        mNameTextView.setText(mUser.getFirstName());
        buttonOnClickListeners();
    }

    private void buttonOnClickListeners() {

        mProductsListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProducts = mBuyDao.getProductsList();
                buildRecyclerView(ProductAdapter.PRODUCT_VIEW);
                mAdapter.notifyDataSetChanged();
                mCheckoutButton.setVisibility(View.GONE);
                mProductsListButton.setVisibility(View.GONE);
                mOrderHistoryButton.setVisibility(View.VISIBLE);
                mShoppingCartButton.setVisibility(View.VISIBLE);
                mSearchCardView.setVisibility(View.VISIBLE);
            }
        });

        mOrderHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ShoppingCart> orders = mBuyDao.getOrdersByCheckedOut(true);

                mProducts = new ArrayList<>();
                for (ShoppingCart order : orders) {
                    if(order.getUserId() == mUser.getUserId()) {
                        mProducts.add(mBuyDao.getProductById(order.getProductId()));
                    }
                }
                buildRecyclerView(ProductAdapter.ORDER_HISTORY_VIEW);
                mAdapter.notifyDataSetChanged();
                if (mProducts.size() <= 0) {
                    Toast.makeText(getApplicationContext(), getString(R.string.no_orders_found), Toast.LENGTH_SHORT).show();
                }
                mCheckoutButton.setVisibility(View.GONE);
                mProductsListButton.setVisibility(View.VISIBLE);
                mOrderHistoryButton.setVisibility(View.GONE);
                mShoppingCartButton.setVisibility(View.VISIBLE);
                mSearchCardView.setVisibility(View.GONE);
            }
        });

        mShoppingCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ShoppingCart> orders = mBuyDao.getOrdersByCheckedOut(false);
                mProducts = new ArrayList<>();
                for (ShoppingCart order : orders) {
                    mProducts.add(mBuyDao.getProductById(order.getProductId()));
                }
                buildRecyclerView(ProductAdapter.SHOPPING_CART_VIEW);
                mAdapter.notifyDataSetChanged();
                if (orders.size() <= 0) {
                    Toast.makeText(getApplicationContext(), getString(R.string.no_items_in_sc), Toast.LENGTH_SHORT).show();
                }
                mCheckoutButton.setVisibility(View.VISIBLE);

                mProductsListButton.setVisibility(View.VISIBLE);
                mOrderHistoryButton.setVisibility(View.VISIBLE);
                mShoppingCartButton.setVisibility(View.GONE);
                mSearchCardView.setVisibility(View.GONE);
            }
        });

        mAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Admin_Controls.class);
                intent.putExtra(Util.USER_KEY, mUser.getUserId());
                startActivity(intent);
            }
        });

        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutAlertDialogBuilder();
            }
        });

        mCheckoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ShoppingCart> orders = mBuyDao.getOrdersByCheckedOut(false);
                mProducts = new ArrayList<>();
                for(ShoppingCart order: orders) {
                    order.setCheckedOut(true);
                    mBuyDao.update(order);
                }

                buildRecyclerView(ProductAdapter.SHOPPING_CART_VIEW);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), getString(R.string.products_checked_out), Toast.LENGTH_SHORT).show();
            }
        });

        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void filter(String text) {
        List<Product> products = new ArrayList<>();
        for(Product product: mProducts) {
            if(product.getName().toLowerCase().contains(text.toLowerCase())) {
                products.add(product);
            }
        }

        mAdapter.filterList(products);
    }

    private void logOutAlertDialogBuilder() {
        MaterialAlertDialogBuilder alertBuilder = new MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialogRounded);

        alertBuilder.setCancelable(true);

        alertBuilder.setTitle(getString(R.string.log_out));
        alertBuilder.setMessage(getString(R.string.confirm_logout));
        alertBuilder.setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mUser = null;
                getIntent().putExtra(Util.USER_KEY, -1);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        alertBuilder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertBuilder.create().show();
    }

    private void adminButtonVisibility() {
        if (mUser.getIsAdmin()) {
            mAdminButton.setVisibility(View.VISIBLE);
        }
    }

    private void connectView() {
        mNameTextView = findViewById(R.id.bottom_sheet_user_name);
        mOrderHistoryButton = findViewById(R.id.bottom_sheet_order_history_button);
        mShoppingCartButton = findViewById(R.id.bottom_sheet_shopping_cart_button);
        mAdminButton = findViewById(R.id.bottom_sheet_admin_button);
        mProductsListButton = findViewById(R.id.bottom_sheet_product_list_button);
        mLogOutButton = findViewById(R.id.bottom_sheet_logout_button);
        mCheckoutButton = findViewById(R.id.bottom_sheet_checkout_button);
        mSearchCardView = findViewById(R.id.bottom_sheet_landing_page_search_card_view);
        mSearchEditText = findViewById(R.id.bottom_sheet_landing_page_search);

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

    private void buildRecyclerView(int viewType) {
        mRecyclerView = findViewById(R.id.landing_page_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ProductAdapter(mProducts, viewType);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        onRecyclerViewClick();
    }

    private void onRecyclerViewClick() {
        mAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onBuyClick(int position) {
                buyItem(position);
            }

            @Override
            public void onCartClick(int position) {
                addItemToCart(position);
            }

            @Override
            public void onDeleteClick(int position) {
                deleteItemFromCart(position);
            }

            @Override
            public void onEditClick(int position) {

            }
        });
    }

    private void deleteItemFromCart(int position) {
        Product product = mProducts.get(position);
        if (mBuyDao.getProductById(product.getProductId()) != null) {
            product.setQuantity(product.getQuantity() + 1);
            mBuyDao.update(product);
            ShoppingCart sc = mBuyDao.getShoppingCartItem(mUser.getUserId(), product.getProductId());
            mProducts.remove(position);
            mAdapter.notifyItemRemoved(position);
            mBuyDao.delete(sc);
            Toast.makeText(getApplicationContext(), product.getName() + " " + getString(R.string.removed_from_sc), Toast.LENGTH_SHORT).show();
        }

    }

    private void addItemToCart(int position) {
        Product product = mProducts.get(position);
        if (checkProduct(product)) {
            product.setQuantity(product.getQuantity() - 1);
            mBuyDao.update(product);
            ShoppingCart sc = new ShoppingCart(mUser.getUserId(), product.getProductId());
            sc.setCheckedOut(false);
            mBuyDao.insert(sc);
            mAdapter.notifyItemChanged(position);
            Toast.makeText(getApplicationContext(), product.getName() + " " + getString(R.string.added_to_shopping_cart), Toast.LENGTH_SHORT).show();
        }
    }

    public void buyItem(int position) {
        Product product = mProducts.get(position);
        if (checkProduct(product)) {
            product.setQuantity(product.getQuantity() - 1);
            mBuyDao.update(product);
            ShoppingCart sc = new ShoppingCart(mUser.getUserId(), product.getProductId());
            mBuyDao.insert(sc);
            mAdapter.notifyItemChanged(position);
            Toast.makeText(getApplicationContext(), product.getName() + " " + getString(R.string.bought), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkProduct(Product product) {

        if (product == null) {
            return false;
        }

        int productId = product.getProductId();

        if (mBuyDao.getProductById(productId) == null) {
            return false;
        }

        int quantity = product.getQuantity();

        if (quantity <= 0) {
            Toast.makeText(getApplicationContext(), "Product out of stock", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public static Intent getLandingPageIntent(Context context, int userId) {
        Intent intent = new Intent(context, Landing_Page.class);
        intent.putExtra(Util.USER_KEY, userId);
        return intent;
    }
}