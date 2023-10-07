package com.example.teamcht.ChoO;
public class room {
    private int id;
    private String RoomType;
    private String description;
    private double price;
    private int imageUrl;
    private String roomNumber;
    private int songuoitrongphong;
    private String diadiem;

    public room(int id, String roomType, String description, double price, int imageUrl, String roomNumber,int songuoitrongphong,String diadiem) {

        this.id = id;
        RoomType = roomType;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.roomNumber = roomNumber;
        this.songuoitrongphong = songuoitrongphong;
        this.diadiem=diadiem;
    }

    public room(String description, double price, int imageUrl, String roomNumber,int songuoitrongphong,String diadiem) {

        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.roomNumber = roomNumber;

        this.songuoitrongphong = songuoitrongphong;
        this.diadiem=diadiem;


    }

    public int getId() {
        return id;
    }

    public String getRoomType() {
        return RoomType;
    }

    public String getDescription() {
        return description;
    }

    public String getDiadiem() {
        return diadiem;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoomType(String roomType) {
        RoomType = roomType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setSonguoitrongphong(int songuoitrongphong) {
        this.songuoitrongphong = songuoitrongphong;
    }

    public void setDiadiem(String diadiem) {
        this.diadiem = diadiem;
    }

    public double getPrice() {
        return price;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getSonguoitrongphong() {
        return songuoitrongphong;
    }
}