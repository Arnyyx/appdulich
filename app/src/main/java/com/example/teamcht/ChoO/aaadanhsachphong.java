package com.example.teamcht.ChoO;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.R;

import java.util.ArrayList;

public class aaadanhsachphong extends AppCompatActivity {
    private ListView danhSachPhongListView;
    private ArrayList<room> phongList;
    private DatabaseHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuongdanhsachphong);
        dbHelper = new DatabaseHelper(this);
        danhSachPhongListView = findViewById(R.id.danhSachPhongListView);
        Intent intent = getIntent();
        String selectedLoaiPhong = intent.getStringExtra("selectedLoaiPhong");
        addphongthem(dbHelper);
        EditText searchEditText = findViewById(R.id.searchdiadiemEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            private final Handler handler = new Handler();
            private Runnable runnable;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(runnable);
            }

            @Override
            public void afterTextChanged(Editable s) {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        String searchKeyword = s.toString().toLowerCase();
                        ArrayList<room> searchResults = dbHelper.timkiem(searchKeyword,selectedLoaiPhong);

                        if (searchResults.size() > 0) {
                            RoomAdapter adapter = new RoomAdapter(aaadanhsachphong.this, searchResults);
                            danhSachPhongListView.setAdapter(adapter);
                            Toast.makeText(aaadanhsachphong.this, "Có " +searchResults.size() + " phòng được tìm thấy", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(aaadanhsachphong.this, "Không tìm thấy phòng phù hợp", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                handler.postDelayed(runnable, 1000);
            }
        });




        phongList = dbHelper.getAllRooms(selectedLoaiPhong);
        RoomAdapter adapter = new RoomAdapter(this, phongList);
        danhSachPhongListView.setAdapter(adapter);
        danhSachPhongListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                room selectedRoom = phongList.get(position);
                Intent intent = new Intent(aaadanhsachphong.this, aaachitietphong.class);
                intent.putExtra("selectedPhong", selectedRoom.getRoomNumber());
                intent.putExtra("selectedLoaiPhong", selectedLoaiPhong);
                startActivity(intent);
            }
        });

    }
    private void timkiem(String location) {
        ArrayList<room> searchResults = new ArrayList<>();
        String searchKeyword = location.toLowerCase();

        for (room room : phongList) {
            String diadiem = room.getDiadiem().toLowerCase();
            if (diadiem.startsWith(searchKeyword)) {
                searchResults.add(room);
            }
        }

        if (searchResults.size() > 0) {
            RoomAdapter adapter = new RoomAdapter(this, searchResults);
            danhSachPhongListView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Không tìm thấy phòng phù hợp", Toast.LENGTH_SHORT).show();
        }
    }


    private void addphongthem(DatabaseHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String countQuery = "SELECT count(*) FROM Rooms";
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int rowCount = cursor.getInt(0);
        cursor.close();

        if (rowCount == 0) {
            room roomA1 = new room(1, "Gia đình", "url_a1.jpg", 100.0, R.drawable.anhcuong1, "Phòng 1", 4, "Hà Nội");
            dbHelper.addRoom(roomA1);
            room roomA2 = new room(2, "Gia đình", "url_a2.jpg", 200.0, R.drawable.anhcuong2, "Phòng 2", 4, "Hà Nội");
            dbHelper.addRoom(roomA2);
            room roomA3 = new room(3, "Gia đình", "url_a3.jpg", 300.0, R.drawable.anhcuong3, "Phòng 3", 4, "Hà Nội");
            dbHelper.addRoom(roomA3);
            room roomA4 = new room(4, "Gia đình", "url_a4.jpg", 400.0, R.drawable.anhcuong4, "Phòng 4", 4, "Hà Nội");
            dbHelper.addRoom(roomA4);
            room roomA5 = new room(5, "Gia đình", "url_a5.jpg", 500.0, R.drawable.anhcuong5, "Phòng 5", 5, "Hà Nội");
            dbHelper.addRoom(roomA5);
            room roomA6 = new room(6, "Gia đình", "url_a6.jpg", 600.0, R.drawable.anhcuong6, "Phòng 6", 5, "Hà Nội");
            dbHelper.addRoom(roomA6);
            room roomA7 = new room(7, "Gia đình", "url_a7.jpg", 700.0, R.drawable.anhcuong7, "Phòng 7", 5, "Hà Nội");
            dbHelper.addRoom(roomA7);
            room roomA8 = new room(8, "Gia đình", "url_a8.jpg", 800.0, R.drawable.anhcuong8, "Phòng 8", 6,"Thành phố Hồ Chí Minh");
            dbHelper.addRoom(roomA8);
            room roomA9 = new room(9, "Gia đình", "url_a9.jpg", 900.0, R.drawable.anhcuong9, "Phòng 9", 6,"Thành phố Hồ Chí Minh");
            dbHelper.addRoom(roomA9);
            room roomA10 = new room(10, "Gia đình", "url_a10.jpg", 1000.0, R.drawable.anhcuong10, "Phòng 10", 6,"Thành phố Hồ Chí Minh");
            dbHelper.addRoom(roomA10);
            room roomA11 = new room(11, "Gia đình", "url_a11.jpg", 1100.0, R.drawable.anhcuong1, "Phòng 11", 6,"Thành phố Hồ Chí Minh");
            dbHelper.addRoom(roomA11);
            room roomA12 = new room(12, "Gia đình", "url_a12.jpg", 1200.0, R.drawable.anhcuong2, "Phòng 12", 5,"Thành phố Hồ Chí Minh");
            dbHelper.addRoom(roomA12);
            room roomA13 = new room(13, "Gia đình", "url_a13.jpg", 1300.0, R.drawable.anhcuong3, "Phòng 13", 5,"Thành phố Hồ Chí Minh");
            dbHelper.addRoom(roomA13);
            room roomA14 = new room(14, "Gia đình", "url_a14.jpg", 1400.0, R.drawable.anhcuong4, "Phòng 14", 3,"Thành phố Hồ Chí Minh");
            dbHelper.addRoom(roomA14);
            room roomA15 = new room(15, "Gia đình", "url_a15.jpg", 1500.0, R.drawable.anhcuong5, "Phòng 15", 3,"Thành phố Hồ Chí Minh");
            dbHelper.addRoom(roomA15);
            room roomB1 = new room(16, "Đơn", "url_b1.jpg", 1600.0, R.drawable.anhcuong6, "Phòng 16", 1, "Hà Nội");
            dbHelper.addRoom(roomB1);
            room roomB2 = new room(17, "Đơn", "url_b2.jpg", 1700.0, R.drawable.anhcuong7, "Phòng 17", 1, "Hà Nội");
            dbHelper.addRoom(roomB2);
            room roomB3 = new room(18, "Đơn", "url_b3.jpg", 1800.0, R.drawable.anhcuong8, "Phòng 18", 1, "Hà Nội");
            dbHelper.addRoom(roomB3);
            room roomB4 = new room(19, "Đơn", "url_b4.jpg", 1900.0, R.drawable.anhcuong9, "Phòng 19", 1, "Hà Nội");
            dbHelper.addRoom(roomB4);
            room roomB5 = new room(20, "Đơn", "url_b5.jpg", 2000.0, R.drawable.anhcuong2, "Phòng 20", 1, "Hà Nội");
            dbHelper.addRoom(roomB5);
            room roomB6 = new room(21, "Đơn", "url_b6.jpg", 2100.0, R.drawable.anhcuong1, "Phòng 21", 1, "Hà Nội");
            dbHelper.addRoom(roomB6);
            room roomB7 = new room(22, "Đơn", "url_b7.jpg", 2200.0, R.drawable.anhcuong2, "Phòng 22", 1,"Thành phố Hồ Chí Minh");
            dbHelper.addRoom(roomB7);
            room roomB8 = new room(23, "Đơn", "url_b8.jpg", 2300.0, R.drawable.anhcuong3, "Phòng 23", 1,"Thành phố Hồ Chí Minh");
            dbHelper.addRoom(roomB8);
            room roomB9 = new room(24, "Đơn", "url_b9.jpg", 2400.0, R.drawable.anhcuong4, "Phòng 24", 2,"Thành phố Hồ Chí Minh");
            dbHelper.addRoom(roomB9);
            room roomB10 = new room(25, "Đơn", "url_b10.jpg", 2500.0, R.drawable.anhcuong5, "Phòng 25", 2,"Thành phố Hồ Chí Minh");
            dbHelper.addRoom(roomB10);
            room roomB11 = new room(26, "Đơn", "url_b11.jpg", 2600.0, R.drawable.anhcuong6, "Phòng 26", 2,"Thành phố Hồ Chí Minh");
            dbHelper.addRoom(roomB11);
            room roomB12 = new room(27, "Đơn", "url_b12.jpg", 2700.0, R.drawable.anhcuong7, "Phòng 27", 2,"Thành phố Hồ Chí Minh");
            dbHelper.addRoom(roomB12);
            room roomB13 = new room(28, "Đơn", "url_b13.jpg", 2800.0, R.drawable.anhcuong8, "Phòng 28", 2,"Đà Nẵng");
            dbHelper.addRoom(roomB13);
            room roomB14 = new room(29, "Đơn", "url_b14.jpg", 2900.0, R.drawable.anhcuong9, "Phòng 29", 2,"Đà Nẵng");
            dbHelper.addRoom(roomB14);
            room roomB15 = new room(30, "Đơn", "url_b15.jpg", 3000.0, R.drawable.anhcuong10, "Phòng 30", 2,"Đà Nẵng");
            dbHelper.addRoom(roomB15);

            room roomC1 = new room(31, "Suite", "url_c1.jpg", 3100.0, R.drawable.anhcuong3, "Phòng 31", 4, "Hà Nội");
            dbHelper.addRoom(roomC1);
            room roomC2 = new room(32, "Suite", "url_c2.jpg", 3200.0, R.drawable.anhcuong2, "Phòng 32", 4, "Hà Nội");
            dbHelper.addRoom(roomC2);
            room roomC3 = new room(33, "Suite", "url_c3.jpg", 3300.0, R.drawable.anhcuong3, "Phòng 33", 4, "Hà Nội");
            dbHelper.addRoom(roomC3);
            room roomC4 = new room(34, "Suite", "url_c4.jpg", 3400.0, R.drawable.anhcuong4, "Phòng 34", 4, "Hà Nội");
            dbHelper.addRoom(roomC4);
            room roomC5 = new room(35, "Suite", "url_c5.jpg", 3500.0, R.drawable.anhcuong5, "Phòng 35", 4, "Hà Nội");
            dbHelper.addRoom(roomC5);
            room roomC6 = new room(36, "Suite", "url_c6.jpg", 3600.0, R.drawable.anhcuong6, "Phòng 36", 4, "Hà Nội");
            dbHelper.addRoom(roomC6);
            room roomC7 = new room(37, "Suite", "url_c7.jpg", 3700.0, R.drawable.anhcuong7, "Phòng 37", 4,"Đà Nẵng");
            dbHelper.addRoom(roomC7);
            room roomC8 = new room(38, "Suite", "url_c8.jpg", 3800.0, R.drawable.anhcuong8, "Phòng 38", 5,"Đà Nẵng");
            dbHelper.addRoom(roomC8);
            room roomC9 = new room(39, "Suite", "url_c9.jpg", 3900.0, R.drawable.anhcuong9, "Phòng 39", 5,"Đà Nẵng");
            dbHelper.addRoom(roomC9);
            room roomC10 = new room(40, "Suite", "url_c10.jpg", 4000.0, R.drawable.anhcuong10, "Phòng 40", 5,"Đà Nẵng");
            dbHelper.addRoom(roomC10);
            room roomC11 = new room(41, "Suite", "url_c11.jpg", 4100.0, R.drawable.anhcuong1, "Phòng 41", 5,"Đà Nẵng");
            dbHelper.addRoom(roomC11);
        }
    }
}

