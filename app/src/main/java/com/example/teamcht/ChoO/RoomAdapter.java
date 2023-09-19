package com.example.teamcht.ChoO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teamcht.R;

import java.util.List;

public class RoomAdapter extends BaseAdapter {

    private Context context;
    private List<Room> roomList;

    public RoomAdapter(Context context, List<Room> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    @Override
    public int getCount() {
        return roomList.size();
    }

    @Override
    public Object getItem(int position) {
        return roomList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.customgriddanhsachcuacuong,null);
        }

        ImageView roomImage = view.findViewById(R.id.roomImageView);
        TextView roomName = view.findViewById(R.id.roomNameTextView);

        Room room = roomList.get(position);
        roomImage.setImageResource(room.getRoomImageResource());
        roomName.setText(room.getRoomName());

        return view;
    }
}
