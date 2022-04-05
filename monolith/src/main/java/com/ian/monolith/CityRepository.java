package com.ian.monolith;

import java.util.Arrays;
import java.util.List;

public class CityRepository {
    public static List<String> getAllCities(){
        return Arrays.asList("Feilding and District", "Virtual", "Northland", "Auckland", "The Coromandel",
                "Hawke's Bay / Gisborne", "Waikato", "Bay of Plenty", "Taranaki", "Manawatu / Whanganui",
                "Wellington Region", "Nelson / Tasman", "Marlborough", "West Coast", "Canterbury", "Otago",
                "Southland");
    }
}
