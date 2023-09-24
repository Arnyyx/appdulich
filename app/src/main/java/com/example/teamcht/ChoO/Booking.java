package com.example.teamcht.ChoO;

public class Booking {
    private String roomName;
    private int roomImageResource;
    private String roomDescription;
    private String guestInfo;

    public Booking(String roomName, int roomImageResource, String roomDescription, String guestInfo) {
        this.roomName = roomName;
        this.roomImageResource = roomImageResource;
        this.roomDescription = roomDescription;
        this.guestInfo = guestInfo;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getRoomImageResource() {
        return roomImageResource;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public String getGuestInfo() {
        return guestInfo;
    }
}

