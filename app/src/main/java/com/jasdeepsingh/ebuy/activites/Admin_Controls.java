package com.jasdeepsingh.ebuy.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jasdeepsingh.ebuy.R;
import com.jasdeepsingh.ebuy.RecyclerViewContents.ProductAdapter;
import com.jasdeepsingh.ebuy.RecyclerViewContents.UserAdapter;
import com.jasdeepsingh.ebuy.Util;
import com.jasdeepsingh.ebuy.db.BuyDAO;
import com.jasdeepsingh.ebuy.entities.Product;
import com.jasdeepsingh.ebuy.entities.User;

import java.util.List;

public class Admin_Controls extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<User> mUsers;
    private UserAdapter mUserAdapter;
    private List<Product> mProducts;
    private ProductAdapter mProductAdapter;

    private BuyDAO mBuyDao;
    private User mUser;

    private ImageView mUsersImageView;
    private ImageView mProductsImageView;
    private FloatingActionButton mProductFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_controls);

        connectView();

        mBuyDao = Util.getDatabase(this);
        mUsers = mBuyDao.getUserList();
        mProducts = mBuyDao.getProductsList();
        getUser();
        checkForUser();
        buildUserRecyclerView();
        imageViewClickListeners();
    }

    private void imageViewClickListeners() {
        mUsersImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildUserRecyclerView();
                mProductFloatingActionButton.setVisibility(View.GONE);
            }
        });

        mProductsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildProductRecyclerView();
                mProductFloatingActionButton.setVisibility(View.VISIBLE);
            }
        });

        mProductFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productAlertDialog();
            }
        });

    }

    private void productAlertDialog() {
        MaterialAlertDialogBuilder alertBuilder = new MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialogRounded);
        View v = getLayoutInflater().inflate(R.layout.product_add, null);
        EditText productName, productDesc, productPrice, productQuantity;
        Button addButton, cancelButton;
        productName = v.findViewById(R.id.product_name_edittext);
        productDesc = v.findViewById(R.id.product_description_edittext);
        productPrice = v.findViewById(R.id.product_price_edittext);
        productQuantity = v.findViewById(R.id.product_quantity_edittext);
        addButton = v.findViewById(R.id.product_change_button);
        addButton.setText(getString(R.string.add));
        cancelButton = v.findViewById(R.id.product_change_exit_button);
        cancelButton.setText(getString(R.string.cancel));

        alertBuilder.setCancelable(true);
        alertBuilder.setView(v);
        AlertDialog alertDialog = alertBuilder.create();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name, desc;
                double price = 0.0;
                int quantity = 0;

                name = productName.getText().toString();
                desc = productDesc.getText().toString();

                try {
                    price = Double.parseDouble(productPrice.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), getString(R.string.enter_valid) + " " + getString(R.string.price_only), Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    quantity = Integer.parseInt(productQuantity.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), getString(R.string.enter_valid) + " " + getString(R.string.quantity_only), Toast.LENGTH_SHORT).show();
                    return;
                }
                Product product = mBuyDao.getProductByName(name);
                if (product != null) {
                    product.setQuantity(product.getQuantity() + quantity);
                    mBuyDao.update(product);
                } else {
                    product = new Product(name, price, quantity, desc);
                    mBuyDao.insert(product);
                }
                mProducts = mBuyDao.getProductsList();
                buildProductRecyclerView();
                alertDialog.dismiss();
                Toast.makeText(getApplicationContext(), product.getName() + " " + getString(R.string.added_success), Toast.LENGTH_SHORT).show();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        alertDialog.show();
    }

    private void buildProductRecyclerView() {
        mRecyclerView = findViewById(R.id.admin_controls_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mProductAdapter = new ProductAdapter(mProducts, ProductAdapter.EDIT_DELETE_VIEW);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mProductAdapter);

        onRecycleProductViewClick();
    }

    private void onRecycleProductViewClick() {
        mProductAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onBuyClick(int position) {

            }

            @Override
            public void onCartClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {
                deleteProduct(position);
            }

            @Override
            public void onEditClick(int position) {
                productEdit(position);
            }
        });
    }

    private void productEdit(int position) {
        Product product = mProducts.get(position);
        if (product == null) {
            Toast.makeText(this, getString(R.string.product_does_not_exist), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mBuyDao.getProductById(product.getProductId()) == null) {
            Toast.makeText(this, getString(R.string.product_does_not_exist), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, EditProductActivity.class);
        intent.putExtra(Util.USER_KEY, mUser.getUserId());
        intent.putExtra(Util.PRODUCT_KEY, product.getProductId());
        startActivity(intent);
        mProductAdapter.notifyItemChanged(position);
    }

    private void deleteProduct(int position) {
        Product product = mProducts.get(position);
        if (product == null) {
            Toast.makeText(this, getString(R.string.product_does_not_exist), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mBuyDao.getProductById(product.getProductId()) == null) {
            Toast.makeText(this, getString(R.string.product_does_not_exist), Toast.LENGTH_SHORT).show();
            return;
        }
        mProducts.remove(position);
        mProductAdapter.notifyItemRemoved(position);
        mBuyDao.delete(product);
        Toast.makeText(this, product.getName() + " " + getString(R.string.user_deleted_success), Toast.LENGTH_SHORT).show();

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

    private void connectView() {
        mUsersImageView = findViewById(R.id.admin_controls_users_bottom_sheet);
        mProductsImageView = findViewById(R.id.admin_controls_products_bottom_sheet);
        mProductFloatingActionButton = findViewById(R.id.admin_controls_products_add_floating_button);
    }

    private void buildUserRecyclerView() {
        mRecyclerView = findViewById(R.id.admin_controls_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mUserAdapter = new UserAdapter(mUsers);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mUserAdapter);

        onRecycleUserViewClick();
    }

    private void onRecycleUserViewClick() {
        mUserAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                deleteUser(position);
            }

            @Override
            public void onStarClick(int position) {
                changeAdminStatus(position);
            }
        });
    }

    private void changeAdminStatus(int position) {
        User user = mUsers.get(position);
        if (user == null) {
            Toast.makeText(this, getString(R.string.user_does_not_exist), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mBuyDao.getUserById(user.getUserId()) == null) {
            Toast.makeText(this, getString(R.string.user_does_not_exist), Toast.LENGTH_SHORT).show();
            return;
        }
        if (user.getUserId() == mUser.getUserId()) {
            Toast.makeText(this, getString(R.string.self_user_admin_status), Toast.LENGTH_SHORT).show();
            return;
        }
        user.setIsAdmin(!user.getIsAdmin());
        mUserAdapter.notifyItemChanged(position);
        mBuyDao.update(user);
        Toast.makeText(this, user.getUsername() + " " + getString(R.string.admin_status_changed), Toast.LENGTH_SHORT).show();
    }

    private void deleteUser(int position) {
        User user = mUsers.get(position);
        if (user == null) {
            Toast.makeText(this, getString(R.string.user_does_not_exist), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mBuyDao.getUserById(user.getUserId()) == null) {
            Toast.makeText(this, getString(R.string.user_does_not_exist), Toast.LENGTH_SHORT).show();
            return;
        }
        if (user.getUserId() == mUser.getUserId()) {
            Toast.makeText(this, getString(R.string.self_user_delete), Toast.LENGTH_SHORT).show();
            return;
        }
        mUsers.remove(position);
        mUserAdapter.notifyItemRemoved(position);
        mBuyDao.delete(user);
        Toast.makeText(this, user.getUsername() + " " + getString(R.string.user_deleted_success), Toast.LENGTH_SHORT).show();
    }


}