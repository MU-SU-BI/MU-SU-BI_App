package com.example.musubi.model.entity;

public abstract class Person {
    private final long id;
    private final String email;
    private final String name;
    private final Gender gender;
    private final int age;
    private final String nickname;
    private final String phone;
    private final String address;
    private double latitude;
    private double longitude;
    private String district;

    protected Person(long id, String email, String name, Gender gender, int age, String nickname, String phone, String address, String district) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.nickname = nickname;
        this.phone = phone;
        this.address = address;
        this.district = district;
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

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
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

    public String getDistrict() { return district; }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setDistrict(String district) { this.district = district; }
}
