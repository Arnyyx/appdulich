package com.example.teamcht.VanChuyen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.R;
import com.example.teamcht.VanChuyen.Admin.Main;
import com.example.teamcht.VanChuyen.Admin.ThemChuyenDi;

public class VanChuyen extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vanchuyen);

        startActivity(new Intent(this, Main.class));
        finish();

        //Todo splash screen
        //Todo Kiểm tra user hay admin
    }
}
