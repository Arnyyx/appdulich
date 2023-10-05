package com.example.teamcht.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.teamcht.R;
import com.example.teamcht.Models.ChuyenDi;

import java.util.List;

public class ChuyenDiAdapter extends RecyclerView.Adapter<ChuyenDiAdapter.MyViewHolder> {

    private Context context;
    private List<ChuyenDi> listChuyenDi;

    public ChuyenDiAdapter(Context context, List<ChuyenDi> listChuyenDi) {
        this.context = context;
        this.listChuyenDi = listChuyenDi;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vanchuyen_chuyendi_itemchuyendi, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ChuyenDi chuyenDi = listChuyenDi.get(position);
        holder.tvPhuongTien.setText(chuyenDi.getPhuongTien());
        holder.tvDiemKhoiHanh.setText(chuyenDi.getDiemKhoiHanh());
        holder.tvDiemDen.setText(chuyenDi.getDiemDen());
        holder.tvSoHanhKhach.setText(chuyenDi.getSoHanhKhach());
        holder.tvNgayDi.setText(chuyenDi.getNgayDi());
        holder.tvGiaVe.setText(chuyenDi.getGiaVe());
    }

    @Override
    public int getItemCount() {
        return listChuyenDi.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPhuongTien, tvDiemKhoiHanh, tvDiemDen, tvSoHanhKhach, tvNgayDi, tvGiaVe;

        public MyViewHolder(View view) {
            super(view);
            tvPhuongTien = view.findViewById(R.id.tvPhuongTien);
            tvDiemKhoiHanh = view.findViewById(R.id.tvDiemKhoiHanh);
            tvDiemDen = view.findViewById(R.id.tvDiemDen);
            tvSoHanhKhach = view.findViewById(R.id.tvSoHanhKhach);
            tvNgayDi = view.findViewById(R.id.tvNgayDi);
            tvGiaVe = view.findViewById(R.id.tvGiaVe);
        }
    }
}
