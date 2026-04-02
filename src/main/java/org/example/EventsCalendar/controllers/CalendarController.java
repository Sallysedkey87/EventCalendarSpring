package org.example.EventsCalendar.controllers;

import org.example.EventsCalendar.models.Event;
import org.example.EventsCalendar.repositories.EventRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CalendarController {

    private final EventRepository eventRepository;

    public CalendarController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/api/events")
    @ResponseBody
    public List<Event> getEvents() {
        return eventRepository.findAll();
    }
}
