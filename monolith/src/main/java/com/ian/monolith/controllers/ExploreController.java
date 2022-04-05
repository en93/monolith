package com.ian.monolith.controllers;

import com.ian.monolith.CityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value={"/", "explore"})
public class ExploreController {

    @GetMapping
    public String explore(Model model){
        model.addAttribute("name", "Ian");
        model.addAttribute("cities", CityRepository.getAllCities());
        return "explore";
    }

    @PutMapping
    public String exploreCity(Model model){
        model.addAttribute("name", "NOt Ian");
        model.addAttribute("cities", CityRepository.getAllCities());
        return "explore";
    }
}
