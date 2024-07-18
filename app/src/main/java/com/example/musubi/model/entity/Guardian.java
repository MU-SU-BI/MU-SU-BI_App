package com.example.musubi.model.entity;

public class Guardian extends Person{
    private final Person user;

    protected Guardian(int id, String email, String name, String nickname, String phone, String address, Person user) {
        super(id, email, name, nickname, phone, address);
        this.user = user;
    }

    public Person getUser() {
        return user;
    }
}
