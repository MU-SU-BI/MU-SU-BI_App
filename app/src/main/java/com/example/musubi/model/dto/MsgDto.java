package com.example.musubi.model.dto;

public class MsgDto {
    private final String responseMessage;

    public MsgDto(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage(){return this.responseMessage;}
}
