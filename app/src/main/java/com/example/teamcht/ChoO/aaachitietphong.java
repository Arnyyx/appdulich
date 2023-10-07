package com.example.teamcht.ChoO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.R;


public class aaachitietphong extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuongbooking);
        Intent intent = getIntent();
        String selectedLoaiPhong = intent.getStringExtra("selectedLoaiPhong");
        String selectedRoomNumber = intent.getStringExtra("selectedPhong");
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        room selectedRoom = dbHelper.getRoomByNumber(selectedRoomNumber);

        ImageView roomImageView = findViewById(R.id.roomImageView);
        TextView roomNameTextView = findViewById(R.id.roomNameTextView);
        TextView roomTypeTextView = findViewById(R.id.roomTypeTextView);
        TextView roomDescriptionTextView = findViewById(R.id.roomDescriptionTextView);
        TextView roomPriceTextView = findViewById(R.id.roomPriceTextView);
        TextView soluongnguoi = findViewById(R.id.soluongnguoitv);
        TextView diadiem = findViewById(R.id.diadiemtv);

        roomImageView.setImageResource(selectedRoom.getImageUrl());
        roomNameTextView.setText(selectedRoomNumber);
        roomTypeTextView.setText(selectedLoaiPhong);
        roomDescriptionTextView.setText(selectedRoom.getDescription());
        roomPriceTextView.setText("Giá: $" + selectedRoom.getPrice() + "/đêm");
        soluongnguoi.setText( selectedRoom.getSonguoitrongphong()+ " người/phòng");
        diadiem.setText( "Địa điểm: "+selectedRoom.getDiadiem());
        double price=selectedRoom.getPrice();
        Button bookNowButton = findViewById(R.id.bookNowButton);

        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(aaachitietphong.this, BookingActivity.class);
                intent.putExtra("selectedLoaiPhong", selectedLoaiPhong);
                intent.putExtra("selectedPhong", selectedRoomNumber);
                intent.putExtra("priceall", price);
                intent.putExtra("soluongnguoi", selectedRoom.getSonguoitrongphong());
                startActivity(intent);

            }
        });
    }

}
