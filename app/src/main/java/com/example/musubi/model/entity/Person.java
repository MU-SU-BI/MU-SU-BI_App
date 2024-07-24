package com.example.musubi.model.entity;

public abstract class Person {
    private final long id;
    private final String email;
    private final String name;
    private final String nickname;
    private final String phone;
    private final String address;
    private final Gender gender;
    private final int age;
    private double latitude;
    private double longitude;

    protected Person(long id, String email, String name, String nickname, String phone, String address, Gender gender, int age) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.age = age;
    }

    public long getId() {
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

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
}
