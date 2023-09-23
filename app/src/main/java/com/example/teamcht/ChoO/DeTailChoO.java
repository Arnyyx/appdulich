package com.example.teamcht.ChoO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.R;


public class DeTailChoO extends AppCompatActivity {

    private ImageView anhphong;
    private TextView mota, sophong;
    private Button datphong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuongdetailchoo);
        anhphong = findViewById(R.id.anhphong);
        mota = findViewById(R.id.motaphong);
        sophong = findViewById(R.id.sophong);
        datphong = findViewById(R.id.datphong);

        Intent intent = getIntent();
        mota.setText(intent.getStringExtra("Description"));
        sophong.setText(intent.getStringExtra("Name"));
        anhphong.setImageResource(intent.getIntExtra("Image", 0));

        datphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeTailChoO.this, DatPhongActivity.class);
                intent.putExtra("Name", sophong.getText().toString());
                intent.putExtra("Image", intent.getIntExtra("Image", 0));
                intent.putExtra("Description", mota.getText().toString());
                startActivity(intent);
            }
        });
    }
}




