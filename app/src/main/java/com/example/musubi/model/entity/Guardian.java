package com.example.musubi.model.entity;

public class Guardian extends Person{
    private final Person user;

    protected Guardian(long id, String email, String name, Gender gender, int age, String nickname, String phone, String address, String district, Person user) {
        super(id, email, name, gender, age, nickname, phone, address, district);
        this.user = user;
    }

    public Person getUser() {
        return user;
    }
}
