package com.ian.monolith.controllers;

import com.ian.monolith.CityRepository;
import com.ian.monolith.form.CitySelection;
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
        return "explore";
    }

    @PostMapping
    public String redirectToExploreCity(@ModelAttribute CitySelection citySelection){
        return "redirect:/" + citySelection.getCity();
    }

    @GetMapping("/{city}")
    public String exploreCity(Model model, @PathVariable("city")  String city){
        //How to handle this situation better?
        CitySelection selection = new CitySelection();
        selection.setCity(city);

//        model.addAttribute("name", "Not Ian");
        model.addAttribute("user_city", city);
        model.addAttribute("cities", CityRepository.getAllCities());
        model.addAttribute("cityselection", selection);
        return "explore";
    }
}
