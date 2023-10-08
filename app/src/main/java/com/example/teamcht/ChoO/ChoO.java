package com.example.teamcht.ChoO;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.HoatDongGiaiTri.DanhSachDiaDiem;
import com.example.teamcht.R;
import com.example.teamcht.TaiKhoan.QLTaiKhoan;
import com.example.teamcht.VanChuyen.VanChuyen;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChoO extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choo);

        findViewById(R.id.btnDatPhong).setOnClickListener(view -> startActivity(new Intent(this, loaiphong.class)));
        findViewById(R.id.btnLichSuDatPhong).setOnClickListener(view -> startActivity(new Intent(this, lichsudatphong.class)));

        navigate();
    }

    private void navigate() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.place);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.account) {
                startActivity(new Intent(this, QLTaiKhoan.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.place) {
                return true;
            } else if (itemId == R.id.entertainment) {
                startActivity(new Intent(this, DanhSachDiaDiem.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.transport) {
                startActivity(new Intent(this, VanChuyen.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });
    }
}
