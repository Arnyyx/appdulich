package com.example.teamcht.ChoO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teamcht.R;

import java.util.ArrayList;

public class RoomAdapter extends ArrayAdapter<room> {
    public RoomAdapter(Context context, ArrayList<room> rooms) {
        super(context, 0, rooms);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        room room = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.customroom, parent, false);
        }

        TextView roomNumberTextView = convertView.findViewById(R.id.roomNumberTextView);
        TextView descriptionTextView = convertView.findViewById(R.id.descriptionTextView);
        TextView priceTextView = convertView.findViewById(R.id.priceTextView);
        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView soluongnguoitrongphong = convertView.findViewById(R.id.soluongTextView);

        TextView diadiem = convertView.findViewById(R.id.diadiemTextView);


        roomNumberTextView.setText(room.getRoomNumber());
        descriptionTextView.setText("Mô tả:\n" + room.getDescription());
        priceTextView.setText(String.valueOf(room.getPrice()));
        imageView.setImageResource(room.getImageUrl());
        soluongnguoitrongphong.setText("Phòng " + room.getSonguoitrongphong() + " người");
        diadiem.setText("Địa điểm: " + room.getDiadiem());


        return convertView;
    }
}
