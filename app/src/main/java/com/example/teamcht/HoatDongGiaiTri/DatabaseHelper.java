package com.example.teamcht.HoatDongGiaiTri;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dulich";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_DIADIEM_TABLE = "CREATE TABLE diadiem (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT," +
            "price INT," +
            "description TEXT," +
            "image_url TEXT);";

    private static final String CREATE_GIOHANGDIADIEM_TABLE = "CREATE TABLE giohang_diadiem (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT," +
            "price INT," +
            "quantity INT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DIADIEM_TABLE);
//        db.execSQL(CREATE_GIOHANGDIADIEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS diadiem");
//        db.execSQL("DROP TABLE IF EXISTS giohang_diadiem");
        onCreate(db);
    }
    public void addPlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("price", place.getPrice());
        values.put("name", place.getName());
        values.put("image_url", place.getImageResource());
        values.put("description", place.getDescription());
        db.insert("diadiem", null, values);
        db.close();
    }

    public ArrayList<Place> getdata_diadiem() {
        ArrayList<Place> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name, price, image_url, description FROM diadiem", null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int price = cursor.getInt(cursor.getColumnIndex("price"));
                int imageResourceId = cursor.getInt(cursor.getColumnIndex("image_url"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                Place item = new Place(imageResourceId, name, price, description);
                list.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    public ArrayList<CartItem> getCartItems() {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM giohang_diadiem", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int price = cursor.getInt(cursor.getColumnIndex("price"));
                int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
                cartItems.add(new CartItem(id, name, price, quantity));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return cartItems;
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    public void updateCartItemQuantity(int cartItemId, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", newQuantity);
        db.update("giohang_diadiem", values, "id = ?", new String[]{String.valueOf(cartItemId)});
        db.close();
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeFromCart(int cartItemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("giohang_diadiem", "id = ?", new String[]{String.valueOf(cartItemId)});
        db.close();
    }
}
