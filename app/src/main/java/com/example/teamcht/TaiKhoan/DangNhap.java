package com.example.teamcht.TaiKhoan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.ChoO.ChoO;
import com.example.teamcht.Database.DBTaiKhoan;
import com.example.teamcht.Models.TaiKhoan;
import com.example.teamcht.R;

import java.util.ArrayList;
import java.util.List;

public class DangNhap extends AppCompatActivity {
    DBTaiKhoan db;
    EditText name, pass;
    List<TaiKhoan> taiKhoanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taikhoan_dangnhap);

        db = new DBTaiKhoan(this);
        taiKhoanList = new ArrayList<>();
        taiKhoanList = db.getAll();

        findViewById(R.id.btnDangNhap).setOnClickListener(view -> {
            name = findViewById(R.id.name);
            pass = findViewById(R.id.password);
            String strName = name.getText().toString();
            String strPass = pass.getText().toString();

            if (strName.matches("")) {
                name.requestFocus();
                name.setError(getString(R.string.TaiKhoanNull));
            } else if (strPass.matches("")) {
                pass.requestFocus();
                pass.setError(getString(R.string.MatKhauNull));
            } else {
                int i = 0;
                for (TaiKhoan a : taiKhoanList) {
                    if (a.getName().matches(strName) && a.getPassword().matches(strPass)) {
                        db.update(a.getId(), a.getName(), a.getPassword(), "1");
                        startActivity(new Intent(this, ChoO.class));
                        finish();
                        break;
                    }
                    i++;
                }
                if (i == taiKhoanList.size()) {
                    Toast.makeText(this, "Sai tên tài khoaản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.textDangKy).setOnClickListener(view -> startActivity(new Intent(this, DangKy.class)));
    }
}
