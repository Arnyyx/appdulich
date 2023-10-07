package com.example.teamcht.ChoO;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamcht.R;

import java.util.ArrayList;

public class BookingListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Booking> bookingList;
    private DBHPbooking databaseHelper;

    public BookingListAdapter(Context context, ArrayList<Booking> bookingList, DBHPbooking databaseHelper) {

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
        TextView price = convertView.findViewById(R.id.priceTextView1);
        TextView id = convertView.findViewById(R.id.textViewidBooking);

        id.setText("Mã đặt phòng: " + booking.getId());
        textViewName.setText("Tên: " + booking.getName());
        textViewbookingdate.setText( booking.getBookingDate());
        textViewCheckInDate.setText(booking.getCheckInDate());
        textViewCheckOutDate.setText(booking.getCheckOutDate());
        textViewRoomType.setText("Loại phòng: " + booking.getRoomType());
        textViewRoomNumber.setText("Số phòng: " + booking.getRoomNumber());
        textViewsoluong.setText("Số lượng người: " + booking.getsoluong());
        price.setText(booking.getPriceall() );


        Button buttonDelete = convertView.findViewById(R.id.huydatphong);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa bản ghi này?");

                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long bookingId = booking.getId();
                        Toast.makeText(context, ""+bookingId, Toast.LENGTH_SHORT).show();
                        boolean isDeleted = databaseHelper.xoaphong(bookingId);

                        if (isDeleted) {
                            bookingList.remove(booking);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xóa không thành công.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        Button sua= convertView.findViewById(R.id.suathongtinbooking);
        sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customalert a= new customalert(context, databaseHelper,booking,BookingListAdapter.this);

                a.show();
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bookingId = booking.getId();
                removeBooking(bookingId);
            }
        });

        return convertView;
    }

    public void updateBookingList(ArrayList<Booking> newBookingList) {
        this.bookingList = newBookingList;
        notifyDataSetChanged();
    }
}
