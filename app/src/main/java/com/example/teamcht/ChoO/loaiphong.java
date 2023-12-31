package com.example.teamcht.ChoO;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.R;

import java.util.ArrayList;
import java.util.Arrays;

public class loaiphong extends AppCompatActivity {
    private Button giaDinhButton;
    private Button donButton;
    private Button suiteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuongmanhinhroom);

        giaDinhButton = findViewById(R.id.roomgiadinh);
        donButton = findViewById(R.id.roomdon);
        suiteButton = findViewById(R.id.roomsuite);

        giaDinhButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedLoaiPhong = "Gia đình";
                start(selectedLoaiPhong);
            }
        });

        donButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedLoaiPhong = "Đơn";
                start(selectedLoaiPhong);

            }
        });

        suiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedLoaiPhong = "Suite";
                start(selectedLoaiPhong);

            }
        });
    }
    private void start(String selectedLoaiPhong){
        Intent intent = new Intent(loaiphong.this, aaadanhsachphong.class);
        intent.putExtra("selectedLoaiPhong", selectedLoaiPhong);
        startActivity(intent);
    }
}



