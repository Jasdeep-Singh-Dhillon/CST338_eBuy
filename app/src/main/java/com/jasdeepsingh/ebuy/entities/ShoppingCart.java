package com.jasdeepsingh.ebuy.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.jasdeepsingh.ebuy.db.AppDatabase;

@Entity(tableName = AppDatabase.SHOPPING_TABLE)
public class ShoppingCart {

    @PrimaryKey(autoGenerate = true)
    private int mOrderId;

    private int mUserId;
    private int mProductId;
    private boolean mCheckedOut;

    public ShoppingCart(int userId, int productId) {
        mUserId = userId;
        mProductId = productId;
        mCheckedOut = true;
    }

    public int getOrderId() {
        return mOrderId;
    }

    public void setOrderId(int mOrderId) {
        this.mOrderId = mOrderId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int mProductId) {
        this.mProductId = mProductId;
    }

    public boolean isCheckedOut() {
        return mCheckedOut;
    }

    public void setCheckedOut(boolean mCheckedOut) {
        this.mCheckedOut = mCheckedOut;
    }
}
