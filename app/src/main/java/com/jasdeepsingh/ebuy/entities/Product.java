package com.jasdeepsingh.ebuy.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.jasdeepsingh.ebuy.R;
import com.jasdeepsingh.ebuy.db.AppDatabase;

@Entity(tableName = AppDatabase.PRODUCT_TABLE)
public class Product {

    @PrimaryKey(autoGenerate = true)
    private int mProductId;

    private String mName;
    private Double mPrice;
    private Integer mQuantity;
    private Integer mImage;
    private String mDescription;

    public Product(String name, Double price, Integer quantity, String description) {
        mName = name;
        mPrice = price;
        mQuantity = quantity;
        mDescription = description;
        mImage = R.drawable.insert_photo;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int mProductId) {
        this.mProductId = mProductId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public Double getPrice() {
        return mPrice;
    }

    public void setPrice(Double mPrice) {
        this.mPrice = mPrice;
    }

    public Integer getQuantity() {
        return mQuantity;
    }

    public void setQuantity(Integer mQuantity) {
        this.mQuantity = mQuantity;
    }

    public Integer getImage() {
        return mImage;
    }

    public void setImage(Integer mImage) {
        this.mImage = mImage;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    @Override
    public String toString() {
        return "Product{" +
                "mProductId=" + mProductId +
                ", mName='" + mName + '\'' +
                ", mPrice=" + mPrice +
                ", mQuantity=" + mQuantity +
                ", mImage=" + mImage +
                ", mDescription='" + mDescription + '\'' +
                '}';
    }
}
