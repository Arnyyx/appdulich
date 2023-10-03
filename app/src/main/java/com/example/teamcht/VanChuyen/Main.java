package com.example.teamcht.VanChuyen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.Adapters.ChuyenDiAdapter;
import com.example.teamcht.Database.SQLiteDB;
import com.example.teamcht.R;

import java.util.List;

public class Main extends AppCompatActivity {
    ListView lvChuyenDi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vanchuyen_admin_main);

        lvChuyenDi = findViewById(R.id.lvChuyenDi);

        findViewById(R.id.btnThemChuyenDi).setOnClickListener(view -> {
            startActivity(new Intent(this, ThemChuyenDi.class));
            finish();
        });

        findViewById(R.id.btnTimChuyenDi).setOnClickListener(view -> {
            timChuyenDi();
        });

        lvChuyenDi.setOnItemClickListener((adapterView, view, i, l) -> {
            //Todo sua, xoa
        });

        getData();
    }

    private void getData() {
        SQLiteDB sqLiteDB = new SQLiteDB(this, "chuyenDi.db", null, 1);
        List<ChuyenDi> chuyenDiList = sqLiteDB.getAllChuyenDi("SELECT * FROM chuyenDi");
        ChuyenDiAdapter chuyenDiAdapter = new ChuyenDiAdapter(this, chuyenDiList);
        lvChuyenDi.setAdapter(chuyenDiAdapter);
        lvChuyenDi.invalidateViews();
    }

    private void timChuyenDi() {
        EditText txtTimChuyenDi = findViewById(R.id.txtTimChuyenDi);
        String searchText = txtTimChuyenDi.getText().toString();

        if (searchText.matches("")) {
            getData();
        } else {
            SQLiteDB sqLiteDB = new SQLiteDB(this, "chuyenDi.db", null, 1);
            List<ChuyenDi> chuyenDiList = sqLiteDB.getAllChuyenDi("SELECT * FROM chuyenDi WHERE '" + searchText + "' IN (phuongTien, diemKhoiHanh, diemDen, ngayDi, soHanhKhach, giaVe)");
            ChuyenDiAdapter chuyenDiAdapter = new ChuyenDiAdapter(this, chuyenDiList);
            lvChuyenDi.setAdapter(chuyenDiAdapter);
            lvChuyenDi.invalidateViews();
        }

    }
}
