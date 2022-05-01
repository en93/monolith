package com.ian.monolith;

import com.ian.monolith.models.City;

import java.util.Arrays;
import java.util.List;

public class DataSetupHelper {
    public static List<City> getAllCities(){
        return Arrays.asList(new City("Feilding and District", "30779"), new City("Virtual", "34045"),
                new City("Northland", "1"), new City("Auckland", "2"),
                new City("The Coromandel", "41"), new City("Hawke's Bay - Gisborne", "6"),
                new City("Waikato", "3"), new City("Bay of Plenty", "4"),
                new City("Taranaki", "7"), new City("Manawatu - Whanganui", "9"),
                new City("Wellington Region", "11"), new City("Nelson - Tasman", "12"),
                new City("Marlborough", "13"), new City("West Coast", "14"),
                new City("Canterbury", "15"), new City("Otago", "17"),
                new City("Southland", "18"));
    }
}
