package com.example.teamcht.VanChuyen;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamcht.Adapters.ChuyenDiAdapter;
import com.example.teamcht.Database.DBChuyenDi;
import com.example.teamcht.Models.ChuyenDi;
import com.example.teamcht.Models.TaiKhoan;
import com.example.teamcht.R;
import com.example.teamcht.Utils.RecyclerTouchListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class QLChuyenDi extends AppCompatActivity {
    DBChuyenDi db;
    List<ChuyenDi> chuyenDiList;
    ChuyenDiAdapter chuyenDiAdapter;
    RecyclerView recyclerView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vanchuyen_chuyendi_qlchuyendi);


        fab = findViewById(R.id.btnAdd);
        db = new DBChuyenDi(this);
        chuyenDiList = new ArrayList<>();
        chuyenDiList = db.getAll();
        chuyenDiAdapter = new ChuyenDiAdapter(this, chuyenDiList);
        recyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(chuyenDiAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                showChuyenDiDialog(chuyenDiList.get(position), position);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        fab.setOnClickListener(view -> {
            showChuyenDiDialog(null, -1);
        });

    }


    private void showChuyenDiDialog(ChuyenDi chuyendi, int position) {
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.vanchuyen_chuyendi_dialog, null);
        final EditText editPhuongTien = promptsView.findViewById(R.id.editPhuongTien);
        final EditText editDiemKhoiHanh = promptsView.findViewById(R.id.editDiemKhoiHanh);
        final EditText editDiemDen = promptsView.findViewById(R.id.editDiemDen);
        final EditText editNgayDi = promptsView.findViewById(R.id.editNgayDi);
        final EditText editSoHanhKhach = promptsView.findViewById(R.id.editSoHanhKhach);
        final EditText editGiaVe = promptsView.findViewById(R.id.editGiaVe);
        promptsView.findViewById(R.id.btnDatePicker).setOnClickListener(view -> {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR),
                    month = c.get(Calendar.MONTH),
                    day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view1, year1, monthOfYear, dayOfMonth) -> {
                editNgayDi.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
            }, year, month, day);
            datePickerDialog.show();
        });

        if (chuyendi != null) {
            editPhuongTien.setText(chuyendi.getPhuongTien());
            editDiemKhoiHanh.setText(chuyendi.getDiemKhoiHanh());
            editDiemDen.setText(chuyendi.getDiemDen());
            editNgayDi.setText(chuyendi.getNgayDi());
            editSoHanhKhach.setText(chuyendi.getSoHanhKhach());
            editGiaVe.setText(chuyendi.getGiaVe());
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);


        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("Lưu",
                        (dialog, id) -> {
                            if (chuyendi == null) {
                                create(
                                        editPhuongTien.getText().toString(),
                                        editDiemKhoiHanh.getText().toString(),
                                        editDiemDen.getText().toString(),
                                        editNgayDi.getText().toString(),
                                        editSoHanhKhach.getText().toString(),
                                        editGiaVe.getText().toString()
                                );
                            } else {
                                update(
                                        position, chuyendi.getId(),
                                        editPhuongTien.getText().toString(),
                                        editDiemKhoiHanh.getText().toString(),
                                        editDiemDen.getText().toString(),
                                        editNgayDi.getText().toString(),
                                        editSoHanhKhach.getText().toString(),
                                        editGiaVe.getText().toString()
                                );
                            }
                        })
                .setNegativeButton("Xoá",
                        (dialog, id) -> {
                            delete(position, chuyendi.getId());
                        })
                .setNeutralButton("Huỷ bỏ", ((dialogInterface, i) -> dialogInterface.cancel()));

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void create(String phuongtien, String diemkhoihanh, String diemden, String ngaydi, String sohanhkhah, String giave) {
        long id = db.create(phuongtien, diemkhoihanh, diemden, ngaydi, sohanhkhah, giave);
        ChuyenDi chuyenDi = new ChuyenDi();
        chuyenDi.setId(id);
        chuyenDi.setPhuongTien(phuongtien);
        chuyenDi.setDiemKhoiHanh(diemkhoihanh);
        chuyenDi.setDiemDen(diemden);
        chuyenDi.setNgayDi(ngaydi);
        chuyenDi.setSoHanhKhach(sohanhkhah);
        chuyenDi.setGiaVe(giave);
        chuyenDiList.add(0, chuyenDi);
        chuyenDiAdapter.notifyDataSetChanged();
    }

    private void update(int position, long id, String phuongtien, String diemkhoihanh, String diemden, String ngaydi, String sohanhkhah, String giave) {
        db.update(id, phuongtien, diemkhoihanh, diemden, ngaydi, sohanhkhah, giave);
        ChuyenDi chuyenDi = new ChuyenDi();
        chuyenDi.setId(id);
        chuyenDi.setPhuongTien(phuongtien);
        chuyenDi.setDiemKhoiHanh(diemkhoihanh);
        chuyenDi.setDiemDen(diemden);
        chuyenDi.setNgayDi(ngaydi);
        chuyenDi.setSoHanhKhach(sohanhkhah);
        chuyenDi.setGiaVe(giave);
        chuyenDiList.set(position, chuyenDi);
        chuyenDiAdapter.notifyItemChanged(position);
    }

    private void delete(int position, long id) {
        db.delete(id);
        chuyenDiList.remove(position);
        chuyenDiAdapter.notifyItemRemoved(position);
    }

}