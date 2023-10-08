package com.example.teamcht.VanChuyen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.ChoO.ChoO;
import com.example.teamcht.HoatDongGiaiTri.DanhSachDiaDiem;
import com.example.teamcht.R;
import com.example.teamcht.TaiKhoan.QLTaiKhoan;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class VanChuyen extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vanchuyen);

        findViewById(R.id.btnQLChuyenDi).setOnClickListener(view -> startActivity(new Intent(this, QLChuyenDi.class)));
        findViewById(R.id.btnMuaVe).setOnClickListener(view -> startActivity(new Intent(this, MuaVe.class)));
        navigate();
    }

    private void navigate() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.transport);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.account) {
                startActivity(new Intent(this, QLTaiKhoan.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.place) {
                startActivity(new Intent(this, ChoO.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.entertainment) {
                startActivity(new Intent(this, DanhSachDiaDiem.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.transport) {
                return true;
            }
            return false;
        });
    }
}
