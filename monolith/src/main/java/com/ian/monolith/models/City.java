package com.ian.monolith.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("city")
public class City {

    @MongoId
    private String id;
    private String name;


    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public City(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
