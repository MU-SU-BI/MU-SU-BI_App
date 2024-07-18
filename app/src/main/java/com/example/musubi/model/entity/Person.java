package com.example.musubi.model.entity;

public abstract class Person {
    private final int id;
    private final String email;
    private final String name;
    private final String nickname;
    private final String phone;
    private final String address;
    private double latitude;
    private double longitude;

    protected Person(int id, String email, String name, String nickname, String phone, String address) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
