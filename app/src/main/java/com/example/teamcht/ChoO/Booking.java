package com.example.teamcht.ChoO;
public class Booking {
    private long id;

    private String name;
    private String checkInDate;
    private String checkOutDate;
    private String bookingDate;
    private String roomType;
    private int soluong;
    private String roomNumber;
    private String priceall;

    public Booking() {

    }

    public Booking(String name, String checkInDate, String checkOutDate, String bookingDate, String roomType, int soluong, String roomNumber, String priceall) {

        this.name = name;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingDate = bookingDate;
        this.roomType = roomType;
        this.soluong = soluong;
        this.roomNumber = roomNumber;
        this.priceall = priceall;
    }

    public long getId() {
        return id;
    }
    public int getId() {
        return id;
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

    public void setId(long id) {this.id = id;}

    public void setName(String name) {
        this.name = name;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setPriceall(String priceall) {
        this.priceall = priceall;
    }

}