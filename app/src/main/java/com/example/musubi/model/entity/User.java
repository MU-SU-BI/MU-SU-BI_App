package com.example.musubi.model.entity;

public class User extends Person{
    private final DiseaseType diseaseType;
    private final int rating;
    private final User guardian;

    protected User(int id, String email, String name, String nickname, String phone, String address, DiseaseType type, int rating, User guardian) {
        super(id, email, name, nickname, phone, address);
        this.diseaseType = type;
        this.rating = rating;
        this.guardian = guardian;
    }

    public DiseaseType getDiseaseType() {
        return diseaseType;
    }

    public int getRating() {
        return rating;
    }

    public User getGuardian() {
        return guardian;
    }
}
