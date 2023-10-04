package com.example.teamcht.DangNhap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.Database.DBTaiKhoan;
import com.example.teamcht.R;

public class DangKy extends AppCompatActivity {

    EditText name, pass;
    DBTaiKhoan db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taikhoan_dangky);

        db = new DBTaiKhoan(this);

        findViewById(R.id.btnDangKy).setOnClickListener(view -> {
            name = findViewById(R.id.name);
            pass = findViewById(R.id.password);
            String strName = name.getText().toString();
            String strPass = pass.getText().toString();

            if (strName.matches("")) {
                name.requestFocus();
                name.setError("Hãy nhập tên đăng nhập");
            } else if (strPass.matches("")) {
                pass.requestFocus();
                pass.setError("Hãy nhập mật khẩu");
            } else {
                db.create(strName, strPass, "");
                Toast.makeText(this, "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, DangNhap.class));
                finish();
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(view -> onBackPressed());
    }
}
