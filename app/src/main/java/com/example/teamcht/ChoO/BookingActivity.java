package com.example.teamcht.ChoO;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamcht.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BookingActivity extends AppCompatActivity {

    private EditText editTextName, editTextNumberOfGuests;
    private TextView editTextCheckInDate, editTextCheckOutDate, editTextBookingDate, editTextRoomType, editTextRoomNumber, editTextPriceall;
    private Button buttonSubmit;
    private DBHPbooking dbHelper;
    private int dolechngay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xacnhanbooking);
        findViewById(R.id.btnBack).setOnClickListener(view -> onBackPressed());
        dbHelper = new DBHPbooking(this);

        Intent intent = getIntent();
        String selectedLoaiPhong = intent.getStringExtra("selectedLoaiPhong");
        String selectedRoomNumber = getIntent().getStringExtra("selectedPhong");
        double priceall = getIntent().getDoubleExtra("priceall", 0.0);
        int soluongnguoi = getIntent().getIntExtra("soluongnguoi", 0);
        editTextName = findViewById(R.id.editTextName);
        editTextCheckInDate = findViewById(R.id.editTextCheckInDate);
        editTextCheckOutDate = findViewById(R.id.editTextCheckOutDate);
        editTextBookingDate = findViewById(R.id.editTextbookingdate);
        editTextRoomType = findViewById(R.id.editTextRoomType);
        editTextNumberOfGuests = findViewById(R.id.editTextNumberOfGuests);
        editTextRoomNumber = findViewById(R.id.editTextRoomNumber);
        editTextPriceall = findViewById(R.id.priceTextView);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        editTextRoomType.setText(" " + selectedLoaiPhong);
        editTextRoomNumber.setText(" " + selectedRoomNumber);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String currentDate = day + "/" + (month + 1) + "/" + year;
        editTextBookingDate.setText(currentDate);
        TextView priceTextView = findViewById(R.id.priceTextView);
        double giaBanDau = priceall;
        double giaChoMoiNguoi = 5.00;
        priceTextView.setText("Tổng tiền cần trả: $" + giaBanDau);


        editTextCheckInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDatePickerDialog(editTextCheckInDate);
            }
        });

        editTextCheckOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog1(editTextCheckOutDate);

            }
        });


        int soluong;

        if (editTextNumberOfGuests.getText().toString().isEmpty()) {
            soluong = soluongnguoi;
        } else {
            soluong = Integer.parseInt(editTextNumberOfGuests.getText().toString());

        }

        editTextNumberOfGuests.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void afterTextChanged(Editable editable) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date checkInDate = null;
                Date checkOutDate = null;

                try {
                    checkInDate = sdf.parse(editTextCheckInDate.getText().toString());
                    checkOutDate = sdf.parse(editTextCheckOutDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (checkInDate != null && checkOutDate != null) {
                    Calendar checkInCalendar = Calendar.getInstance();
                    checkInCalendar.setTime(checkInDate);

                    Calendar checkOutCalendar = Calendar.getInstance();
                    checkOutCalendar.setTime(checkOutDate);

                    dolechngay = checkOutCalendar.get(Calendar.DAY_OF_YEAR) - checkInCalendar.get(Calendar.DAY_OF_YEAR);
                }
                String sl = editable.toString();
                if (dolechngay == 0) {
                    dolechngay = 1;
                }
                if (sl.isEmpty()) {
                    priceTextView.setText("Tổng tiền cần trả: $" + (giaBanDau * dolechngay));
                } else {
                    int numberOfGuests;
                    if (sl == "") {
                        numberOfGuests = soluongnguoi;
                    } else {
                        numberOfGuests = Integer.parseInt(sl);
                    }
                    if (numberOfGuests <= soluongnguoi) {
                        priceTextView.setText("Tổng tiền cần trả: $" + (giaBanDau * dolechngay));
                    } else {
                        double giaTong;
                        if (numberOfGuests > soluongnguoi) {
                            giaTong = (giaBanDau + ((numberOfGuests - soluongnguoi) * giaChoMoiNguoi)) * dolechngay;
                        } else {
                            giaTong = giaBanDau;
                        }
                        priceTextView.setText("Tổng tiền cần trả: $" + giaTong);
                    }
                }
            }
        });


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = editTextName.getText().toString();
                String checkInDate1 = editTextCheckInDate.getText().toString();
                String checkOutDate1 = editTextCheckOutDate.getText().toString();

                if (name1.isEmpty() || checkInDate1.isEmpty() || checkOutDate1.isEmpty()) {
                    Toast.makeText(BookingActivity.this, "Vui lòng nhập đầy đủ thông tin và chọn ngày đến và ngày đi.", Toast.LENGTH_SHORT).show();
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date checkInDate = sdf.parse(checkInDate1);
                        Date checkOutDate = sdf.parse(checkOutDate1);

                        if (checkOutDate.before(checkInDate)) {
                            Toast.makeText(BookingActivity.this, "Ngày đi không thể nhỏ hơn ngày đến", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(BookingActivity.this, "Lỗi xảy ra khi xử lý ngày.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String name = editTextName.getText().toString();
                    String checkInDate = editTextCheckInDate.getText().toString();
                    String checkOutDate = editTextCheckOutDate.getText().toString();
                    String bookingDate = editTextBookingDate.getText().toString();
                    String roomType = editTextRoomType.getText().toString();
                    String roomNumber = editTextRoomNumber.getText().toString();
                    String priceall = editTextPriceall.getText().toString();


                    Booking booking = new Booking(name, checkInDate, checkOutDate, bookingDate, roomType, soluong, roomNumber, priceall);
                    long bookingId = dbHelper.addBooking(booking);

                    if (bookingId != -1) {
                        Toast.makeText(BookingActivity.this, "Thêm đặt phòng thành công!", Toast.LENGTH_SHORT).show();
                        clearFields();
                        Intent i = new Intent(BookingActivity.this, ChoO.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(BookingActivity.this, "Thêm đặt phòng thất bại.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


//private void create(String name,String checkindate, String checkoutdate, String bookingdate, String roomtype,int soluong, String sophong, String gia ){
//        dbHelper = new DBHPbooking(BookingActivity.this);
//        long bookingId = dbHelper.addBooking(name, checkindate, checkoutdate, bookingdate,roomtype,soluong,sophong, gia);
//        Booking booking= new Booking(name, checkindate, checkoutdate, bookingdate,roomtype,soluong,sophong, gia);
//        booking.setId(bookingId);
//        booking.setBookingDate(bookingdate);
//        booking.setName(name);
//        booking.setCheckInDate(checkindate);
//        booking.setCheckOutDate(checkoutdate);
//        booking.setRoomType(roomtype);
//        booking.setRoomNumber(sophong);
//        booking.setSoluong(soluong);
//        booking.setPriceall(gia);
//    Log.d("Booking ID", "ID của đặt phòng mới: " + booking.getId());
//
//}


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
                        editText.setText("" + selectedDate);
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
                        editText.setText("" + selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();

    }

    private void clearFields() {
        editTextName.setText("");
        editTextCheckInDate.setText("");
        editTextCheckOutDate.setText("");
        editTextBookingDate.setText("");
        editTextRoomType.setText("");
        editTextNumberOfGuests.setText("");
        editTextRoomNumber.setText("");
        editTextPriceall.setText("");
    }
}
