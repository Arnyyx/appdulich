package com.example.teamcht.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.teamcht.Models.ChuyenDi;

import java.util.ArrayList;
import java.util.List;

public class DBChuyenDi extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbChuyenDi";
    private static final String TABLE_NAME = "tbChuyenDi";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PHUONGTIEN = "phuongTien";
    private static final String COLUMN_DIEMKHOIHANH = "DiemKhoiHanh";
    private static final String COLUMN_DIEMDIEN = "DiemDen";
    private static final String COLUMN_NGAYDI = "NgayDi";
    private static final String COLUMN_SOHANHKHACH = "SoHanhKhach";
    private static final String COLUMN_GIAVE = "GiaVe";

    public DBChuyenDi(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String value = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                TABLE_NAME, COLUMN_ID, COLUMN_PHUONGTIEN, COLUMN_DIEMKHOIHANH, COLUMN_DIEMDIEN, COLUMN_NGAYDI, COLUMN_SOHANHKHACH, COLUMN_GIAVE);
        db.execSQL(value);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String value = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(value);
        onCreate(db);
    }

    public long create(String phuongtien, String diemkhoihanh, String diemden, String ngaydi, String sohanhkhach, String giave) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHUONGTIEN, phuongtien);
        values.put(COLUMN_DIEMKHOIHANH, diemkhoihanh);
        values.put(COLUMN_DIEMDIEN, diemden);
        values.put(COLUMN_NGAYDI, ngaydi);
        values.put(COLUMN_SOHANHKHACH, sohanhkhach);
        values.put(COLUMN_GIAVE, giave);
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public int update(long id, String phuongtien, String diemkhoihanh, String diemden, String ngaydi, String sohanhkhach, String giave) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHUONGTIEN, phuongtien);
        values.put(COLUMN_DIEMKHOIHANH, diemkhoihanh);
        values.put(COLUMN_DIEMDIEN, diemden);
        values.put(COLUMN_NGAYDI, ngaydi);
        values.put(COLUMN_SOHANHKHACH, sohanhkhach);
        values.put(COLUMN_GIAVE, giave);
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void delete(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public ChuyenDi getValue(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_PHUONGTIEN, COLUMN_DIEMKHOIHANH, COLUMN_DIEMDIEN, COLUMN_NGAYDI, COLUMN_SOHANHKHACH, COLUMN_GIAVE}, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        ChuyenDi chuyendi = new ChuyenDi();
        chuyendi.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        chuyendi.setPhuongTien(cursor.getString(cursor.getColumnIndex(COLUMN_PHUONGTIEN)));
        chuyendi.setDiemKhoiHanh(cursor.getString(cursor.getColumnIndex(COLUMN_DIEMKHOIHANH)));
        chuyendi.setDiemDen(cursor.getString(cursor.getColumnIndex(COLUMN_DIEMDIEN)));
        chuyendi.setNgayDi(cursor.getString(cursor.getColumnIndex(COLUMN_NGAYDI)));
        chuyendi.setSoHanhKhach(cursor.getString(cursor.getColumnIndex(COLUMN_SOHANHKHACH)));
        chuyendi.setGiaVe(cursor.getString(cursor.getColumnIndex(COLUMN_GIAVE)));
        cursor.close();
        return chuyendi;
    }

    public List<ChuyenDi> getAll() {
        List<ChuyenDi> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ChuyenDi chuyendi = new ChuyenDi();
                chuyendi.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                chuyendi.setPhuongTien(cursor.getString(cursor.getColumnIndex(COLUMN_PHUONGTIEN)));
                chuyendi.setDiemKhoiHanh(cursor.getString(cursor.getColumnIndex(COLUMN_DIEMKHOIHANH)));
                chuyendi.setDiemDen(cursor.getString(cursor.getColumnIndex(COLUMN_DIEMDIEN)));
                chuyendi.setNgayDi(cursor.getString(cursor.getColumnIndex(COLUMN_NGAYDI)));
                chuyendi.setSoHanhKhach(cursor.getString(cursor.getColumnIndex(COLUMN_SOHANHKHACH)));
                chuyendi.setGiaVe(cursor.getString(cursor.getColumnIndex(COLUMN_GIAVE)));
                list.add(chuyendi);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }
}
