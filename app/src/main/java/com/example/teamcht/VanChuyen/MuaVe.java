package com.example.teamcht.VanChuyen;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

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

        findViewById(R.id.btnBack).setOnClickListener(view -> onBackPressed());

        editSearch.setOnClickListener(view -> {
            showMuaVeDialog(chuyenDiList);
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            editSearch.clearFocus();
            //Hide keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);

            recyclerView.setAdapter(chuyenDiAdapter);
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void showMuaVeDialog(List<ChuyenDi> chuyenDiList) {
        LayoutInflater li = LayoutInflater.from(MuaVe.this);
        View promptsView = li.inflate(R.layout.vanchuyen_muave_dialog, null);

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
}
