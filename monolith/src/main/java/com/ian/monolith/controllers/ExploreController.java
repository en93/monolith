package com.ian.monolith.controllers;

import com.ian.monolith.CityRepository;
import com.ian.monolith.models.City;
import com.ian.monolith.models.CitySelection;
import com.ian.monolith.services.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class ExploreController {

    @GetMapping
    public String explore(Model model){
//        model.addAttribute("name", "Ian");
        model.addAttribute("cities", CityRepository.getAllCities());
        model.addAttribute("cityselection", new CitySelection());
        model.addAttribute("events", EventService.getEventsTodayForCity(null));
        return "explore";
    }

    //How to handle this better?
    @PostMapping(value = {"/", "/city/{city}"})
    public String redirectToExploreCity(@ModelAttribute CitySelection citySelection){
        return "redirect:/city/" + citySelection.getCity();
    }

    //theres a problem, all nonempty paths match here
    @GetMapping("/city/{city}")
    public String exploreCity(Model model, @PathVariable("city")  String location){
        //How to handle this situation better?
        CitySelection selection = new CitySelection();
        selection.setCity(location);
        //Handle null case better
        City city = CityRepository.getAllCities().stream()
                .filter( x -> location.equals(x.getName()))
                .findFirst()
                .orElse(null);

//        model.addAttribute("name", "Not Ian");
        model.addAttribute("user_city", city);
        model.addAttribute("cities", CityRepository.getAllCities());
        model.addAttribute("cityselection", selection);
        model.addAttribute("events", EventService.getEventsTodayForCity(city));
        return "explore";
    }
}
