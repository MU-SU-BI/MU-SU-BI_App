package com.example.musubi.model.entity;

public class User extends Person{
    private final DiseaseType diseaseType;
    private final int rating;
    private final Person guardian;

    protected User(int id, String email, String name, String nickname, String phone, String address, DiseaseType type, int rating, Person guardian) {
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

    public Person getGuardian() {
        return guardian;
    }
}
