package com.example.musubi.model.entity;

import com.example.musubi.model.dto.GuardianDto;
import com.example.musubi.model.dto.UserDto;

public class User extends Person{
    private String failureLevel;
    private Person guardian;

    private static User instance;
    public static synchronized User getInstance() {
        if(null == instance){
            instance = new User(-1, null, null, null, 0, null, null, null, null, null);
        }
        return instance;
    }

    public User(long id, String email, String name, Gender gender, int age, String nickname, String phone, String address, String district) {
        super(id, email, name, gender, age, nickname, phone, address, district);
    }

    private User(long id, String email, String name, Gender gender, int age, String nickname, String phone, String address, String district, Person guardian) {
        super(id, email, name, gender, age, nickname, phone, address, district);
        this.guardian = guardian;
    }

    public void initUser (UserDto userDto, GuardianDto guardianDto) {
        Guardian myGuardian;
        if (guardianDto == null)
            myGuardian = null;
        else
            myGuardian = new Guardian(guardianDto.getUserId(), guardianDto.getEmail(), guardianDto.getName(), guardianDto.getSex(), guardianDto.getAge(), guardianDto.getNickname(), guardianDto.getPhoneNumber(), guardianDto.getHomeAddress(), guardianDto.getDistrict());
        if (userDto == null)
            instance = null;
        else
            instance = new User(userDto.getUserId(), userDto.getEmail(), userDto.getName(), userDto.getSex(), userDto.getAge(), userDto.getNickname(), userDto.getPhoneNumber(), userDto.getHomeAddress(), userDto.getDistrict(), myGuardian);
    }

    public Person getGuardian() {
        return guardian;
    }

    public String getFailureLevel() {
        return failureLevel;
    }

    public void setMyGuardian(GuardianDto guardian) {
        this.guardian = new Guardian(guardian.getUserId(), guardian.getEmail(), guardian.getName(), guardian.getSex(), guardian.getAge(), guardian.getNickname(), guardian.getPhoneNumber(), guardian.getHomeAddress(), guardian.getDistrict());
    }
}
