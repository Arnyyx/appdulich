package com.example.teamcht.ChoO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.teamcht.R;

import java.util.ArrayList;

public class BookingListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Booking> bookingList;
    private DatabaseHelper databaseHelper;

    public BookingListAdapter(Context context, ArrayList<Booking> bookingList, DatabaseHelper databaseHelper) {
        this.context = context;
        this.bookingList = bookingList;
        this.databaseHelper = databaseHelper;
    }

    @Override
    public int getCount() {
        return bookingList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cuongbooking_list_item, null);
        }

        Booking booking = bookingList.get(position);

        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewbookingdate = convertView.findViewById(R.id.textViewbookingdate);
        TextView textViewCheckInDate = convertView.findViewById(R.id.textViewCheckInDate);
        TextView textViewCheckOutDate = convertView.findViewById(R.id.textViewCheckOutDate);
        TextView textViewRoomType = convertView.findViewById(R.id.textViewRoomType);
        TextView textViewRoomNumber = convertView.findViewById(R.id.textViewRoomNumber);
        TextView textViewsoluong = convertView.findViewById(R.id.textViewNumberOfGuests);
        TextView price = convertView.findViewById(R.id.priceTextView);

        textViewName.setText("Tên: " + booking.getName());
        textViewbookingdate.setText("Ngày đặt: " + booking.getBookingDate());
        textViewCheckInDate.setText("Ngày đến: " + booking.getCheckInDate());
        textViewCheckOutDate.setText("Ngày đi: " + booking.getCheckOutDate());
        textViewRoomType.setText("Loại phòng: " + booking.getRoomType());
        textViewRoomNumber.setText("Số phòng: " + booking.getRoomNumber());
        textViewsoluong.setText("Số lượng người: " + booking.getsoluong());
        price.setText(booking.getPriceall() + "/Đêm");

        Button buttonDelete = convertView.findViewById(R.id.huydatphong);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bookingId = booking.getId();
                removeBooking(bookingId);
            }
        });

        return convertView;
    }

    public void removeBooking(int bookingId) {
        for (int i = 0; i < bookingList.size(); i++) {
            Booking booking = bookingList.get(i);
            if (booking.getId() == bookingId) {
                bookingList.remove(i);
                notifyDataSetChanged();
                break;
            }
        }

        databaseHelper.deleteBookingFromDatabase(bookingId);
    }


}
