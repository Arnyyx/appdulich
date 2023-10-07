package com.example.teamcht.ChoO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.HoatDongGiaiTri.HoatDongGiaiTri;
import com.example.teamcht.R;
import com.example.teamcht.TaiKhoan.QLTaiKhoan;
import com.example.teamcht.VanChuyen.QLChuyenDi;
import com.example.teamcht.VanChuyen.VanChuyen;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;

public class loaiphong extends AppCompatActivity {
    private ListView danhSachLoaiPhongListView;
    private ArrayList<String> loaiPhongList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuongmanhinhroom);

        danhSachLoaiPhongListView = findViewById(R.id.roomListView);

        loaiPhongList = new ArrayList<>(Arrays.asList("Gia đình", "Đơn", "Suite"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, loaiPhongList);
        danhSachLoaiPhongListView.setAdapter(adapter);

        danhSachLoaiPhongListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedLoaiPhong = loaiPhongList.get(position);

                Intent intent = new Intent(loaiphong.this, aaadanhsachphong.class);
                intent.putStringArrayListExtra("loaiPhongList", loaiPhongList);
                intent.putExtra("selectedLoaiPhong", selectedLoaiPhong);
                startActivity(intent);

            }
        });

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
                return true;
            } else if (itemId == R.id.place) {
                return true;
            } else if (itemId == R.id.entertainment) {
                startActivity(new Intent(this, HoatDongGiaiTri.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.transport) {
                startActivity(new Intent(this, VanChuyen.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }
}

