package com.example.teamcht.ChoO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.R;

import java.util.ArrayList;
import java.util.List;

public class ChoO extends AppCompatActivity {

    private GridView gridView;
    private RoomAdapter roomAdapter;
    private List<Room> roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choo);

        gridView = findViewById(R.id.danhsachphong);

        roomList = new ArrayList<>();
        roomList.add(new Room("Phòng 1", R.drawable.anhcuong1));
        roomList.add(new Room("Phòng 2", R.drawable.anhcuong2));

        roomAdapter = new RoomAdapter(this, roomList);
        gridView.setAdapter(roomAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Room selected = roomList.get(position);
                Intent intent = new Intent(ChoO.this, DeTailChoO.class);
                intent.putExtra("Name", selected.getRoomName());
                intent.putExtra("Image", selected.getRoomImageResource());
                startActivity(intent);
            }
        });
    }
}

