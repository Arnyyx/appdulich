package com.example.teamcht.HoatDongGiaiTri;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.ChoO.loaiphong;
import com.example.teamcht.R;
import com.example.teamcht.TaiKhoan.QLTaiKhoan;
import com.example.teamcht.VanChuyen.QLChuyenDi;
import com.example.teamcht.VanChuyen.VanChuyen;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HoatDongGiaiTri extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoatdonggiaitri);

        navigate();
    }

    private void navigate() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.entertainment);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.account) {
                startActivity(new Intent(getApplicationContext(), QLTaiKhoan.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.place) {
                startActivity(new Intent(getApplicationContext(), loaiphong.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.entertainment) {
                return true;
            } else if (itemId == R.id.transport) {
                startActivity(new Intent(getApplicationContext(), VanChuyen.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }
}
