package com.example.teamcht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.teamcht.ChoO.loaiphong;
import com.example.teamcht.DangNhap.QLTaiKhoan;
import com.example.teamcht.DangNhap.Splash;
import com.example.teamcht.Models.TaiKhoan;
import com.example.teamcht.Database.DBTaiKhoan;
import com.example.teamcht.HoatDongGiaiTri.HoatDongGiaiTri;
import com.example.teamcht.VanChuyen.VanChuyen;

import java.util.List;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.btnChoO).setOnClickListener(view -> startActivity(new Intent(this, loaiphong.class)));
        findViewById(R.id.btnHoaDongGiaiTri).setOnClickListener(view -> startActivity(new Intent(this, HoatDongGiaiTri.class)));
        findViewById(R.id.btnVanChuyen).setOnClickListener(view -> startActivity(new Intent(this, VanChuyen.class)));
        findViewById(R.id.btnTaiKhoan).setOnClickListener(view -> startActivity(new Intent(this, QLTaiKhoan.class)));
        findViewById(R.id.btnDangXuat).setOnClickListener(view -> {
            DBTaiKhoan db = new DBTaiKhoan(this);
            List<TaiKhoan> taiKhoanList = db.getAll();
            int i = 0;
            for (TaiKhoan a : taiKhoanList) {
                if (a.getStatus().matches("1")) {
                    db.update(a.getId(), a.getName(), a.getPassword(), "");
                    startActivity(new Intent(this, Splash.class));
                    finish();
                    break;
                }
                i++;
            }
            if (i == taiKhoanList.size()) {
                Toast.makeText(this, "Lỗi đăng xuất", Toast.LENGTH_SHORT).show();
            }
        });
    }
}