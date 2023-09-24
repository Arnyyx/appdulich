package com.example.teamcht.ChoO;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.R;

import java.util.ArrayList;
import java.util.List;

public class ChoO extends AppCompatActivity {

    private GridView gridView;
    private RoomAdapter roomAdapter;
    private List<Room> roomList;
    private Button lichsu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choo);
        gridView = findViewById(R.id.danhsachphong);
        roomList = new ArrayList<>();
        roomList.add(new Room("Phòng 1","Depk",R.drawable.anhcuong1));
        roomList.add(new Room("Phòng 2","Depk", R.drawable.anhcuong2));
        roomList.add(new Room("Phòng 3","Depk",R.drawable.anhcuong2));
        roomList.add(new Room("Phòng 4","Depk",R.drawable.anhcuong3));
        roomList.add(new Room("Phòng 5","Depk", R.drawable.anhcuong4));
        roomList.add(new Room("Phòng 6","Depk", R.drawable.anhcuong5));
        roomList.add(new Room("Phòng 7","Depk", R.drawable.anhcuong6));
        roomList.add(new Room("Phòng 8","Depk", R.drawable.anhcuong7));
        roomList.add(new Room("Phòng 9","Depk", R.drawable.anhcuong9));
        roomList.add(new Room("Phòng 10","Depk", R.drawable.anhcuong10));

        roomAdapter = new RoomAdapter(this, roomList);
        gridView.setAdapter(roomAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Room selected = roomList.get(position);
                Intent intent = new Intent(ChoO.this, DeTailChoO.class);
                intent.putExtra("Name", selected.getRoomName());
                intent.putExtra("Description", selected.getDescription());
                intent.putExtra("Image", selected.getRoomImageResource());
                startActivity(intent);
            }
        });
        lichsu= findViewById(R.id.btnlichsudatphong);
        lichsu.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ChoO.this, LichSuDatPhongActivity.class);
            startActivity(intent);
        }
    });
    }




}

