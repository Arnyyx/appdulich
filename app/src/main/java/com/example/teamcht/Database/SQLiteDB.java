package com.example.teamcht.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.teamcht.VanChuyen.ChuyenDi;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDB extends SQLiteOpenHelper {
    public SQLiteDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void QueryData(String sql) {
        SQLiteDatabase data = getWritableDatabase();
        data.execSQL(sql);
    }

    public Cursor getData(String sql) {
        SQLiteDatabase data = getReadableDatabase();
        return data.rawQuery(sql, null);
    }

    public List<ChuyenDi> getAllChuyenDi(String sql) {
        List<ChuyenDi> chuyenDiList = new ArrayList<>();

        // Tạo đối tượng SQLiteDatabase
        SQLiteDatabase db = this.getReadableDatabase();

        // Chạy truy vấn SQL để lấy dữ liệu từ bảng
        Cursor cursor = db.rawQuery(sql, null);

        // Duyệt qua các hàng trong kết quả truy vấn
        while (cursor.moveToNext()) {
            // Tạo một đối tượng ChuyenDi
            ChuyenDi chuyenDi = new ChuyenDi();

            // Đặt các giá trị cho đối tượng ChuyenDi
//            chuyenDi.setId(cursor.getInt(cursor.getColumnIndex("id")));
            chuyenDi.setPhuongTien(cursor.getString(cursor.getColumnIndex("phuongTien")));
            chuyenDi.setDiemKhoiHanh(cursor.getString(cursor.getColumnIndex("diemKhoiHanh")));
            chuyenDi.setDiemDen(cursor.getString(cursor.getColumnIndex("diemDen")));
            chuyenDi.setNgayDi(cursor.getString(cursor.getColumnIndex("ngayDi")));
            chuyenDi.setSoHanhKhach(cursor.getString(cursor.getColumnIndex("soHanhKhach")));
            chuyenDi.setGiaVe(cursor.getString(cursor.getColumnIndex("giaVe")));

            // Thêm ChuyenDi vào danh sách
            chuyenDiList.add(chuyenDi);
        }

        // Đóng kết quả truy vấn
        cursor.close();

        return chuyenDiList;
    }
}
