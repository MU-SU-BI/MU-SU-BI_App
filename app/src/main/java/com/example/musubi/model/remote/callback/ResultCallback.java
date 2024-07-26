package com.example.musubi.model.remote.callback;

public interface ResultCallback<T> {
    void onSuccess(T result);
    void onFailure(String result, Throwable t);
}
