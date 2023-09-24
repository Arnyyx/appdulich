package com.example.teamcht.ChoO;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.teamcht.R;
import java.util.ArrayList;
import java.util.List;

public class LichSuDatPhongActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> lichSuDatPhongList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lichsu);
        listView = findViewById(R.id.danhsachlichsuphong);
        lichSuDatPhongList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lichSuDatPhongList);

        listView.setAdapter(adapter);

        loadLichSuDatPhong();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedLichSu = lichSuDatPhongList.get(position);

                Toast.makeText(LichSuDatPhongActivity.this, selectedLichSu, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadLichSuDatPhong() {
        testsql sql = new testsql(this, "booking.db", null, 1);
        Cursor cursor = sql.getData("SELECT * FROM booking");

        while (cursor.moveToNext()) {
            String ten = cursor.getString(cursor.getColumnIndex("ten"));
            String ngayNhanPhong = cursor.getString(cursor.getColumnIndex("ngayNhanPhong"));
            String ngayTraPhong = cursor.getString(cursor.getColumnIndex("ngayTraPhong"));
            String soPhong = cursor.getString(cursor.getColumnIndex("soPhong"));

            String lichSuItem = "Tên: " + ten + "\nNgày nhận phòng: " + ngayNhanPhong + "\nNgày trả phòng: " + ngayTraPhong + "\nSố phòng: " + soPhong;

            lichSuDatPhongList.add(lichSuItem);
        }

        adapter.notifyDataSetChanged();
    }
}
