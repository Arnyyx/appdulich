package com.example.teamcht.DangNhap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.Database.DBTaiKhoan;
import com.example.teamcht.Main;
import com.example.teamcht.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Splash extends AppCompatActivity {
    DBTaiKhoan db;
    List<TaiKhoan> taiKhoanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taikhoan_splash);

        db = new DBTaiKhoan(this);
        taiKhoanList = new ArrayList<>();
        taiKhoanList = db.getAll();

        new Handler().postDelayed(() -> {
            int i = 0;
            for (TaiKhoan a : taiKhoanList) {
                if (Objects.equals(a.getStatus(), "1")) {
                    startActivity(new Intent(this, Main.class));
                    finish();
                    break;
                }
                i++;
            }
            if (i == taiKhoanList.size()) {
                startActivity(new Intent(this, DangNhap.class));
                finish();
            }
        }, 1000);
    }
}
