package com.ian.monolith.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

/***
 * Stores information on cities we cover
 */
@Document("city")
public class City {

    @MongoId
    private String id;
    private String name;

    public City() {    }

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
