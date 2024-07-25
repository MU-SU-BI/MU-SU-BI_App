package com.example.musubi.model.entity;

import com.example.musubi.model.dto.UserDto;

public class User extends Person{
    private String failureLevel;
    private Person guardian;

    private static User instance;
    public static synchronized User getInstance() {
        if(null == instance){
            instance = new User(-1, null, null, null, 0, null, null, null);
        }
        return instance;
    }

    private User(long id, String email, String name, Gender gender, int age, String nickname, String phone, String address) {
        super(id, email, name, gender, age, nickname, phone, address);
    }

    public void initUser (UserDto dto) {
       instance = new User(dto.getId(), dto.getEmail(), dto.getName(), dto.getSex(), dto.getAge(), dto.getNickname(), dto.getPhoneNumber(), dto.getHomeAddress());
    }

    public Person getGuardian() {
        return guardian;
    }

    public String getFailureLevel() {
        return failureLevel;
    }

    public void setGuardian(Person guardian) {
        this.guardian = guardian;
    }

}
