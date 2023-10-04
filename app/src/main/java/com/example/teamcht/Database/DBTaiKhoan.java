package com.example.teamcht.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.teamcht.DangNhap.TaiKhoan;

import java.util.ArrayList;
import java.util.List;

public class DBTaiKhoan extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbTaiKhoan";
    private static final String TABLE_NAME = "tbTaiKhoan";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_STATUS = "status";

    public DBTaiKhoan(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String value = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT)", TABLE_NAME, COLUMN_ID, COLUMN_NAME, COLUMN_PASSWORD, COLUMN_STATUS);
        db.execSQL(value);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String value = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(value);
        onCreate(db);
    }

    public long create(String name, String password, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_STATUS, status);
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public int update(long id, String name, String password, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_STATUS, status);
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void delete(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public TaiKhoan getValue(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_PASSWORD, COLUMN_STATUS}, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        TaiKhoan taikhoan = new TaiKhoan();
        taikhoan.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        taikhoan.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        taikhoan.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
        taikhoan.setStatus(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));
        cursor.close();
        return taikhoan;
    }

    public List<TaiKhoan> getAll() {
        List<TaiKhoan> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                TaiKhoan taikhoan = new TaiKhoan();
                taikhoan.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                taikhoan.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                taikhoan.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
                taikhoan.setStatus(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));
                list.add(taikhoan);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }
}
