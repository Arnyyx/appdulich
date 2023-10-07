package com.example.teamcht.ChoO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Room.db";
    private static final int DATABASE_VERSION = 1;

    // Tạo bảng Rooms
    private static final String CREATE_ROOMS_TABLE = "CREATE TABLE Rooms (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "room_number TEXT," +
            "description TEXT," +
            "price REAL," +
            "image_url TEXT," +
            "room_type TEXT," +
            "soluongnguoi INTEGER," +
            "dia_diem TEXT);";

//tạo bảng booking

    private static final String CREATE_BOOKINGS_TABLE = "CREATE TABLE Bookings (" +
            "id INTEGER PRIMARY KEY," +
            "customer_name TEXT," +
            "booking_date DATE," +
            "checkin_date DATE," +
            "checkout_date DATE," +
            "room_type TEXT," +
            "number_of_guests INTEGER," +
            "room_number TEXT," +
            "priceall TEXT);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ROOMS_TABLE);
        db.execSQL(CREATE_BOOKINGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);    }
    public void addRoom(room room) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("room_number", room.getRoomNumber());
        values.put("description", room.getDescription());
        values.put("price", room.getPrice());
        values.put("image_url", room.getImageUrl());
        values.put("room_type", room.getRoomType());
        values.put("soluongnguoi", room.getSonguoitrongphong());
        values.put("dia_diem", room.getDiadiem());
        db.insert("Rooms", null, values);
        db.close();
    }


    public ArrayList<room> getAllRooms(String roomtype) {
        ArrayList<room> roomList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT room_number, description, price, image_url, soluongnguoi,dia_diem FROM Rooms WHERE room_type = ?";
        Cursor cursor = db.rawQuery(query, new String[]{roomtype});

        if (cursor.moveToFirst()) {
            do {
                String description = cursor.getString(cursor.getColumnIndex("description"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                int imageUrl = cursor.getInt(cursor.getColumnIndex("image_url"));
                String roomNumber = cursor.getString(cursor.getColumnIndex("room_number"));
                int soluongnguoi = cursor.getInt(cursor.getColumnIndex("soluongnguoi"));
                String diadiem=cursor.getString (cursor.getColumnIndex("dia_diem"));
                room room = new room(description, price, imageUrl, roomNumber, soluongnguoi,diadiem);
                roomList.add(room);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return roomList;
    }



    public room getRoomByNumber(String roomNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Rooms WHERE room_number = ?", new String[]{roomNumber});
        room selectedRoom = null;
        if (cursor.moveToFirst()) {
            String description = cursor.getString(cursor.getColumnIndex("description"));
            double price = cursor.getDouble(cursor.getColumnIndex("price"));
            int imageResourceId = cursor.getInt(cursor.getColumnIndex("image_url"));
            int soluongnguoi = cursor.getInt(cursor.getColumnIndex("soluongnguoi"));
            String diadiem=cursor.getString (cursor.getColumnIndex("dia_diem"));

            selectedRoom = new room(description, price, imageResourceId, roomNumber,soluongnguoi,diadiem);
        }

        cursor.close();
        db.close();

        return selectedRoom;
    }

    public ArrayList<String> getRoom() {
        ArrayList<String> roomList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT room_number FROM Rooms", null);

        if (cursor.moveToFirst()) {
            do {
                String roomNumber = cursor.getString(cursor.getColumnIndex("room_number"));
                roomList.add(roomNumber);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return roomList;
    }
    public long addBooking(String customerName, String bookingDate, String checkInDate,
                           String checkOutDate, String roomType, int numberOfGuests,
                           String roomNumber, String priceall) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("customer_name", customerName);
        values.put("booking_date", bookingDate);
        values.put("checkin_date", checkInDate);
        values.put("checkout_date", checkOutDate);
        values.put("room_type", roomType);
        values.put("number_of_guests", numberOfGuests);
        values.put("room_number", roomNumber);
        values.put("priceall", priceall);
        long id = db.insert("Bookings", null, values);
        db.close();
        return id ;


    }



    public ArrayList<Booking> getAllBookings() {
        ArrayList<Booking> bookingList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Bookings", null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));

                String customerName = cursor.getString(cursor.getColumnIndex("customer_name"));
                String bookingDate = cursor.getString(cursor.getColumnIndex("booking_date"));
                String checkinDate = cursor.getString(cursor.getColumnIndex("checkin_date"));
                String checkoutDate = cursor.getString(cursor.getColumnIndex("checkout_date"));
                String roomType = cursor.getString(cursor.getColumnIndex("room_type"));
                int numberOfGuests = cursor.getInt(cursor.getColumnIndex("number_of_guests"));
                String roomNumber = cursor.getString(cursor.getColumnIndex("room_number"));
                String priceallll = cursor.getString(cursor.getColumnIndex("priceall"));
                Booking booking = new Booking(customerName,  checkinDate, checkoutDate,bookingDate, roomType, numberOfGuests, roomNumber, priceallll);
                bookingList.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return bookingList;
    }

    public boolean xoaphong(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete("Bookings", "id=0", new String[]{String.valueOf(id)});
        db.close();
        // Nếu có ít nhất một dòng bị xóa, trả về true, ngược lại trả về false.

        return deletedRows>0;
    }
    public ArrayList<room> timkiem(String keyword, String loaiPhong) {
        ArrayList<room> searchResults = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM Rooms WHERE dia_diem LIKE ? AND room_type=?";
        Cursor cursor = db.rawQuery(query, new String[]{keyword + "%", loaiPhong});

        if (cursor.moveToFirst()) {
            do {
                String description = cursor.getString(cursor.getColumnIndex("description"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                int imageUrl = cursor.getInt(cursor.getColumnIndex("image_url"));
                String roomNumber = cursor.getString(cursor.getColumnIndex("room_number"));
                int soluongnguoi = cursor.getInt(cursor.getColumnIndex("soluongnguoi"));
                String diadiem = cursor.getString(cursor.getColumnIndex("dia_diem"));

                room room = new room(description, price, imageUrl, roomNumber, soluongnguoi, diadiem);
                searchResults.add(room);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return searchResults;
    }




}


