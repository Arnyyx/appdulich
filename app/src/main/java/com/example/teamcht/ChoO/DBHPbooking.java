package com.example.teamcht.ChoO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHPbooking extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BookingDatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_BOOKINGS = "bookings";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CHECK_IN_DATE = "check_in_date";
    private static final String COLUMN_CHECK_OUT_DATE = "check_out_date";
    private static final String COLUMN_BOOKING_DATE = "booking_date";
    private static final String COLUMN_ROOM_TYPE = "room_type";
    private static final String COLUMN_SOLUONG = "soluong";
    private static final String COLUMN_ROOM_NUMBER = "room_number";
    private static final String COLUMN_PRICE_ALL = "priceall";

    public DBHPbooking(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_BOOKINGS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_CHECK_IN_DATE + " TEXT," +
                COLUMN_CHECK_OUT_DATE + " TEXT," +
                COLUMN_BOOKING_DATE + " TEXT," +
                COLUMN_ROOM_TYPE + " TEXT," +
                COLUMN_SOLUONG + " INTEGER," +
                COLUMN_ROOM_NUMBER + " TEXT," +
                COLUMN_PRICE_ALL + " TEXT" +
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        onCreate(db);
    }

    public long addBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, booking.getName());
        values.put(COLUMN_CHECK_IN_DATE, booking.getCheckInDate());
        values.put(COLUMN_CHECK_OUT_DATE, booking.getCheckOutDate());
        values.put(COLUMN_BOOKING_DATE, booking.getBookingDate());
        values.put(COLUMN_ROOM_TYPE, booking.getRoomType());
        values.put(COLUMN_SOLUONG, booking.getsoluong());
        values.put(COLUMN_ROOM_NUMBER, booking.getRoomNumber());
        values.put(COLUMN_PRICE_ALL, booking.getPriceall());
        long id = db.insert(TABLE_BOOKINGS, null, values);
        db.close();
        return id;
    }

    public ArrayList<Booking> getAllBookings() {
        ArrayList<Booking> bookingsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_BOOKINGS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Booking booking = new Booking();
                booking.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                booking.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                booking.setCheckInDate(cursor.getString(cursor.getColumnIndex(COLUMN_CHECK_IN_DATE)));
                booking.setCheckOutDate(cursor.getString(cursor.getColumnIndex(COLUMN_CHECK_OUT_DATE)));
                booking.setBookingDate(cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_DATE)));
                booking.setRoomType(cursor.getString(cursor.getColumnIndex(COLUMN_ROOM_TYPE)));
                booking.setSoluong(cursor.getInt(cursor.getColumnIndex(COLUMN_SOLUONG)));
                booking.setRoomNumber(cursor.getString(cursor.getColumnIndex(COLUMN_ROOM_NUMBER)));
                booking.setPriceall(cursor.getString(cursor.getColumnIndex(COLUMN_PRICE_ALL)));
                bookingsList.add(booking);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bookingsList;
    }

    public boolean xoaphong(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_BOOKINGS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return deletedRows > 0;
    }

    public int updateBooking(long bookingId, String name, String checkIn, String checkOut, String guests) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CHECK_IN_DATE, checkIn);
        values.put(COLUMN_CHECK_OUT_DATE, checkOut);
        values.put(COLUMN_SOLUONG, guests);

        int rowsAffected = db.update("bookings", values, COLUMN_ID + " = ?", new String[]{String.valueOf(bookingId)});
        db.close();

        return rowsAffected;
    }
}
