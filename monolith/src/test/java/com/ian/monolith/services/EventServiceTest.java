package com.ian.monolith.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ian.monolith.models.City;
import com.ian.monolith.models.EventListing;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventServiceTest {

    @Test
    public void getAucklandEventPreviews(){
        City city = new City("Wellington Region", "11");
        List<EventListing> aucklandEventPreviews = EventService.getEventsTodayForCity(city);
    }

    @Test
    public void testDate() {
        String nowDate;
        String thenDate;
        DateTimeFormatter formatter;
        String test;

        LocalDateTime now = LocalDateTime.now(ZoneId.of("GMT+12:00"));
        LocalDateTime then = now.plusDays(15);

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        nowDate = now.format(formatter);
        thenDate = then.format(formatter);
        test = "123";

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        nowDate = now.format(formatter);
        thenDate = then.format(formatter);
        test = "123";

//        formatter = DateTimeFormatter.ofPattern("yyyy-MM-DD");
//        nowDate = now.format(formatter);
//        thenDate = then.format(formatter);
//        test = "123";
//
//        formatter = DateTimeFormatter.ofPattern("yyyy-MM-D");
//        nowDate = now.format(formatter);
//        test = "123";



    }

}
