package com.example.musubi.model.dto;

import com.example.musubi.model.entity.Gender;
import com.example.musubi.model.entity.User;

public class UserDto {
    private final long id;
    private final String email;
    private final String password;
    private final String name;
    private final Gender gender;
    private final int age;
    private final String nickname;
    private final String phone;
    private final String address;

    public UserDto(long id, String email, String password, String name, Gender gender, int age, String nickname, String phone, String address) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.nickname = nickname;
        this.phone = phone;
        this.address = address;
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

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
