package com.example.teamcht.ChoO;

public class Booking {
    private String name;
    private String checkInDate;
    private String checkOutDate;
    private String bookingDate;
    private String roomType;
    private int soluong;
    private String roomNumber;
    private String priceall;

    public Booking(String name, String checkInDate, String checkOutDate, String bookingDate, String roomType, int soluong, String roomNumber,String priceall) {
        this.name = name;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingDate = bookingDate;
        this.roomType = roomType;
        this.soluong = soluong;
        this.roomNumber = roomNumber;
        this.priceall=priceall;
    }

    public String getName() {
        return name;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getsoluong() {
        return soluong;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
    public String getPriceall() {
        return priceall;
    }
}
