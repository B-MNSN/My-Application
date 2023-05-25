package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "usersdb";
    private static final String TABLE_PRODUCT = "productDetails";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "productName";
    private static final String KEY_DES = "productDes";
    private static final String KEY_CATEGORY = "productCategory";
    private static final String KEY_PRICE = "productPrice";

    public DbHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_DES + " TEXT,"
                + KEY_CATEGORY + " TEXT,"
                + KEY_PRICE + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        // Create tables again
        onCreate(db);
    }

    public void insertProductDetails(String name, String description, String category, String price){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_DES, description);
        cValues.put(KEY_CATEGORY, category);
        cValues.put(KEY_PRICE, price);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_PRODUCT,null, cValues);
        db.close();
    }

    // Get Product Details
    @SuppressLint("Range")
    public ArrayList<ProductModel> GetProducts(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<ProductModel> productList = new ArrayList<>();
        String query = "SELECT " + KEY_ID + ", " + KEY_NAME + ", " + KEY_DES + ", " + KEY_CATEGORY + ", " + KEY_PRICE + " FROM "+ TABLE_PRODUCT;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            ProductModel product = new ProductModel();

            product.setTitle(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            product.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DES)));
            product.setCategory(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));
            product.setPrice(cursor.getDouble(cursor.getColumnIndex(KEY_PRICE)));
            product.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxGW7Xenkb8mkB7Jq-CR3pXFevxcOW1FpiwA&usqp=CAU");
            productList.add(product);
        }
        return  productList;
    }

    // Get User Details based on userid
    @SuppressLint("Range")
    public ArrayList<ProductModel> GetProductById(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<ProductModel> productList = new ArrayList<>();
        String query = "SELECT " + KEY_ID + ", " + KEY_NAME + ", " + KEY_DES + ", " + KEY_CATEGORY + ", " + KEY_PRICE + " FROM " + TABLE_PRODUCT;
        Cursor cursor = db.query(TABLE_PRODUCT, new String[]{KEY_NAME, KEY_DES, KEY_CATEGORY,KEY_PRICE}, KEY_ID+ "=?",new String[]{String.valueOf(userid)},null, null, null, null);
        if (cursor.moveToNext()){
            ProductModel product = new ProductModel();
            product.setTitle(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            product.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DES)));
            product.setCategory(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));
            product.setPrice(cursor.getDouble(cursor.getColumnIndex(KEY_PRICE)));
            product.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxGW7Xenkb8mkB7Jq-CR3pXFevxcOW1FpiwA&usqp=CAU");
            productList.add(product);
        }
        return  productList;
    }

    @SuppressLint("Range")
    public ArrayList<String> GetCategory(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> productList = new ArrayList<>();
        String query = "SELECT " + KEY_CATEGORY + " FROM "+ TABLE_PRODUCT + " GROUP BY " + KEY_CATEGORY;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            productList.add(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));
        }
        return  productList;
    }

    // Delete Product Details
    public void DeleteProduct(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, KEY_ID+" = ?",new String[]{String.valueOf(userid)});
        db.close();
    }

    // Update Product Details
    public int UpdateProductDetails(String description, String category, int id, Double price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_DES, description);
        cVals.put(KEY_CATEGORY, category);
        cVals.put(KEY_PRICE, price);
        int count = db.update(TABLE_PRODUCT, cVals, KEY_ID+" = ?",new String[]{String.valueOf(id)});
        return  count;
    }
}
