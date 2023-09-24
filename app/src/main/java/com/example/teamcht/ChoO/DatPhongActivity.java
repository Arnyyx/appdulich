package com.example.teamcht.ChoO;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.teamcht.R;
import java.util.Calendar;

public class DatPhongActivity extends AppCompatActivity {

    private EditText edtNgayNhanPhong;
    private EditText edtNgayTraPhong;
    private EditText edtTen;
    private EditText edtSoDienThoai;
    private EditText edtSoLuongNguoi;
    private EditText edtGhiChu;
    private Button btnDatPhong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_phong);

        edtNgayNhanPhong = findViewById(R.id.edtNgayNhanPhong);
        edtNgayTraPhong = findViewById(R.id.edtNgayTraPhong);
        edtTen = findViewById(R.id.edtTen);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai);
        edtSoLuongNguoi = findViewById(R.id.edtSoLuongNguoi);
        edtGhiChu = findViewById(R.id.edtGhiChu);
        btnDatPhong = findViewById(R.id.btnDatPhong);

        edtNgayNhanPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(edtNgayNhanPhong);
            }
        });

        edtNgayTraPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(edtNgayTraPhong);
            }
        });

        btnDatPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edtTen.getText().toString();
                String soDienThoai = edtSoDienThoai.getText().toString();
                String ngayNhanPhong = edtNgayNhanPhong.getText().toString();
                String ngayTraPhong = edtNgayTraPhong.getText().toString();
                int soLuongNguoi = Integer.parseInt(edtSoLuongNguoi.getText().toString());
                String ghiChu = edtGhiChu.getText().toString();
                String roomNumber = getIntent().getStringExtra("Name");

                testsql sql = new testsql(DatPhongActivity.this, "booking.db", null, 1);
                sql.QueryData("CREATE TABLE IF NOT EXISTS 'booking' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'ten' TEXT, 'soDienThoai' TEXT, 'ngayNhanPhong' TEXT, 'ngayTraPhong' TEXT, 'soNguoi' TEXT, 'ghiChu' TEXT, 'soPhong' TEXT)");
                try {
                    sql.QueryData("INSERT INTO booking (ten, soDienThoai, ngayNhanPhong, ngayTraPhong, soNguoi, ghiChu, soPhong) VALUES ('" + ten + "', '" + soDienThoai + "', '" + ngayNhanPhong + "', '" + ngayTraPhong + "', '" + soLuongNguoi + "', '" + ghiChu + "', '" + roomNumber + "');");
                    Intent intent = new Intent(DatPhongActivity.this, ChoO.class);
                    startActivity(intent);
                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(DatPhongActivity.this, "Lỗi khi đặt phòng: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showDatePickerDialog(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        editText.setText(selectedDate);
                    }
                },
                year, month, day
        );

        datePickerDialog.show();
    }
}

