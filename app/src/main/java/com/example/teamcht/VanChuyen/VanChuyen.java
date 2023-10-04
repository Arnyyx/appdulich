package com.example.teamcht.VanChuyen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.R;

public class VanChuyen extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vanchuyen);

        findViewById(R.id.btnQLChuyenDi).setOnClickListener(view -> {
            startActivity(new Intent(this, QLChuyenDi.class));
        });
    }
}
