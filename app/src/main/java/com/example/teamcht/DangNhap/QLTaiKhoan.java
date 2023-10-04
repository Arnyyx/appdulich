package com.example.teamcht.DangNhap;


import android.app.AlertDialog;
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

import com.example.teamcht.Adapters.TaiKhoanAdapter;
import com.example.teamcht.Database.DBTaiKhoan;
import com.example.teamcht.R;
import com.example.teamcht.Utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class QLTaiKhoan extends AppCompatActivity {
    DBTaiKhoan db;
    List<TaiKhoan> taiKhoanList;
    TaiKhoanAdapter taiKhoanAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taikhoan_main);

        db = new DBTaiKhoan(this);
        taiKhoanList = new ArrayList<>();
        taiKhoanList = db.getAll();
        taiKhoanAdapter = new TaiKhoanAdapter(this, taiKhoanList);
        recyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(taiKhoanAdapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                showTaiKhoanDialog(taiKhoanList.get(position), position);
            }

            @Override
            public void onLongClick(View view, int position) {
                showDeleteDialog(taiKhoanList.get(position), position);
            }
        }));

    }

    private void showTaiKhoanDialog(TaiKhoan taiKhoan, int position) {
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.taikhoan_dialog, null);
        final EditText editTaiKhoan = promptsView.findViewById(R.id.editTaiKhoan);
        final EditText editMatKhau = promptsView.findViewById(R.id.editMatKhau);
        if (taiKhoan != null) {
            editTaiKhoan.setText(taiKhoan.getName());
            editMatKhau.setText(taiKhoan.getPassword());
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("XÁC NHẬN",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (taiKhoan == null) {
                                    create(editTaiKhoan.getText().toString(), editMatKhau.getText().toString());
                                } else {
                                    update(position, taiKhoan.getId(), editTaiKhoan.getText().toString(), editMatKhau.getText().toString());
                                }
                            }
                        })
                .setNegativeButton("HỦY BỎ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

    }

    private void showDeleteDialog(TaiKhoan taiKhoan, int position) {
    }

    private void create(String name, String pass) {
        long id = db.create(name, pass, "");
        TaiKhoan taikhoan = new TaiKhoan();
        taikhoan.setId(id);
        taikhoan.setName(name);
        taikhoan.setPassword(pass);
        taiKhoanList.add(0, taikhoan);
        taiKhoanAdapter.notifyDataSetChanged();
    }

    private void update(int position, long id, String name, String pass) {
        db.update(id, name, pass, "");
        TaiKhoan taikhoan = new TaiKhoan();
        taikhoan.setId(id);
        taikhoan.setName(name);
        taikhoan.setPassword(pass);
        taiKhoanList.set(position, taikhoan);
        taiKhoanAdapter.notifyItemChanged(position);
    }
}
