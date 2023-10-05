package com.example.teamcht.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.teamcht.Models.TaiKhoan;
import com.example.teamcht.R;

import java.util.List;

public class TaiKhoanAdapter extends RecyclerView.Adapter<TaiKhoanAdapter.MyViewHolder> {
    private Context context;
    private List<TaiKhoan> listTaiKhoan;

    public TaiKhoanAdapter(Context context, List<TaiKhoan> listTaiKhoan) {
        this.context = context;
        this.listTaiKhoan = listTaiKhoan;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.taikhoan_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TaiKhoan taikhoan = listTaiKhoan.get(position);
        holder.tvID.setText(String.valueOf(taikhoan.getId()));
        holder.tvName.setText(taikhoan.getName());
        holder.tvPass.setText(taikhoan.getPassword());
        holder.tvStatus.setText(taikhoan.getStatus());
    }

    @Override
    public int getItemCount() {
        return listTaiKhoan.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvID, tvName, tvPass, tvStatus;

        public MyViewHolder(View view) {
            super(view);
            tvID = view.findViewById(R.id.tvID);
            tvName = view.findViewById(R.id.tvTaiKhoan);
            tvPass = view.findViewById(R.id.tvMatKhau);
            tvStatus = view.findViewById(R.id.tvTrangThai);
        }

    }

}
