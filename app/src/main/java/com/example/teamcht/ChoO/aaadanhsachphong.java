package com.example.teamcht.ChoO;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.R;

import java.util.ArrayList;

public class aaadanhsachphong extends AppCompatActivity {
    private ListView danhSachPhongListView;
    private ArrayList<room> phongList;
    private ArrayList<String> phong;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuongdanhsachphong);

        danhSachPhongListView = findViewById(R.id.danhSachPhongListView);

        Intent intent = getIntent();
        ArrayList<String> loaiPhongList = intent.getStringArrayListExtra("loaiPhongList");
        String selectedLoaiPhong = intent.getStringExtra("selectedLoaiPhong");


        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(db, 1, 2);
        for (String loaiPhong : loaiPhongList) {
            if (loaiPhong.equals(selectedLoaiPhong)) {

                if ("Gia đình".equals(loaiPhong)) {
                    room roomA1 = new room(1, "Gia đình",  "url_a1.jpg",100.0,  R.drawable.anhcuong1,"Phòng 1");
                    dbHelper.addRoom(roomA1);
                    room roomA2 = new room(2, "Gia đình", "url_a2.jpg",900.0,  R.drawable.anhcuong2,"Phòng 2");
                    dbHelper.addRoom(roomA2);
                    room roomA3 = new room(3, "Gia đình", "url_a3.jpg",800.0,  R.drawable.anhcuong3,"Phòng 3");
                    dbHelper.addRoom(roomA3);
                } else if ("Đơn".equals(loaiPhong)) {
                    room roomB1 = new room(11, "Đơn", "url_a4.jpg",700.0,  R.drawable.anhcuong4,"Phòng 11");
                    dbHelper.addRoom(roomB1);
                    room roomB2 = new room(22, "Đơn","url_a5.jpg",600.0,  R.drawable.anhcuong5,"Phòng 22");
                    dbHelper.addRoom(roomB2);
                } else if ("Suite".equals(loaiPhong)) {
                    room roomC1 = new room(111, "Suite","url_a6.jpg",500.0,  R.drawable.anhcuong6,"Phòng 111");
                    dbHelper.addRoom(roomC1);
                    room roomC2 = new room(222, "Suite", "url_a7.jpg",400.0,  R.drawable.anhcuong7,"Phòng 222");
                    dbHelper.addRoom(roomC2);
                    room roomC3 = new room(333, "Suite", "url_a8.jpg",300.0,  R.drawable.anhcuong8,"Phòng 333");
                    dbHelper.addRoom(roomC3);
                    room roomC4 = new room(444, "Suite", "url_a9.jpg",200.0,  R.drawable.anhcuong9,"Phòng 444");
                    dbHelper.addRoom(roomC4);
                }
            }
        }

        phongList = dbHelper.getAllRooms();
        phong = dbHelper.getRoom();
        RoomAdapter adapter = new RoomAdapter(this, phongList);
        danhSachPhongListView.setAdapter(adapter);


        danhSachPhongListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedRoom = phong.get(position);
               Intent intent = new Intent(aaadanhsachphong.this, aaachitietphong.class);
                intent.putExtra("selectedPhong", selectedRoom);
                intent.putExtra("selectedLoaiPhong", selectedLoaiPhong);

                startActivity(intent);

            }
        });
    }
}

