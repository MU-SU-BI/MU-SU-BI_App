package com.example.musubi.model.dto;

public class Dto<T> {
    private final String responseMessage;
    private final T data;

    public Dto(String responseMessage, T data) {
        this.responseMessage = responseMessage;
        this.data = data;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
    public T getData() {
        return data;
    }
}
