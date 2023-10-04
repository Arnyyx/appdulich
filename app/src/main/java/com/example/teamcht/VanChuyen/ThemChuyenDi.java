package com.example.teamcht.VanChuyen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.Database.SQLiteDB;
import com.example.teamcht.R;

public class ThemChuyenDi extends AppCompatActivity {
    EditText phuongTien, diemKhoiHanh, diemDen, ngayDi, soHanhKhach, giaVe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vanchuyen_them);

        phuongTien = findViewById(R.id.phuongTien);
        diemKhoiHanh = findViewById(R.id.diemKhoiHanh);
        diemDen = findViewById(R.id.diemDen);
        ngayDi = findViewById(R.id.ngayDi);
        soHanhKhach = findViewById(R.id.soHanhKhach);
        giaVe = findViewById(R.id.giaVe);

        findViewById(R.id.btnThemChuyenDi).setOnClickListener(view -> luuThem());
    }

    private void luuThem() {
        String tphuongTien = phuongTien.getText().toString(),
                tdiemKhoiHanh = diemKhoiHanh.getText().toString(),
                tdiemDen = diemDen.getText().toString(),
                tngayDi = ngayDi.getText().toString(),
                tsoHanhKhach = soHanhKhach.getText().toString(),
                tgiaVe = giaVe.getText().toString();

        SQLiteDB sq = new SQLiteDB(this, "chuyenDi.db", null, 1);
        try {
            sq.QueryData("CREATE TABLE IF NOT EXISTS chuyenDi(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "phuongTien TEXT," +
                    "diemKhoiHanh TEXT," +
                    "diemDen TEXT," +
                    "ngayDi TEXT," +
                    "soHanhKhach TEXT," +
                    "giaVe TEXT)");

            String sql = String.format(
                    "INSERT INTO chuyenDi(phuongTien, diemKhoiHanh, diemDen, ngayDi, soHanhKhach, giaVe) VALUES ('%s', '%s', '%s', '%s', '%s', '%s');",
                    tphuongTien,
                    tdiemKhoiHanh,
                    tdiemDen,
                    tngayDi,
                    tsoHanhKhach,
                    tgiaVe
            );
            sq.QueryData(sql);

            Toast.makeText(this, "Đã lưu chuyến đi", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi thêm dữ liệu", Toast.LENGTH_SHORT).show();
        } finally {
            sq.close();
            startActivity(new Intent(this, Main.class));
            finish();
        }
    }
}
