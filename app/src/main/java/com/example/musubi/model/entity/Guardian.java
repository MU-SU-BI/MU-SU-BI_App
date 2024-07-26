package com.example.musubi.model.entity;

import com.example.musubi.model.dto.GuardianDto;

public class Guardian extends Person{
    private Person user;

    private static Guardian instance;
    public static synchronized Guardian getInstance() {
        if(null == instance){
            instance = new Guardian(-1, null, null, null, 0, null, null, null, null);
        }
        return instance;
    }

    protected Guardian(long id, String email, String name, Gender gender, int age, String nickname, String phone, String address, String district) {
        super(id, email, name, gender, age, nickname, phone, address, district);
    }

    public void initGuardian (GuardianDto dto) {
        instance = new Guardian(dto.getUserId(), dto.getEmail(), dto.getName(), dto.getSex(), dto.getAge(), dto.getNickname(), dto.getPhoneNumber(), dto.getHomeAddress(), dto.getDistrict());
    }

    public Person getUser() {
        return user;
    }
}
