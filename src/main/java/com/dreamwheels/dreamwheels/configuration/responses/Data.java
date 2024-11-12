package com.dreamwheels.dreamwheels.configuration.responses;

public class Data<T> {
    private T results;
    public Data(T data) {
        this.results = data;
    }

    public T getData() {
        return results;
    }

    public void setData(T data) {
        this.results = data;
    }
}
