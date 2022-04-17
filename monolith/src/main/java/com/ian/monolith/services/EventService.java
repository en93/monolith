package com.ian.monolith.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ian.monolith.models.City;
import com.ian.monolith.models.Event;
import com.ian.monolith.models.EventList;
import com.ian.monolith.models.EventListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventService {

//    @Autowired
//    private static Environment env;
//
//    @Override
//    public void setEnvironment(Environment environment) {
//        env = environment;
//    }

    //todo timezones
    //Need to add time componenet
    public static List<EventListing> getEventsTodayForCity(City city) {

        //Get NZ time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        String formattedDate = LocalDateTime.now(ZoneId.of("GMT+12:00")).format(formatter);

        //TODO REMOVE BEFORE COMMITING
        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("", "").build();
        String url;
        //Tidy this up
        if (city == null){
            //All events
            url = "https://api.eventfinda.co.nz/v2/events.json?order=date&location=&end_date="+ formattedDate + " 23:59:59&fields=event:(id,name,description)";
        } else {
            url = "https://api.eventfinda.co.nz/v2/events.json?order=date&location=" + city.code + "&end_date=" + formattedDate + " 23:59:59&fields=event:(id,name,description)";
        }
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        String body = forEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        JsonNode root;
        try {
            root = mapper.readTree(body);
        } catch (JsonProcessingException e){
            return Collections.EMPTY_LIST;
        }
        JsonNode events = root.path("events");

        //Would be easier via strings
        List<EventListing> eventListings = new ArrayList<>();
        events.iterator()
                .forEachRemaining(event -> eventListings.add(mapper.convertValue(event, EventListing.class)));

        return eventListings;
    }

    public static Event getEventById(String id){

        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("testingmicroserviceseventfinda", "89kp6wbwjq7p").build();
        String url = "https://api.eventfinda.co.nz/v2/events.json?id=" + id;
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);

        //Get rid of repeated boilerplate
        String body = forEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
//        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        JsonNode root;
        try {
            root = mapper.readTree(body);
        } catch (JsonProcessingException e){
            return null;
        }
        //Feeling lazy
        JsonNode events = root.path("events");
        events = events.path(0);

        Event event = new Event();
        event.id = events.path("id").asText();
        event.name = events.path("name").asText();
        event.description = events.path("description").asText();
        event.start = events.path("datetime_start").asText();
        event.end = events.path("datetime_end").asText();
        event.location = events.path("location_summary").asText();
        event.address = events.path("address").asText();
        event.isFree = events.path("is_free").asBoolean();
        event.isCancelled = events.path("is_cancelled").asBoolean();
        event.restrictions = events.path("restrictions").asText();

        return event;

    }

}
