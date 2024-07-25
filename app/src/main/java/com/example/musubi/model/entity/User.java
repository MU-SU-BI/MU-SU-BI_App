package com.example.musubi.model.entity;

public class User extends Person{
    private String failureLevel;
    private Person guardian;

    public User(long id, String email, String name, int age, String nickname, String phone, String address, Gender gender) {
        super(id, email, name, gender, age, nickname, phone, address);
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
