package com.example.musubi.model.dto;

import com.example.musubi.model.entity.Gender;
import com.example.musubi.model.entity.User;

public class UserDto {
    private final long id;
    private final String email;
    private final String password;
    private final String name;
    private final Gender sex;
    private final int age;
    private final String nickname;
    private final String phoneNumber;
    private final String homeAddress;

    public UserDto(long id, String email, String password, String name, Gender sex, int age, String nickname, String phoneNumber, String homeAddress) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.homeAddress = homeAddress;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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
}
