package com.example.teamcht.ChoO;
public class room {
    private int id;
    private String RoomType;
    private String description;
    private double price;
    private int imageUrl;
    private String roomNumber;
   // private int songuoitrongphong;

    public room(int id, String roomType, String description, double price, int imageUrl, String roomNumber) {
        this.id = id;
        RoomType = roomType;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.roomNumber = roomNumber;
       // this.songuoitrongphong = songuoitrongphong;
    }

    public room(String description, double price, int imageUrl, String roomNumber) {
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.roomNumber = roomNumber;
      //  this.songuoitrongphong = songuoitrongphong;
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

    public double getPrice() {
        return price;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

//    public int getSonguoitrongphong() {
//        return songuoitrongphong;
//    }
}
