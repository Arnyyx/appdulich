package com.example.teamcht.VanChuyen;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.example.teamcht.R;
import com.example.teamcht.Utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class MuaVe extends AppCompatActivity {
    DBChuyenDi db;
    List<ChuyenDi> chuyenDiList;
    ChuyenDiAdapter chuyenDiAdapter;
    RecyclerView recyclerView;
    EditText editSearch;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vanchuyen_muave);

        db = new DBChuyenDi(this);
        chuyenDiList = db.getAll();
        chuyenDiAdapter = new ChuyenDiAdapter(this, chuyenDiList);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        editSearch = findViewById(R.id.editSearch);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MuaVe.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(chuyenDiAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                showMuaVeDialog(chuyenDiList.get(position), position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        findViewById(R.id.btnBack).setOnClickListener(view -> onBackPressed());

        editSearch.setOnClickListener(view -> showTimVeDialog(chuyenDiList));

        swipeRefreshLayout.setOnRefreshListener(() -> {
            editSearch.clearFocus();
            //Hide keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);

            db = new DBChuyenDi(this);
            chuyenDiList = db.getAll();
            chuyenDiAdapter = new ChuyenDiAdapter(this, chuyenDiList);
            recyclerView.setAdapter(chuyenDiAdapter);
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void showMuaVeDialog(ChuyenDi chuyenDi, int position) {
        //Todo mua ve
        LayoutInflater li = LayoutInflater.from(MuaVe.this);
        View promptsView = li.inflate(R.layout.vanchuyen_muave_dialog_thanhtoan, null);

        EditText editPhuongTien = promptsView.findViewById(R.id.editPhuongTien);
        EditText editDiemKhoiHanh = promptsView.findViewById(R.id.editDiemKhoiHanh);
        EditText editDiemDen = promptsView.findViewById(R.id.editDiemDen);
        EditText editNgayDi = promptsView.findViewById(R.id.editNgayDi);
        EditText editSoHanhKhach = promptsView.findViewById(R.id.editSoHanhKhach);
        EditText editGiaVe = promptsView.findViewById(R.id.editGiaVe);
        EditText editTongThanhToan = promptsView.findViewById(R.id.editTongThanhToan);

        editPhuongTien.setText(chuyenDi.getPhuongTien());
        editDiemKhoiHanh.setText(chuyenDi.getDiemKhoiHanh());
        editDiemDen.setText(chuyenDi.getDiemDen());
        editNgayDi.setText(chuyenDi.getNgayDi());
        editGiaVe.setText(chuyenDi.getGiaVe());

        editSoHanhKhach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int soHK = editSoHanhKhach.getText().toString().isEmpty() ? 1 : Integer.parseInt(editSoHanhKhach.getText().toString());
                int giaVe = Integer.parseInt(editGiaVe.getText().toString());
                editTongThanhToan.setText(String.valueOf(soHK * giaVe));
                //Todo mua vé, trừ đi số hành khách trong vé đã đặt
            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder
                .setPositiveButton("Mua vé", null)
                .setNeutralButton("Huỷ", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
            if (editSoHanhKhach.getText().toString().matches("")) {
                editSoHanhKhach.setError("Hãy nhập số hành khách");
            } else {
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_plane_ticket)
                        .setTitle("Xác nhận mua vé")
                        .setMessage("Bạn có xác nhận mua vé từ " + editDiemKhoiHanh.getText().toString()
                                + " đến " + editDiemDen.getText().toString()
                                + " với giá " + editTongThanhToan.getText().toString() + " ?")
                        .setPositiveButton("Có", (dialogInterface1, i1) -> {
                            Toast.makeText(this, "Đã mua vé", Toast.LENGTH_SHORT).show();
                            update(chuyenDi, editSoHanhKhach.getText().toString());
                        })
                        .setNegativeButton("Không", null)
                        .setNeutralButton("Huỷ", null)
                        .show();
                alertDialog.dismiss();
            }

        });
    }

    private void showTimVeDialog(List<ChuyenDi> chuyenDiList) {
        LayoutInflater li = LayoutInflater.from(MuaVe.this);
        View promptsView = li.inflate(R.layout.vanchuyen_muave_dialog_timve, null);

        final AutoCompleteTextView acPhuongTien = promptsView.findViewById(R.id.acPhuongTien);
        final AutoCompleteTextView acDiemKhoiHanh = promptsView.findViewById(R.id.acDiemKhoiHanh);
        final AutoCompleteTextView acDiemDen = promptsView.findViewById(R.id.acDiemDen);
        final EditText editNgayDi = promptsView.findViewById(R.id.editNgayDi);
        final AutoCompleteTextView acSoHanhKhach = promptsView.findViewById(R.id.acSoHanhKhach);
        final AutoCompleteTextView acGiaVe = promptsView.findViewById(R.id.acGiaVe);

        promptsView.findViewById(R.id.btnDatePicker).setOnClickListener(view -> {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR), month = c.get(Calendar.MONTH), day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view1, year1, monthOfYear, dayOfMonth) -> editNgayDi.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
            datePickerDialog.show();
        });

        List<String> phuongTienList = new ArrayList<>();
        List<String> diemKhoiHanhList = new ArrayList<>();
        List<String> diemDenList = new ArrayList<>();
        List<String> soHanhKhachList = new ArrayList<>();
        List<String> giaVeList = new ArrayList<>();


        for (ChuyenDi chuyenDi : chuyenDiList) {
            phuongTienList.add(chuyenDi.getPhuongTien());
            diemKhoiHanhList.add(chuyenDi.getDiemKhoiHanh());
            diemDenList.add(chuyenDi.getDiemDen());
            soHanhKhachList.add(chuyenDi.getSoHanhKhach());
            giaVeList.add(chuyenDi.getGiaVe());
        }

        //Loại bỏ những đối tượng trùng nhau
        phuongTienList = phuongTienList.stream().distinct().collect(Collectors.toList());
        diemKhoiHanhList = diemKhoiHanhList.stream().distinct().collect(Collectors.toList());
        diemDenList = diemDenList.stream().distinct().collect(Collectors.toList());
        soHanhKhachList = soHanhKhachList.stream().distinct().collect(Collectors.toList());
        giaVeList = giaVeList.stream().distinct().collect(Collectors.toList());

        acPhuongTien.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, phuongTienList));
        acDiemKhoiHanh.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, diemKhoiHanhList));
        acDiemDen.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, diemDenList));
        acSoHanhKhach.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, soHanhKhachList));
        acGiaVe.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, giaVeList));

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("Tìm",
                        (dialog, id) -> {
                            ChuyenDiAdapter chuyenDiAdapterSearch = new ChuyenDiAdapter(this,
                                    search(
                                            acPhuongTien.getText().toString(),
                                            acDiemKhoiHanh.getText().toString(),
                                            acDiemDen.getText().toString(),
                                            editNgayDi.getText().toString(),
                                            acSoHanhKhach.getText().toString(),
                                            acGiaVe.getText().toString()
                                            , chuyenDiList
                                    ));
                            recyclerView.setAdapter(chuyenDiAdapterSearch);
                        })
                .setNegativeButton("Huỷ",
                        (dialog, id) -> {
                            dialog.cancel();
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private List<ChuyenDi> search(String phuongTien, String diemKhoiHanh, String diemDen, String ngayDi, String soHanhKhach, String giaVe, List<ChuyenDi> chuyenDiList) {
        phuongTien = phuongTien.isEmpty() ? "``" : phuongTien.toLowerCase();
        diemKhoiHanh = diemKhoiHanh.isEmpty() ? "``" : diemKhoiHanh.toLowerCase();
        diemDen = diemDen.isEmpty() ? "``" : diemDen.toLowerCase();
        ngayDi = ngayDi.isEmpty() ? "``" : ngayDi.toLowerCase();
        soHanhKhach = soHanhKhach.isEmpty() ? "``" : soHanhKhach.toLowerCase();
        giaVe = giaVe.isEmpty() ? "``" : giaVe.toLowerCase();

        if (phuongTien.matches("") &&
                diemKhoiHanh.matches("") &&
                diemDen.matches("") &&
                ngayDi.matches("") &&
                soHanhKhach.matches("") &&
                giaVe.matches("")) {
            return chuyenDiList;
        } else {
            List<ChuyenDi> listResult = new ArrayList<>();
            for (ChuyenDi a : chuyenDiList) {
                if (a.getPhuongTien().toLowerCase().contains(phuongTien) ||
                        a.getDiemKhoiHanh().toLowerCase().contains(diemKhoiHanh) ||
                        a.getDiemDen().toLowerCase().contains(diemDen) ||
                        a.getNgayDi().contains(ngayDi) ||
                        a.getSoHanhKhach().toLowerCase().contains(soHanhKhach) ||
                        a.getGiaVe().toLowerCase().contains(giaVe)) {
                    listResult.add(a);
                }
            }
            return listResult;
        }
    }

    private void update(ChuyenDi chuyenDi, String soHanhKhach) {
        int soHanhKhachNew = Integer.parseInt(chuyenDi.getSoHanhKhach()) - Integer.parseInt(soHanhKhach);
        db.update(chuyenDi.getId(),
                chuyenDi.getPhuongTien(),
                chuyenDi.getDiemKhoiHanh(),
                chuyenDi.getDiemDen(),
                chuyenDi.getNgayDi(),
                String.valueOf(soHanhKhachNew),
                chuyenDi.getGiaVe());
        db = new DBChuyenDi(this);
        chuyenDiList = db.getAll();
        chuyenDiAdapter = new ChuyenDiAdapter(this, chuyenDiList);
        recyclerView.setAdapter(chuyenDiAdapter);
    }
}
