package com.example.teamcht.TaiKhoan;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.teamcht.Adapters.TaiKhoanAdapter;
import com.example.teamcht.ChoO.ChoO;
import com.example.teamcht.Database.DBTaiKhoan;
import com.example.teamcht.HoatDongGiaiTri.DanhSachDiaDiem;
import com.example.teamcht.Models.TaiKhoan;
import com.example.teamcht.R;
import com.example.teamcht.Utils.RecyclerTouchListener;
import com.example.teamcht.VanChuyen.VanChuyen;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class QLTaiKhoan extends AppCompatActivity {
    DBTaiKhoan db;
    List<TaiKhoan> taiKhoanList;
    TaiKhoanAdapter taiKhoanAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText editSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taikhoan);


        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeLayout);

        db = new DBTaiKhoan(this);
        taiKhoanList = db.getAll();
        taiKhoanAdapter = new TaiKhoanAdapter(this, taiKhoanList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(taiKhoanAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                showTaiKhoanDialog(taiKhoanList.get(position), position);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        editSearch = findViewById(R.id.editSearch);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int aft) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String strSearch = editSearch.getText().toString();
                TaiKhoanAdapter taiKhoanAdapterSearch = new TaiKhoanAdapter(QLTaiKhoan.this, search(strSearch, taiKhoanList));
                recyclerView.setAdapter(taiKhoanAdapterSearch);
            }
        });
        editSearch.setOnEditorActionListener((v, i, event) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                //Hide keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);
                editSearch.clearFocus();
                return true;
            }
            return false;
        });

        findViewById(R.id.btnAdd).setOnClickListener(view -> showTaiKhoanDialog(null, -1));

        findViewById(R.id.btnDangXuat).setOnClickListener(view -> {
            new AlertDialog.Builder(this)
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
                    .show();
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            editSearch.setText("");
            editSearch.clearFocus();
            //Hide keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);

            db = new DBTaiKhoan(this);
            taiKhoanList = db.getAll();
            taiKhoanAdapter = new TaiKhoanAdapter(this, taiKhoanList);
            recyclerView.setAdapter(taiKhoanAdapter);
            swipeRefreshLayout.setRefreshing(false);
        });

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
                startActivity(new Intent(this, ChoO.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.entertainment) {
                startActivity(new Intent(this, DanhSachDiaDiem.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.transport) {
                startActivity(new Intent(this, VanChuyen.class));
                overridePendingTransition(0, 0);
                finish();
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
                }
            }
            return listResult;
        }
    }

    private void showTaiKhoanDialog(TaiKhoan taiKhoan, int position) {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.taikhoan_dialog, null);
        final EditText editTaiKhoan = promptsView.findViewById(R.id.editTaiKhoan);
        final EditText editMatKhau = promptsView.findViewById(R.id.editMatKhau);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setCancelable(true)
                .setPositiveButton("Lưu", null)
                .setNeutralButton("Huỷ", (dialog, id) -> dialog.cancel());

        if (taiKhoan != null) {
            editTaiKhoan.setText(taiKhoan.getName());
            editMatKhau.setText(taiKhoan.getPassword());
            alertDialogBuilder.setNegativeButton("Xoá", (dialog, id) -> {
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_delete)
                        .setTitle("Xác nhận xoá")
                        .setMessage("Bạn có xác nhận muốn xoá tài khoản " + taiKhoan.getName() + " ?")
                        .setPositiveButton("Có", (d, which) -> {
                            if (taiKhoan.getStatus().matches("1")) {
                                Toast.makeText(this, "Không thể xoá tài khoản đang đăng nhập", Toast.LENGTH_SHORT).show();
                            } else {
                                delete(position, taiKhoan.getId());
                                Toast.makeText(this, "Đã xoá tài khoản " + taiKhoan.getName(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Không", null)
                        .setNeutralButton("Huỷ", null)
                        .show();
            });
        }

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
            String strTaiKhoan = editTaiKhoan.getText().toString(),
                    strMatKhau = editMatKhau.getText().toString();
            if (strTaiKhoan.matches("")) {
                editTaiKhoan.requestFocus();
                editTaiKhoan.setError(getString(R.string.TaiKhoanNull));
            } else if (strMatKhau.matches("")) {
                editMatKhau.requestFocus();
                editMatKhau.setError(getString(R.string.MatKhauNull));
            } else if (checkTaiKhoanDuplicate(taiKhoanList, strTaiKhoan, taiKhoan)) {
                editTaiKhoan.setError(getString(R.string.TaiKhoanExists));
                editTaiKhoan.requestFocus();
            } else if (taiKhoan == null) {
                create(editTaiKhoan.getText().toString(), editMatKhau.getText().toString());
                alertDialog.dismiss();
            } else {
                update(position, taiKhoan.getId(), editTaiKhoan.getText().toString(), editMatKhau.getText().toString(), taiKhoan.getStatus());
                alertDialog.dismiss();
            }
        });
    }

    private boolean checkTaiKhoanDuplicate(List<TaiKhoan> taiKhoanList, String strTaiKhoan, TaiKhoan taiKhoan) {
        List<TaiKhoan> taiKhoanListCheck = new ArrayList<>(taiKhoanList);
        if (taiKhoan != null) {
            taiKhoanListCheck.remove(taiKhoan);
        }
        for (TaiKhoan a : taiKhoanListCheck) {
            if (a.getName().matches(strTaiKhoan)) {
                return true;
            }
        }
        return false;
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

    private void update(int position, long id, String name, String pass, String status) {
        db.update(id, name, pass, status);
        TaiKhoan taikhoan = new TaiKhoan();
        taikhoan.setId(id);
        taikhoan.setName(name);
        taikhoan.setPassword(pass);
        taikhoan.setStatus(status);
        taiKhoanList.set(position, taikhoan);
        taiKhoanAdapter.notifyItemChanged(position);
    }

    private void delete(int position, long id) {
        db.delete(id);
        taiKhoanList.remove(position);
        taiKhoanAdapter.notifyItemRemoved(position);
    }
}
