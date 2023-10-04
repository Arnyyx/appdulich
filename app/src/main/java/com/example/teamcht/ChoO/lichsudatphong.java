package com.example.teamcht.ChoO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.R;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class lichsudatphong extends AppCompatActivity {
    private ListView dslv;
    private BookingListAdapter adapter;
    private ArrayList<Booking> bookingList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lichsudatphong);
        dslv = findViewById(R.id.danhsachphongdadat);
        bookingList = new ArrayList<Booking>();
        DatabaseHelper dbHelper = new DatabaseHelper(lichsudatphong.this);

        adapter = new BookingListAdapter(lichsudatphong.this, bookingList,dbHelper);
        dslv.setAdapter(adapter);
        bookingList.clear();
        ArrayList<Booking> bookings = dbHelper.getAllBookings();
        bookingList.addAll(bookings);
        adapter.notifyDataSetChanged();
    }
}
