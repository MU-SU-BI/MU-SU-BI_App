package com.example.musubi.model.dto;

public class Dto<T> {
    private String responseMessage;
    private T data;

    public String getResponseMessage() {
        return responseMessage;
    }
    public T getData() {
        return data;
    }
}
