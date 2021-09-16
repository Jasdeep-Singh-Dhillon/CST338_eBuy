package com.jasdeepsingh.ebuy.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jasdeepsingh.ebuy.entities.Product;
import com.jasdeepsingh.ebuy.entities.ShoppingCart;
import com.jasdeepsingh.ebuy.entities.User;

import java.util.List;

@Dao
public interface BuyDAO {

    //User Queries
    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getUserList();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUsername = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserId = :userId")
    User getUserById(int userId);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUsername = :username and mPassword = :password")
    User getUser(String username, String password);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mIsAdmin = :bool")
    List<User> getUserByAdminStatus(boolean bool);

    //Product Queries
    @Insert
    void insert(Product... products);

    @Update
    void update(Product... products);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM " + AppDatabase.PRODUCT_TABLE + " ORDER BY mName")
    List<Product> getProductsList();

    @Query("SELECT * FROM " + AppDatabase.PRODUCT_TABLE + " WHERE mProductId = :productId")
    Product getProductById(int productId);

    @Query("SELECT * FROM " + AppDatabase.PRODUCT_TABLE + " WHERE mPrice < :price ORDER BY mName")
    List<Product> getProductsUnderPrice(Double price);

    @Query("SELECT * FROM " + AppDatabase.PRODUCT_TABLE + " WHERE mQuantity > :quantity ORDER BY mName")
    List<Product> getProductsByQuantity(Integer quantity);

    @Query("SELECT * FROM " + AppDatabase.PRODUCT_TABLE + " WHERE mName = :name")
    Product getProductByName(String name);

    //ShoppingCart Queries
    @Insert
    void insert(ShoppingCart... orders);

    @Update
    void update(ShoppingCart... orders);

    @Delete
    void delete(ShoppingCart order);

    @Query("SELECT * FROM " + AppDatabase.SHOPPING_TABLE)
    List<ShoppingCart> getOrdersList();

    @Query("SELECT * FROM " + AppDatabase.SHOPPING_TABLE + " WHERE mUserId = :userId")
    List<ShoppingCart> getOrdersByUserId(int userId);

    @Query("SELECT * FROM " + AppDatabase.SHOPPING_TABLE + " WHERE mProductId = :productId")
    List<ShoppingCart> getOrdersByProductId(int productId);

    @Query("SELECT * FROM " + AppDatabase.SHOPPING_TABLE + " WHERE mCheckedOut = :bool")
    List<ShoppingCart> getOrdersByCheckedOut(boolean bool);

    @Query("SELECT * FROM " + AppDatabase.SHOPPING_TABLE + " WHERE mUserId = :userId and mProductId = :productId")
    ShoppingCart getShoppingCartItem(int userId, int productId);

}
