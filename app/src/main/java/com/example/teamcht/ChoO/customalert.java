package com.example.teamcht.ChoO;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamcht.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class customalert {

    private Context context;
    private AlertDialog alertDialog;
    private EditText editTextName, editTextCheckIn, editTextCheckOut;
    private TextView editTextGuests;
    private DBHPbooking dbHelper;
    private Booking bookingToEdit;
    private BookingListAdapter bookingListAdapter;
    private boolean isDateValid = true;

    public customalert(Context context, DBHPbooking dbHelper, Booking bookingToEdit, BookingListAdapter bookingListAdapter) {
        this.context = context;
        this.dbHelper = dbHelper;
        this.bookingToEdit = bookingToEdit;
        this.bookingListAdapter = bookingListAdapter;
    }

    public void show() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.customdialogsuabooking, null);

        editTextName = dialogView.findViewById(R.id.editTextName1);
        editTextCheckIn = dialogView.findViewById(R.id.editTextCheckInDate1);
        editTextCheckOut = dialogView.findViewById(R.id.editTextCheckOutDate1);
        editTextGuests = dialogView.findViewById(R.id.editTextNumberOfGuests1);
        editTextName.setText("" + bookingToEdit.getName());
        editTextCheckIn.setText("" + bookingToEdit.getCheckInDate());
        editTextCheckOut.setText("" + bookingToEdit.getCheckOutDate());
        editTextGuests.setText("" + bookingToEdit.getsoluong());

        editTextCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(editTextCheckIn);
            }
        });

        editTextCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(editTextCheckOut);
            }
        });

        alertDialogBuilder.setView(dialogView);

        alertDialogBuilder.setPositiveButton("Lưu thay đổi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editTextName.getText().toString();
                String checkInText = editTextCheckIn.getText().toString();
                String checkOutText = editTextCheckOut.getText().toString();
                String guests = editTextGuests.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date checkInDate = null;
                Date checkOutDate = null;

                try {
                    checkInDate = sdf.parse(checkInText);
                    checkOutDate = sdf.parse(checkOutText);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (checkInDate != null && checkOutDate != null) {
                    if (checkOutDate.before(checkInDate)) {
                        Toast.makeText(context, "Ngày đi không thể nhỏ hơn ngày đến", Toast.LENGTH_SHORT).show();
                        isDateValid = false;
                    } else {
                        isDateValid = true;
                    }
                }

                if (isDateValid) {
                    String checkIn = "" + checkInText;
                    String checkOut = "" + checkOutText;

                    int checkupdate = dbHelper.updateBooking(bookingToEdit.getId(), name, checkIn, checkOut, guests);

                    if (checkupdate != -1) {
                        Toast.makeText(context, "Cập nhật đặt phòng thành công!", Toast.LENGTH_SHORT).show();
                        bookingListAdapter.updateBookingList(dbHelper.getAllBookings());
                    } else {
                        Toast.makeText(context, "Cập nhật đặt phòng thất bại.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        alertDialogBuilder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Hủy dialog
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showDatePickerDialog(final TextView editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        editText.setText("" + selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }
}
