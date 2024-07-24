package com.example.musubi.model.entity;

public class Guardian extends Person{
    private final Person user;

    protected Guardian(long id, String email, String name, String nickname, String phone, String address, Gender gender, int age, Person user) {
        super(id, email, name, nickname, phone, address, gender, age);
        this.user = user;
    }

    public Person getUser() {
        return user;
    }
}
