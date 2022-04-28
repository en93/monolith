package com.ian.monolith.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ian.monolith.services.ServiceUtils;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

//todo Add event session support
@JsonSerialize
@Document("event")
public class Event {

    @MongoId
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("datetime_start")
    private String start;
    @JsonProperty("datetime_end")
    private String end;
    @JsonProperty("location_summary")
    private String location;
    @JsonProperty("address")
    private String address;
    @JsonProperty("is_free")
    private boolean isFree;
    @JsonProperty("is_cancelled")
    private boolean isCancelled;
    @JsonProperty("restrictions")
    private String restrictions;
    private LocalDateTime cachedDateTime;

    public Event(){
        this.cachedDateTime = ServiceUtils.getLocalTime();
    }

    public LocalDateTime getCachedDateTime() {
        return cachedDateTime;
    }

    public void setCachedDateTime(LocalDateTime cachedDateTime) {
        this.cachedDateTime = cachedDateTime;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }
}
