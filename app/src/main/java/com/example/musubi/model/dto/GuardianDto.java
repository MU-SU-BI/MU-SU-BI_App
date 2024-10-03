package com.example.musubi.model.dto;

import com.example.musubi.model.entity.Gender;

public class GuardianDto {
    private final long userId;
    private final String email;
    private final String name;
    private final Gender sex;
    private final int age;
    private final String nickname;
    private final String phoneNumber;
    private final String homeAddress;
    private final String district;

    public GuardianDto(long userId, String email, String name, Gender sex, int age, String nickname, String phoneNumber, String homeAddress, String district) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.homeAddress = homeAddress;
        this.district = district;
    }

    public long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Gender getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getDistrict() {
        return district;
    }
}
