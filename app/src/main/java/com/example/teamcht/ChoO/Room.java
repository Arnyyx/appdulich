package com.example.teamcht.ChoO;

public class Room {
    private String roomName;
    private String Description;
    private int roomImageResource;

    public Room(String roomName, String description, int roomImageResource) {
        this.roomName = roomName;
        Description = description;
        this.roomImageResource = roomImageResource;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getDescription() {
        return Description;
    }

    public int getRoomImageResource() {
        return roomImageResource;
    }
}
