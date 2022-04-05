package com.ian.monolith.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value={"/", "explore"})
public class ExploreController {

    @GetMapping
    public String explore(){
        return "explore";
    }
}
