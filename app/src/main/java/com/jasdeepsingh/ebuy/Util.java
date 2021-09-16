package com.jasdeepsingh.ebuy;

import android.content.Context;

import androidx.room.Room;

import com.jasdeepsingh.ebuy.db.AppDatabase;
import com.jasdeepsingh.ebuy.db.BuyDAO;

public class Util {

    public static final String USER_KEY = "com.jasdeepsingh.ebuy.USER_KEY";
    public static final String PRODUCT_KEY = "com.jasdeepsingh.ebuy.PRODUCT_KEY";

    public static BuyDAO getDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getBuyDAO();
    }
}
