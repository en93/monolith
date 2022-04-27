package com.ian.monolith.controllers;

import com.ian.monolith.models.City;
import com.ian.monolith.models.Event;
import com.ian.monolith.repositories.CityRepository;
import com.ian.monolith.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
@RequestMapping("/")
public class ExploreController {

    public static final String ALL_CITIES = "all_cities";
    public static final String SELECTED_LOCATION = "selected_location";
    public static final String TODAYS_EVENTS = "todays_events";

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private EventService eventService;

    @GetMapping
    public String explore(Model model){
        // cityRepository.saveAll(CityRepositoryOLD.getAllCities());

        List<City> cities = cityRepository.findAll();
        List<Event> events = eventService.getEventsTodayForCity(null);

        model.addAttribute(ALL_CITIES, cities);
        model.addAttribute(SELECTED_LOCATION, new City());
        model.addAttribute(TODAYS_EVENTS, events);
        return "explore";
    }

    //todo, further explore redirection
    @PostMapping(value = {"/", "/city/{city}"})
    public String redirectToExploreCity(@ModelAttribute City city){

        String selection = city.getName();
        if(StringUtils.hasText(selection)) {
            return "redirect:/city/" + selection;
        }
        //Return home
        return "redirect:/";
    }

    @GetMapping("/city/{city_name}")
    public String exploreCity(Model model, @PathVariable("city_name")  String location){
        City selectedCity = cityRepository.findCityByName(location);
        List<Event> events = eventService.getEventsTodayForCity(selectedCity);
        List<City> cities = cityRepository.findAll();

        model.addAttribute(SELECTED_LOCATION, selectedCity);
        model.addAttribute(ALL_CITIES, cities);
        model.addAttribute(TODAYS_EVENTS, events);
        return "explore";
    }
}
