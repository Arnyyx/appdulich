package com.example.teamcht.ChoO;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.ChoO.DBHPbooking;
import com.example.teamcht.R;

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
        bookingList = new ArrayList<>();
        DBHPbooking dbHelper = new DBHPbooking(lichsudatphong.this);
        ArrayList<Booking> bookings = dbHelper.getAllBookings();
        bookingList.addAll(bookings);
        adapter = new BookingListAdapter(lichsudatphong.this, bookingList, dbHelper);
        dslv.setAdapter(adapter);

        adapter.notifyDataSetChanged();


    }


}
