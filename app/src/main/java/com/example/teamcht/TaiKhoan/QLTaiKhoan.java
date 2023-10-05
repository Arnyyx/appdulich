package com.example.teamcht.TaiKhoan;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamcht.Adapters.ChuyenDiAdapter;
import com.example.teamcht.Adapters.TaiKhoanAdapter;
import com.example.teamcht.ChoO.loaiphong;
import com.example.teamcht.Database.DBTaiKhoan;
import com.example.teamcht.HoatDongGiaiTri.HoatDongGiaiTri;
import com.example.teamcht.Models.ChuyenDi;
import com.example.teamcht.Models.TaiKhoan;
import com.example.teamcht.R;
import com.example.teamcht.Utils.RecyclerTouchListener;
import com.example.teamcht.VanChuyen.QLChuyenDi;
import com.example.teamcht.VanChuyen.VanChuyen;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
            }
        }));

        findViewById(R.id.ic_search).setOnClickListener(view -> {
            EditText editSearch = findViewById(R.id.editSearch);
            String strSearch = editSearch.getText().toString();
            taiKhoanAdapter = new TaiKhoanAdapter(this, search(strSearch, taiKhoanList));
            recyclerView.setAdapter(taiKhoanAdapter);
        });
        findViewById(R.id.btnAdd).setOnClickListener(view -> showTaiKhoanDialog(null, -1));

        findViewById(R.id.btnDangXuat).setOnClickListener(view -> new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_logout)
                .setTitle("Đăng xuất")
                .setMessage("Bạn có muốn đăng xuất?")
                .setPositiveButton("Có", (dialog, which) -> {
                    DBTaiKhoan db = new DBTaiKhoan(QLTaiKhoan.this);
                    List<TaiKhoan> taiKhoanList = db.getAll();
                    int i = 0;
                    for (TaiKhoan a : taiKhoanList) {
                        if (a.getStatus().matches("1")) {
                            db.update(a.getId(), a.getName(), a.getPassword(), "");
                            startActivity(new Intent(QLTaiKhoan.this, Splash.class));
                            finish();
                            break;
                        }
                        i++;
                    }
                    if (i == taiKhoanList.size()) {
                        Toast.makeText(QLTaiKhoan.this, "Lỗi đăng xuất", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Không", null)
                .show());
        navigate();
    }

    private void navigate() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.account);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.account) {
                return true;
            } else if (itemId == R.id.place) {
                startActivity(new Intent(getApplicationContext(), loaiphong.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.entertainment) {
                startActivity(new Intent(getApplicationContext(), HoatDongGiaiTri.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.transport) {
                startActivity(new Intent(getApplicationContext(), VanChuyen.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }

    private List<TaiKhoan> search(String strSearch, List<TaiKhoan> taiKhoanList) {
        strSearch = strSearch.toLowerCase();
        if (strSearch.matches("")) {
            return taiKhoanList;
        } else {
            List<TaiKhoan> listResult = new ArrayList<>();
            for (TaiKhoan taikhoan : taiKhoanList) {
                if (taikhoan.getName().toLowerCase().contains(strSearch) ||
                        taikhoan.getPassword().toLowerCase().contains(strSearch) ||
                        taikhoan.getStatus().toLowerCase().contains(strSearch)) {
                    listResult.add(taikhoan);
                    break;
                }
            }
            return listResult;
        }
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
                .setPositiveButton("Lưu",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (taiKhoan == null) {
                                    create(editTaiKhoan.getText().toString(), editMatKhau.getText().toString());
                                } else {
                                    update(position, taiKhoan.getId(), editTaiKhoan.getText().toString(), editMatKhau.getText().toString());
                                }
                            }
                        })
                .setNegativeButton("Xoá",
                        (dialog, id) -> {
                            delete(position, taiKhoan.getId());
                        })
                .setNeutralButton("Huỷ bỏ",
                        (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
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

    private void delete(int position, long id) {
        db.delete(id);
        taiKhoanList.remove(position);
        taiKhoanAdapter.notifyItemRemoved(position);
    }
}
