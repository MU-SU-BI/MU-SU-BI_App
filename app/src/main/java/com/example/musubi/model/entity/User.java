package com.example.musubi.model.entity;

public class User extends Person{
    private String failureLevel;
    private Person guardian;

    public User(long id, String email, String name, String nickname, String phone, String address, Gender gender, int age) {
        super(id, email, name, nickname, phone, address, gender, age);
    }

    public Person getGuardian() {
        return guardian;
    }

    public void setGuardian(Person guardian) {
        this.guardian = guardian;
    }

    public void setFailureLevel(String failureLevel) {
        this.failureLevel = failureLevel;
    }
}
