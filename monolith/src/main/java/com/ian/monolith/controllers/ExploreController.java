package com.ian.monolith.controllers;

import com.ian.monolith.CityRepositoryOLD;
import com.ian.monolith.models.City;
import com.ian.monolith.models.CitySelection;
import com.ian.monolith.repositories.CityRepository;
import com.ian.monolith.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class ExploreController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private EventService eventService;

    @GetMapping
    public String explore(Model model){

//        cityRepository.saveAll(CityRepositoryOLD.getAllCities());

        //todo Move city repo accesses to a service
        model.addAttribute("cities", cityRepository.findAll());
        CitySelection attributeValue = new CitySelection();
        attributeValue.setCity("New Zealand");
        model.addAttribute("selected_location", attributeValue);
        model.addAttribute("events", eventService.getEventsTodayForCity(null));
        return "explore";
    }

    //How to handle this better?
    @PostMapping(value = {"/", "/city/{city}"})
    public String redirectToExploreCity(@ModelAttribute CitySelection citySelection){
        if("".equals(citySelection.getCity())){
            return "redirect:/";
        }
        return "redirect:/city/" + citySelection.getCity();
    }

    @GetMapping("/city/{city}")
    public String exploreCity(Model model, @PathVariable("city")  String location){
        //How to handle this situation better?
        CitySelection selection = new CitySelection();
        selection.setCity(location);
        //Handle null case better
        City city = CityRepositoryOLD.getAllCities().stream()
                .filter( x -> location.equals(x.getName()))
                .findFirst()
                .orElse(null);

        model.addAttribute("selected_location", selection);
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("events", eventService.getEventsTodayForCity(city));
        return "explore";
    }
}
