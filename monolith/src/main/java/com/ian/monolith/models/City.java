package com.ian.monolith.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

//todo separate doc types from data form data types
@Document("city")
public class City {

    @MongoId
    public String id;
    public String name;

    public City() {    }

    public City(String name) {
        this.name = name;
    }

    public City(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
