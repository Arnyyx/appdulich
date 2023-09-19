package com.example.teamcht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.teamcht.ChoO.ChoO;
import com.example.teamcht.HoatDongGiaiTri.HoatDongGiaiTri;
import com.example.teamcht.VanChuyen.VanChuyen;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.btnChoO).setOnClickListener(view -> startActivity(new Intent(this, ChoO.class)));
        findViewById(R.id.btnHoaDongGiaiTri).setOnClickListener(view -> startActivity(new Intent(this, HoatDongGiaiTri.class)));
        findViewById(R.id.btnVanChuyen).setOnClickListener(view -> startActivity(new Intent(this, VanChuyen.class)));
    }
}