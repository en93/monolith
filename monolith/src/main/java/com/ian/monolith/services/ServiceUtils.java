package com.ian.monolith.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ServiceUtils {
    private static final ZoneId NZ_TIME_ZONE = ZoneId.of("GMT+12:00");
    public static final String DATE_FORMAT = "yyyy-MM-d";

    public static String getFormattedDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDateTime.now(NZ_TIME_ZONE).format(formatter);
    }

    public static LocalDateTime getLocalTime(){
        return LocalDateTime.now(NZ_TIME_ZONE);
    }
}
