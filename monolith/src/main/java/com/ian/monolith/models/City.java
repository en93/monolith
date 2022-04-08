package com.ian.monolith.models;

public class City {
    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public String name;

    public int code;

    public City(String name, int code) {
        this.name = name;
        this.code = code;
    }
}
