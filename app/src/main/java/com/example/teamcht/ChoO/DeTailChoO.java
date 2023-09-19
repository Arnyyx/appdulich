package com.example.teamcht.ChoO;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.R;


public class DeTailChoO extends AppCompatActivity {

    private ImageView anhphong;
    private TextView mota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuongdetailchoo);

        anhphong = findViewById(R.id.anhphong);
        mota = findViewById(R.id.motaphong);

        Intent intent = getIntent();

        // Hiển thị thông tin địa điểm
        mota.setText(intent.getStringExtra("Name"));
        anhphong.setImageResource(intent.getIntExtra("Image", 0));

    }
}



