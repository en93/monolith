package com.ian.monolith.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class EventListing {
    @JsonProperty("id")
    public int id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("description")
    public String description;

//    public EventListing(int id, String name, String description) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//    }
//
//    public EventListing() {}
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

}
