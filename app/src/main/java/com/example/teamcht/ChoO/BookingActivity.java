package com.example.teamcht.ChoO;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BookingActivity extends AppCompatActivity {

    private EditText editTextName,  editTextNumberOfGuests;
    private TextView editTextRoomType,editTextRoomNumber, editTextCheckInDate, editTextCheckOutDate,editTextbookingdate,editTextprice;
    private Button buttonSubmit;
    private ListView listView;
    private ArrayList<Booking> bookingList;
    private BookingListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xacnhanbooking);
        Intent intent = getIntent();
        String selectedLoaiPhong = intent.getStringExtra("selectedLoaiPhong");
        String selectedRoomNumber = getIntent().getStringExtra("selectedPhong");
        double priceall = getIntent().getDoubleExtra("priceall", 0.0);
        int soluongnguoi = getIntent().getIntExtra("soluongnguoi", 0);
        editTextName = findViewById(R.id.editTextName);
        editTextCheckInDate = findViewById(R.id.editTextCheckInDate);
        editTextCheckOutDate = findViewById(R.id.editTextCheckOutDate);
        editTextRoomType = findViewById(R.id.editTextRoomType);
        editTextNumberOfGuests = findViewById(R.id.editTextNumberOfGuests);
        editTextRoomNumber = findViewById(R.id.editTextRoomNumber);
        editTextbookingdate = findViewById(R.id.editTextbookingdate);
        editTextprice = findViewById(R.id.priceTextView);


        editTextRoomType.setText( selectedLoaiPhong);
        editTextRoomNumber.setText( selectedRoomNumber);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String currentDate = day + "/" + (month + 1) + "/" + year;
        editTextbookingdate.setText("Ngày đặt: "+currentDate);
        editTextCheckInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDatePickerDialog(editTextCheckInDate);
            }
        });


        TextView priceTextView = findViewById(R.id.priceTextView);

        double giaBanDau = priceall;
        double giaChoMoiNguoi = 5.00;
        priceTextView.setText("Tổng tiền cần trả: $" + giaBanDau+"/Đêm");

        editTextNumberOfGuests.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void afterTextChanged(Editable editable) {
                String sl = editable.toString();
                String checkIn = editTextCheckInDate.getText().toString();
                String checkOut = editTextCheckOutDate.getText().toString();

                    if (sl.isEmpty()) {
                        priceTextView.setText("Tổng tiền cần trả: $" + giaBanDau+"/Đêm");
                    } else {
                        int numberOfGuests = Integer.parseInt(sl);
                        if (numberOfGuests <= soluongnguoi) {
                            priceTextView.setText("Tổng tiền cần trả: $" + giaBanDau+"/Đêm");
                        } else {
                            double giaTong;
                            if (numberOfGuests > soluongnguoi) {
                                giaTong = (giaBanDau + ((numberOfGuests - soluongnguoi) * giaChoMoiNguoi));
                            } else {
                                giaTong = giaBanDau;
                            }
                            priceTextView.setText("Tổng tiền cần trả: $" + giaTong+"/Đêm");
                        }
                    }


            }



        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String checkInDate = editTextCheckInDate.getText().toString();
                String checkOutDate = editTextCheckOutDate.getText().toString();

                if (name.isEmpty() || checkInDate.isEmpty() || checkOutDate.isEmpty()) {
                    Toast.makeText(BookingActivity.this, "Vui lòng nhập đầy đủ thông tin và chọn ngày đến và ngày đi.", Toast.LENGTH_SHORT).show();
                } else {
                    String roomType = editTextRoomType.getText().toString();
                    int soluong = Integer.parseInt(editTextNumberOfGuests.getText().toString());
                    String roomNumber = editTextRoomNumber.getText().toString();
                    String priceALL = editTextprice.getText().toString();
                    String bookingdate = editTextbookingdate.getText().toString();
                    Booking booking = new Booking(name, checkInDate, checkOutDate, bookingdate, roomType, soluong, roomNumber,priceALL);

                    DatabaseHelper dbHelper = new DatabaseHelper(BookingActivity.this);
                    dbHelper.addBooking(booking);
                    editTextName.setText("");
                    editTextbookingdate.setText("");
                    editTextprice.setText("");
                    editTextCheckInDate.setText("");
                    editTextCheckOutDate.setText("");
                    editTextNumberOfGuests.setText("");
                    Intent in = new Intent(BookingActivity.this, lichsudatphong.class);
                    startActivity(in);
                }
            }
        });


        editTextCheckOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog1(editTextCheckOutDate);
            }
        });





    }



    private void showDatePickerDialog(final TextView editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        editText.setText("Ngày đến: "+selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }
    private void showDatePickerDialog1(final TextView editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        editText.setText("Ngày đi: "+selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

}
