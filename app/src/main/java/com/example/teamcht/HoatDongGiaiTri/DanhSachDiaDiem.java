package com.example.teamcht.HoatDongGiaiTri;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.ChoO.ChoO;
import com.example.teamcht.R;
import com.example.teamcht.TaiKhoan.QLTaiKhoan;
import com.example.teamcht.VanChuyen.VanChuyen;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class DanhSachDiaDiem extends AppCompatActivity {
    private Button cartButton;
    private GridView placeGridView;
    private ArrayList<Place> placeList;
    private CustomAdapter placeAdapter;
    private DatabaseHelper dbHelper;
    private EditText searchEditText;
    private TextView searchLabel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoatdonggiaitri_listsp_form);

        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(db, 1, 2);
        placeGridView = findViewById(R.id.placeList);

        // Tạo danh sách sản phẩm và Adapter
        placeList = new ArrayList<>();
        Place place1 = new Place(R.drawable.sunworld, "Sun World", 165000, "Ai đến Đà Nẵng mà không đi du lịch Bà Nà Hills thì thật là thiếu sót đấy! Bạn hãy cầm trên tay vé Bà Nà Hills và dành nguyên 1 ngày để khám phá điểm đến ấn tượng này nhé. Với vé Bà Nà Hills này, bạn sẽ có cơ hội chiêm ngưỡng quang cảnh hùng vĩ xung quanh khi hệ thống cáp treo dần đưa bạn lên đỉnh núi.");
        dbHelper.addPlace(place1);
        Place place2 = new Place(R.drawable.asiapark, "Asia Park", 95000, "Bạn đang tìm kiếm một nơi kết hợp trò chơi và chương trình giải trí đẳng cấp thế giới với sự pha trộn hài hoà của văn hoá Á Đông, thì Asia Park Đà Nẵng quả là điểm đến cực kì phù hợp với bạn. Bạn sẽ có cảm giác như đi qua một châu Á thu nhỏ qua những công trình kiến trúc đến từ 10 quốc gia châu Á.");
        dbHelper.addPlace(place2);
        Place place3 = new Place(R.drawable.vinwonders, "VinWonders", 543200, "Bạn muốn có một tuần trăng mật lãng mạn? Bạn thích chơi các trò cảm giác mạnh? Hay đang tìm một nơi thư giãn cho cả gia đình? VinWonders Nha Trang sẽ đáp ứng mọi nhu cầu của bạn. Cầm vé VinWonders Nha Trang trên tay và bắt đầu chuyến đi bằng việc ngồi cáp treo vượt biển dài nhất thế giới, ngắm trọn nét thơ mộng của vịnh Nha Trang, một trong 29 vịnh biển đẹp nhất thế giới.");
        dbHelper.addPlace(place3);
        Place place4 = new Place(R.drawable.zoodoo, "ZooDoo", 130000, "Nằm trong khu rừng xanh rờn của núi Lạc Dương, vườn thú ZooDoo rộng 16 ha này tạo nên một không gian nơi nơi con người và động vật có thể tự do tương tác với nhau trong thiên nhiên độc đáo của Đà Lạt với cây thông, hoa, và không khí trong lành.");
        dbHelper.addPlace(place4);
        Place place5 = new Place(R.drawable.lumiere, "Lumiere", 135000, "Trải nghiệm nghệ thuật ánh sáng ứng dụng kỹ thuật cao đầu tiên tại Việt Nam và khám phá địa điểm sống ảo mới tinh của Đà Lạt tại Vườn ánh sáng Lumiere! ");
        dbHelper.addPlace(place5);
        Place place6 = new Place(R.drawable.waterpark, "Mikazuki Water", 200000, "Trải nghiệm công viên nước nóng trong nhà theo phong cách Nhật Bản hot nhất Đà Nẵng—Mikazuki Water Park 365. Với rất nhiều hồ bơi rộng rãi, hệ thống nước nóng quanh năm, khách có thể vui chơi mà bất chấp thời tiết nắng mưa, rét hay nóng.");
        dbHelper.addPlace(place6);
        Place place7 = new Place(R.drawable.hontam, "Hòn Tằm", 400000, "Tự thưởng cho mình một ngày thư giãn tại MerPerle Hòn Tằm Resort! Cảm thấy được nuông chiều khi bạn tận hưởng dịch vụ tàu cao tốc khứ hồi, đồ uống chào mừng và tất cả các loại tiện ích để biến một ngày đi biển trở nên hoàn hảo.");
        dbHelper.addPlace(place7);
        Place place8 = new Place(R.drawable.iresort, "I-Resort", 160000, "Đi chơi cuối tuần chẳng cần hầm hố. Đi chơi cuối tuần chẳng cần lịch trình dày đặc. Cuối tuần đôi khi chỉ là ngồi xuống, thở thật sâu, và kết nối với nội tâm của mình. ");
        dbHelper.addPlace(place8);
        placeList = dbHelper.getdata_diadiem();
        placeAdapter = new CustomAdapter(this, placeList);

        // Thiết lập Adapter cho GridView
        placeGridView.setAdapter(placeAdapter);
        placeAdapter.notifyDataSetChanged();

        cartButton = findViewById(R.id.cartButton);

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachDiaDiem.this, CartActivity.class);
                startActivity(intent);
            }
        });

        // Khởi tạo các thành phần liên quan đến tìm kiếm
        searchEditText = findViewById(R.id.searchEditText);

        // Xử lý sự kiện khi EditText thay đổi
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần xử lý trước sự thay đổi của EditText
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Thực hiện tìm kiếm dựa trên nội dung của EditText và cập nhật danh sách địa điểm
                performSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần xử lý sau sự thay đổi của EditText
            }
        });

        navigate();
    }

    private void navigate() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.entertainment);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.account) {
                startActivity(new Intent(this, QLTaiKhoan.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.place) {
                startActivity(new Intent(this, ChoO.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.entertainment) {
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

    private void performSearch(String query) {
        ArrayList<Place> filteredList = new ArrayList<>();
        for (Place place : placeList) {
            if (place.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(place);
            }
        }
        placeAdapter = new CustomAdapter(DanhSachDiaDiem.this, filteredList);
        placeGridView.setAdapter(placeAdapter);
        placeAdapter.notifyDataSetChanged();
    }
}
