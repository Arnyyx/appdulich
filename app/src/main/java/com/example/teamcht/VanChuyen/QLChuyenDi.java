package com.example.teamcht.VanChuyen;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
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
    EditText editSearch;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vanchuyen_chuyendi_qlchuyendi);

        db = new DBChuyenDi(this);
        chuyenDiList = db.getAll();
        chuyenDiAdapter = new ChuyenDiAdapter(this, chuyenDiList);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeLayout);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(chuyenDiAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                showChuyenDiDialog(chuyenDiList.get(position), position);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        findViewById(R.id.btnAdd).setOnClickListener(view -> showChuyenDiDialog(null, -1));

        findViewById(R.id.btnBack).setOnClickListener(view -> onBackPressed());

        editSearch = findViewById(R.id.editSearch);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String strSearch = editSearch.getText().toString();
                chuyenDiAdapter = new ChuyenDiAdapter(QLChuyenDi.this, search(strSearch, chuyenDiList));
                recyclerView.setAdapter(chuyenDiAdapter);
            }
        });
        editSearch.setOnEditorActionListener((v, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                //Hide keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);
                editSearch.clearFocus();
                return true;
            }
            return false;
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            editSearch.setText("");
            editSearch.clearFocus();
            //Hide keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);

            recyclerView.setAdapter(chuyenDiAdapter);
            swipeRefreshLayout.setRefreshing(false);
        });

    }

    private List<ChuyenDi> search(String strSearch, List<ChuyenDi> chuyenDiList) {
        strSearch = strSearch.toLowerCase();
        if (strSearch.matches("")) {
            return chuyenDiList;
        } else {
            List<ChuyenDi> listResult = new ArrayList<>();
            for (ChuyenDi chuyenDi : chuyenDiList) {
                if (chuyenDi.getPhuongTien().toLowerCase().contains(strSearch) ||
                        chuyenDi.getDiemKhoiHanh().toLowerCase().contains(strSearch) ||
                        chuyenDi.getDiemDen().toLowerCase().contains(strSearch) ||
                        chuyenDi.getSoHanhKhach().toLowerCase().contains(strSearch) ||
                        chuyenDi.getNgayDi().toLowerCase().contains(strSearch) ||
                        chuyenDi.getGiaVe().toLowerCase().contains(strSearch)) {
                    listResult.add(chuyenDi);
                }
            }
            return listResult;
        }
    }

    private void showChuyenDiDialog(ChuyenDi chuyendi, int position) {
        LayoutInflater li = LayoutInflater.from(QLChuyenDi.this);
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

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setCancelable(true)
                .setPositiveButton("Lưu", null)
                .setNeutralButton("Huỷ bỏ", ((dialogInterface, i) -> dialogInterface.cancel()));

        if (chuyendi != null) {
            editPhuongTien.setText(chuyendi.getPhuongTien());
            editDiemKhoiHanh.setText(chuyendi.getDiemKhoiHanh());
            editDiemDen.setText(chuyendi.getDiemDen());
            editNgayDi.setText(chuyendi.getNgayDi());
            editSoHanhKhach.setText(chuyendi.getSoHanhKhach());
            editGiaVe.setText(chuyendi.getGiaVe());

            alertDialogBuilder.setNegativeButton("Xoá", (dialog, id) -> {
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_delete)
                        .setTitle("Xác nhận xoá")
                        .setMessage("Bạn có xác nhận muốn xoá chuyến đi từ " + chuyendi.getDiemKhoiHanh() + " đến " + chuyendi.getDiemDen() + " ?")
                        .setPositiveButton("Có", (d, which) -> {
                            delete(position, chuyendi.getId());
                        })
                        .setNegativeButton("Không", null)
                        .show();
            });
        }

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
            //Todo check null
            //Todo check duplicate
            if (chuyendi == null) {
                create(
                        editPhuongTien.getText().toString(),
                        editDiemKhoiHanh.getText().toString(),
                        editDiemDen.getText().toString(),
                        editNgayDi.getText().toString(),
                        editSoHanhKhach.getText().toString(),
                        editGiaVe.getText().toString()
                );
                alertDialog.dismiss();
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
                alertDialog.dismiss();
            }
        });
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