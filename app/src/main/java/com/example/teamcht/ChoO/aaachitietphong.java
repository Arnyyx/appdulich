package com.example.teamcht.ChoO;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.R;

import java.util.Calendar;

public class aaachitietphong extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuongbooking);
        Intent intent = getIntent();
        String selectedLoaiPhong = intent.getStringExtra("selectedLoaiPhong");
        String selectedRoomNumber = getIntent().getStringExtra("selectedPhong");

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        room selectedRoom = dbHelper.getRoomByNumber(selectedRoomNumber);

        ImageView roomImageView = findViewById(R.id.roomImageView);
        TextView roomNameTextView = findViewById(R.id.roomNameTextView);
        TextView roomTypeTextView = findViewById(R.id.roomTypeTextView);
        TextView roomDescriptionTextView = findViewById(R.id.roomDescriptionTextView);
        TextView roomPriceTextView = findViewById(R.id.roomPriceTextView);

        roomImageView.setImageResource(selectedRoom.getImageUrl());
        roomNameTextView.setText(selectedRoom.getRoomNumber());
        roomTypeTextView.setText(selectedLoaiPhong);
        roomDescriptionTextView.setText(selectedRoom.getDescription());
        roomPriceTextView.setText("Giá: $" + selectedRoom.getPrice() + "/đêm");
        double price=selectedRoom.getPrice();
        Button bookNowButton = findViewById(R.id.bookNowButton);

        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(aaachitietphong.this, BookingActivity.class);
                intent.putExtra("selectedLoaiPhong", selectedLoaiPhong);
                intent.putExtra("selectedPhong", selectedRoomNumber);
                intent.putExtra("priceall", price);
              //  intent.putExtra("soluongnguoi", selectedRoom.getSonguoitrongphong());
                startActivity(intent);

        }
        });
    }

}
