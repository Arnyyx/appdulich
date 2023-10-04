package com.example.teamcht.ChoO;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.R;

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
    }
}

