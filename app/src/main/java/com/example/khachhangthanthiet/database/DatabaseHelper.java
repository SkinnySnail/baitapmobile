package com.example.khachhangthanthiet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.khachhangthanthiet.model.Admin;
import com.example.khachhangthanthiet.model.Customer;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app_database.db";
    private static final int DATABASE_VERSION = 1;

    // Bảng Customer
    private static final String TABLE_CUSTOMERS = "customers";
    private static final String COLUMN_PHONE = "phoneNumber";
    private static final String COLUMN_POINTS = "points";
    private static final String COLUMN_CREATED_DATE = "createdDate";
    private static final String COLUMN_LAST_UPDATED = "lastUpdatedDate";

    // Bảng Admin
    private static final String TABLE_ADMINS = "admins";
    private static final String COLUMN_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCustomerTable = "CREATE TABLE " + TABLE_CUSTOMERS + " (" +
                COLUMN_PHONE + " TEXT PRIMARY KEY, " +
                COLUMN_POINTS + " INTEGER, " +
                COLUMN_CREATED_DATE + " TEXT, " +
                COLUMN_LAST_UPDATED + " TEXT)";
        db.execSQL(createCustomerTable);

        String createAdminTable = "CREATE TABLE " + TABLE_ADMINS + " (" +
                COLUMN_PHONE + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_PHONE + ") REFERENCES " + TABLE_CUSTOMERS + "(" + COLUMN_PHONE + "))";
        db.execSQL(createAdminTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMINS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
        onCreate(db);
    }

    // ➕ Thêm Customer
    public void insertCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHONE, customer.getPhoneNumber());
        values.put(COLUMN_POINTS, customer.getPoints());
        values.put(COLUMN_CREATED_DATE, customer.getCreatedDate());
        values.put(COLUMN_LAST_UPDATED, customer.getLastUpdatedDate());
        db.insert(TABLE_CUSTOMERS, null, values);
        db.close();
    }

    // ➕ Thêm Admin
    public void insertAdmin(Admin admin) {
        insertCustomer(admin); // Gọi lại thêm Customer trước
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHONE, admin.getPhoneNumber());
        values.put(COLUMN_PASSWORD, admin.getPassword());
        db.insert(TABLE_ADMINS, null, values);
        db.close();
    }

    // 🔍 Kiểm tra đăng nhập Admin
    public boolean checkAdminLogin(String phone, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ADMINS +
                " WHERE " + COLUMN_PHONE + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{phone, password});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }
}
