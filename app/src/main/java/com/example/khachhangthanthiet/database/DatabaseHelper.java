package com.example.khachhangthanthiet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import com.example.khachhangthanthiet.model.Customer;

import java.util.ArrayList;


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
        //Tạo bảng customers
        String createCustomerTable = "CREATE TABLE " + TABLE_CUSTOMERS + " (" +
                COLUMN_PHONE + " TEXT PRIMARY KEY, " +
                COLUMN_POINTS + " INTEGER, " +
                COLUMN_CREATED_DATE + " TEXT, " +
                COLUMN_LAST_UPDATED + " TEXT)";
        db.execSQL(createCustomerTable);

        //Tạo bảng admins (liên kết với customers)
        String createAdminTable = "CREATE TABLE " + TABLE_ADMINS + " (" +
                COLUMN_PHONE + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_PHONE + ") REFERENCES " + TABLE_CUSTOMERS + "(" + COLUMN_PHONE + "))";
        db.execSQL(createAdminTable);

        //Thêm admin mặc định ngay khi database được tạo
        insertDefaultAdmin(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMINS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
        onCreate(db);
    }

    //Thêm Customer (sử dụng cho cả Admin)
    private void insertCustomer(SQLiteDatabase db, String phone, int points, String created, String updated) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_POINTS, points);
        values.put(COLUMN_CREATED_DATE, created);
        values.put(COLUMN_LAST_UPDATED, updated);
        db.insert(TABLE_CUSTOMERS, null, values);
    }

    //Thêm Admin
    private void insertAdmin(SQLiteDatabase db, String phone, String password) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_ADMINS, null, values);
    }

    //Thêm admin mặc định khi database được tạo
    private void insertDefaultAdmin(SQLiteDatabase db) {
        String defaultPhone = "0909853845";
        String defaultPassword = "123456";
        String date = "2025-10-19";

        insertCustomer(db, defaultPhone, 0, date, date);
        insertAdmin(db, defaultPhone, defaultPassword);
    }

    //Kiểm tra đăng nhập admin
    public boolean checkAdminLogin(String phone, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ADMINS +
                        " WHERE " + COLUMN_PHONE + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{phone, password});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }
    public boolean changeAdminPassword(String phone, String oldPassword, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        //Kiểm tra xem tài khoản tồn tại và mật khẩu cũ có đúng không
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_ADMINS +
                        " WHERE " + COLUMN_PHONE + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{phone, oldPassword}
        );

        if (cursor.moveToFirst()) {
        // Nếu đúng, tiến hành cập nhật mật khẩu mới
            ContentValues values = new ContentValues();
            values.put(COLUMN_PASSWORD, newPassword);

            int rows = db.update(
                    TABLE_ADMINS,
                    values,
                    COLUMN_PHONE + "=?",
                    new String[]{phone}
            );

            cursor.close();
            db.close();
            return rows > 0; //Thành công
        } else {
            cursor.close();
            db.close();
            return false; // Sai mật khẩu cũ hoặc không tồn tại
        }
    }
    public boolean isAdmin(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM admins WHERE phoneNumber = ?", new String[]{phoneNumber});
        boolean isAdmin = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isAdmin;
    }

    // Lấy danh sách tất cả khách hàng
    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customerList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CUSTOMERS, null);
        if (cursor.moveToFirst()) {
            do {
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
                int points = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_POINTS));
                String createdDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_DATE));
                String lastUpdated = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_UPDATED));

                // Create a Customer object and add it to the list
                Customer customer = new Customer(phone, points, createdDate, lastUpdated);
                customerList.add(customer);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return customerList;
    }
    public void deleteCustomer(String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CUSTOMERS, COLUMN_PHONE + "=?", new String[]{phoneNumber});
        db.close();
    }
    public int getCurrentPoints(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CUSTOMERS,
                new String[]{COLUMN_POINTS},
                COLUMN_PHONE + "=?",
                new String[]{phoneNumber},
                null, null, null);

        int points = -1;
        if (cursor != null && cursor.moveToFirst()) {
            points = cursor.getInt(0);
            cursor.close();
        }

        return points;
    }

    public boolean updatePoints(String phoneNumber, int additionalPoints) {
        SQLiteDatabase db = this.getWritableDatabase();

        int currentPoints = getCurrentPoints(phoneNumber);
        if (currentPoints == -1) {
            return false;
        }

        int updatedPoints = currentPoints + additionalPoints;

        ContentValues values = new ContentValues();
        values.put(COLUMN_POINTS, updatedPoints);

        int rows = db.update(TABLE_CUSTOMERS,
                values,
                COLUMN_PHONE + "=?",
                new String[]{phoneNumber});

        db.close();
        return rows > 0;
    }


    public boolean usePoints(String phoneNumber, int usedPoints) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Lấy điểm hiện tại
        int currentPoints = getCurrentPoints(phoneNumber);
        if (currentPoints == -1) {
            return false; // Không tìm thấy số điện thoại
        }

        // Kiểm tra đủ điểm không
        if (currentPoints < usedPoints) {
            return false; // Không đủ điểm để sử dụng
        }

        // Tính điểm sau khi trừ
        int remainingPoints = currentPoints - usedPoints;

        ContentValues values = new ContentValues();
        values.put(COLUMN_POINTS, remainingPoints);

        int rows = db.update(
                TABLE_CUSTOMERS,
                values,
                COLUMN_PHONE + "=?",
                new String[]{phoneNumber}
        );

        db.close();
        return rows > 0;
    }


}

