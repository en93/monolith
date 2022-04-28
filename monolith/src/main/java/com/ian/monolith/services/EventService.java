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

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    private static final int MAX_CACHED_MINUTES = 3;

    //Need to add pages
    public List<Event> getEventsTodayForCity(City city) {

        String formattedDate = ServiceUtils.getFormattedDate();
        String url = buildUrl(city, formattedDate);

        //Build rest template and call API
        RestTemplate restTemplate = new RestTemplateBuilder()
                .basicAuthentication("", "").build();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        List<Event> events = getEventListFromResponse(responseEntity);
        return events;
    }
    
    private List<Event> getEventListFromResponse(ResponseEntity<String> responseEntity) {
        String responseBody = responseEntity.getBody();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        JsonNode root;
        try {
            root = mapper.readTree(responseBody);
        } catch (JsonProcessingException e){
            return Collections.emptyList();
        }
        JsonNode eventsNode = root.path("events");
        
        return getEventListFromNode(mapper, eventsNode);
    }

    private List<Event> getEventListFromNode(ObjectMapper mapper, JsonNode eventsNode) {
        return StreamSupport
                .stream(eventsNode.spliterator(), false)
                .map(event -> mapper.convertValue(event, Event.class))
                .collect(Collectors.toList());
    }

    private String buildUrl(City city, String formattedDate) {
        if (city == null) {
            //Show all events
            return  "https://api.eventfinda.co.nz/v2/events.json?order=date&location=&end_date="
                    + formattedDate + " 23:59:59&fields=event:(id,name,description)";
        }
        //Show city specific events
        return  "https://api.eventfinda.co.nz/v2/events.json?order=date&location=" + city.getId()
                + "&end_date=" + formattedDate + " 23:59:59&fields=event:(id,name,description)";

    }

    public Event getEventById(String id){

        Event cachedEvent = getCachedEvent(id);
        if (cachedEvent != null) {
            return cachedEvent;
        }

        //Build rest template and call API
        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication(
                "", "").build();
        String url = "https://api.eventfinda.co.nz/v2/events.json?id=" + id
                + "&fields=event:(id,name,description,datetime_start,datetime_end,location_summary,address,is_free,is_cancelled,restrictions)";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        Event event = getEventFromResponse(forEntity);
        return event;
    }

    private Event getEventFromResponse(ResponseEntity<String> forEntity) {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(forEntity.getBody());
        } catch (JsonProcessingException e){
            return null;
        }

        List<Event> eventsList = getEventListFromNode(mapper, root.path("events"));
        Event event = eventsList.get(0);

        //Cache and return value
        eventRepository.save(event);
        return event;
    }

    private Event getCachedEvent(String id) {
        Optional<Event> cached = eventRepository.findById(id);
        if (cached.isPresent()){
            Event event = cached.get();
            long cachedTime = ChronoUnit.MINUTES.between(event.getCachedDateTime(), ServiceUtils.getLocalTime());

            if (cachedTime > MAX_CACHED_MINUTES){
                return event;
            } else {
                eventRepository.delete(event);
            }
        }
        return null;
    }

}
