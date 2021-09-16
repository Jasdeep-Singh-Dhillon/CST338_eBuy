package com.jasdeepsingh.ebuy.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jasdeepsingh.ebuy.entities.Product;
import com.jasdeepsingh.ebuy.entities.ShoppingCart;
import com.jasdeepsingh.ebuy.entities.User;

@Database(entities = {User.class, ShoppingCart.class, Product.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "EBUY_DATABASE";

    public static final String USER_TABLE = "USER_TABLE";
    public static final String SHOPPING_TABLE = "SHOPPING_TABLE";
    public static final String PRODUCT_TABLE = "PRODUCT_TABLE";

    public abstract BuyDAO getBuyDAO();

}
