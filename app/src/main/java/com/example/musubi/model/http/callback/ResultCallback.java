package com.example.musubi.model.http.callback;

public interface ResultCallback<T> {
    void onSuccess(T result);
    void onFailure(T result, Throwable t);
}
