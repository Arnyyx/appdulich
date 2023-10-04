package com.example.teamcht.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.teamcht.R;
import com.example.teamcht.VanChuyen.ChuyenDi;

import java.util.List;

public class ChuyenDiAdapter extends BaseAdapter {

    private Context context;
    private List<ChuyenDi> chuyenDiList;

    public ChuyenDiAdapter(Context context, List<ChuyenDi> chuyenDiList) {
        this.context = context;
        this.chuyenDiList = chuyenDiList;
    }

    @Override
    public int getCount() {
        return chuyenDiList.size();
    }

    @Override
    public Object getItem(int position) {
        return chuyenDiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Tạo view mới nếu view hiện tại không tồn tại
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.vanchuyen_itemchuyendi, parent, false);
        }

        // Lấy đối tượng ChuyenDi
        ChuyenDi chuyenDi = chuyenDiList.get(position);

        // Đặt giá trị cho các view trong layout

        TextView tvPhuongTien = convertView.findViewById(R.id.tvPhuongTien);
        tvPhuongTien.setText(chuyenDi.getPhuongTien());

        TextView tvDiemKhoiHanh = convertView.findViewById(R.id.tvDiemKhoiHanh);
        tvDiemKhoiHanh.setText(chuyenDi.getDiemKhoiHanh());

        TextView tvDiemDen = convertView.findViewById(R.id.tvDiemDen);
        tvDiemDen.setText(chuyenDi.getDiemDen());

        TextView tvNgayDi = convertView.findViewById(R.id.tvNgayDi);
        tvNgayDi.setText(chuyenDi.getNgayDi());

        TextView tvSoHanhKhach = convertView.findViewById(R.id.tvSoHanhKhach);
        tvSoHanhKhach.setText(chuyenDi.getSoHanhKhach());

        TextView tvGiaVe = convertView.findViewById(R.id.tvGiaVe);
        tvGiaVe.setText(chuyenDi.getGiaVe());

        return convertView;
    }
}
