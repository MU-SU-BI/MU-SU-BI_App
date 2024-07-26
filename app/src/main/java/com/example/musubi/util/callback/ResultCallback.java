package com.example.musubi.util.callback;

public interface ResultCallback<T> {
    void onSuccess(T result);
    void onFailure(String result, Throwable t);
}
