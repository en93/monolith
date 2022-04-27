package com.ian.monolith.controllers;

import com.ian.monolith.models.Event;
import com.ian.monolith.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/{event_id}")
    public String showEventPage(Model model, @PathVariable("event_id")  String id){
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        return "event";
    }


}
