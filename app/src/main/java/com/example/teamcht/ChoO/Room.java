package com.example.teamcht.ChoO;

public class Room {
    private String roomName;
    private int roomImageResource;

    public Room(String roomName, int roomImageResource) {
        this.roomName = roomName;
        this.roomImageResource = roomImageResource;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getRoomImageResource() {
        return roomImageResource;
    }
}
