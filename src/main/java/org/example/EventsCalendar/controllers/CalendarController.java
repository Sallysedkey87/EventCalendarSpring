package org.example.EventsCalendar.controllers;

import org.example.EventsCalendar.models.Event;
import org.example.EventsCalendar.repositories.EventRepository;
import org.example.EventsCalendar.service.CrawlerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class CalendarController {

    private final EventRepository eventRepository;
    private final CrawlerService crawlerService;

    public CalendarController(EventRepository eventRepository, CrawlerService crawlerService) {
        this.eventRepository = eventRepository;
        this.crawlerService = crawlerService;
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

    @GetMapping("/api/crawl")
    @ResponseBody
    public String crawl(@RequestParam String url) {
        try {
            return crawlerService.crawlAndProcess(url);
        } catch (IOException e) {
            return "Error crawling: " + e.getMessage();
        }
    }
}
