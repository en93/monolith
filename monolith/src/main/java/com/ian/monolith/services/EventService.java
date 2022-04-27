package com.ian.monolith.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ian.monolith.models.City;
import com.ian.monolith.models.Event;
import com.ian.monolith.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class EventService {

    private static final ZoneId NZ_TIME_ZONE = ZoneId.of("GMT+12:00");

    private static final int CACHED_MINUTES = 3;

    @Autowired
    private EventRepository eventRepository;

    //Need to add pages
    public List<Event> getEventsTodayForCity(City city) {

        //Get NZ time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        String formattedDate = LocalDateTime.now(NZ_TIME_ZONE).format(formatter);

        //TODO REMOVE BEFORE COMMITING
        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("", "").build();
        String url;
        //Tidy this up
        if (city == null){
            //All events
            url = "https://api.eventfinda.co.nz/v2/events.json?order=date&location=&end_date="+ formattedDate + " 23:59:59&fields=event:(id,name,description)";
        } else {
            url = "https://api.eventfinda.co.nz/v2/events.json?order=date&location=" + city.getId() + "&end_date=" + formattedDate + " 23:59:59&fields=event:(id,name,description)";
        }
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        String body = forEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        JsonNode root;
        try {
            root = mapper.readTree(body);
        } catch (JsonProcessingException e){
            return Collections.emptyList();
        }
        JsonNode events = root.path("events");

        //Would be easier via strings
        List<Event> eventListings = new ArrayList<>();
        events.iterator()
                .forEachRemaining(event -> eventListings.add(mapper.convertValue(event, Event.class)));

        return eventListings;
    }

    public Event getEventById(String id){

        //todo handle this as an optional and functionally
        Optional<Event> cached = eventRepository.findById(id);
        //todo move cached time to a parent class and handle all checks there
        if (cached.isPresent()){
            Event event = cached.get();
            long minutes = ChronoUnit.MINUTES.between(event.getCachedDateTime(), LocalDateTime.now(NZ_TIME_ZONE));

            if (minutes > CACHED_MINUTES){
                return event;
            } else {
                //todo consider moving to end or in event of failure to retrieve
                eventRepository.delete(event);
            }
        }

        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("testingmicroserviceseventfinda", "89kp6wbwjq7p").build();
        String url = "https://api.eventfinda.co.nz/v2/events.json?id=" + id;
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);

        //Get rid of repeated boilerplate
        String body = forEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
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
        event.setId(events.path("id").asText());
        event.setName(events.path("name").asText());
        event.setDescription(events.path("description").asText());
        event.setStart(events.path("datetime_start").asText());
        event.setEnd(events.path("datetime_end").asText());
        event.setLocation(events.path("location_summary").asText());
        event.setAddress(events.path("address").asText());
        event.setFree(events.path("is_free").asBoolean());
        event.setCancelled(events.path("is_cancelled").asBoolean());
        event.setRestrictions(events.path("restrictions").asText());
        event.setCachedDateTime(LocalDateTime.now());

        //Save to local cache and return value
        eventRepository.save(event);
        return event;

    }

}
