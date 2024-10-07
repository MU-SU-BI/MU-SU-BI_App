package com.example.musubi.model.dto;

public class SosUserInfoDto {
    private final String name;
    private final int age;
    private final String sex;
    private final String homeAddress;
    private final String phoneNumber;
    private final String guardianPhoneNumber;
    private final String profile;
    private final String mapImage;

    public SosUserInfoDto(String name, int age, String sex, String homeAddress, String phoneNumber, String guardianPhoneNumber, String profile, String mapImage) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.homeAddress = homeAddress;
        this.phoneNumber = phoneNumber;
        this.guardianPhoneNumber = guardianPhoneNumber;
        this.profile = profile;
        this.mapImage = mapImage;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGuardianPhoneNumber() {
        return guardianPhoneNumber;
    }

    public String getProfile() {
        return profile;
    }

    public String getMapImage() {
        return mapImage;
    }
}
