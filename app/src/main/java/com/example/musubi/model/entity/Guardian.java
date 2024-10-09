package com.example.musubi.model.entity;

import com.example.musubi.model.dto.GuardianDto;
import com.example.musubi.model.dto.UserDto;

public class Guardian extends Person {
    private Person user;

    private static Guardian instance;

    public static synchronized Guardian getInstance() {
        if (null == instance) {
            instance = new Guardian(-1, null, null, null, 0, null, null, null, null, null);
        }
        return instance;
    }

    public Guardian(long id, String email, String name, Gender gender, int age, String nickname, String phone, String address, String district) {
        super(id, email, name, gender, age, nickname, phone, address, district);
    }

    private Guardian(long id, String email, String name, Gender gender, int age, String nickname, String phone, String address, String district, Person user) {
        super(id, email, name, gender, age, nickname, phone, address, district);
        this.user = user;
    }

    public void initGuardian(GuardianDto guardianDto, UserDto userDto) {
        User myUser;

        if (userDto == null)
            myUser = null;
        else
            myUser = new User(userDto.getUserId(), userDto.getEmail(), userDto.getName(), userDto.getSex(), userDto.getAge(), userDto.getNickname(), userDto.getPhoneNumber(), userDto.getHomeAddress(), userDto.getDistrict(), userDto.getProfile());
        if (guardianDto == null)
            instance = null;
        else
            instance = new Guardian(guardianDto.getUserId(), guardianDto.getEmail(), guardianDto.getName(), guardianDto.getSex(), guardianDto.getAge(), guardianDto.getNickname(), guardianDto.getPhoneNumber(), guardianDto.getHomeAddress(), guardianDto.getDistrict(), myUser);
    }

    public Person getUser() {
        return user;
    }
}
