package com.example.musubi.model.entity;

public class User extends Person{
    private final Person guardian;

    protected User(long id, String email, String name, String nickname, String phone, String address, Gender gender, int age, Person guardian) {
        super(id, email, name, nickname, phone, address, gender, age);
        this.guardian = guardian;
    }

    public Person getGuardian() {
        return guardian;
    }
}
