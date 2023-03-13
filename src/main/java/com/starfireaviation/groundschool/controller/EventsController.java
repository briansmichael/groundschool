package com.starfireaviation.groundschool.controller;

import com.starfireaviation.groundschool.model.entity.Event;
import com.starfireaviation.groundschool.model.web.Selection;
import com.starfireaviation.groundschool.service.EventService;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Controller
@Slf4j
public class EventsController {

    @Autowired
    private EventService eventService;

    @GetMapping("/")
    public String main(final Model model) {
        return defaultView(model);
    }

    @GetMapping("/events")
    public String getEvents(final Model model) {
        return defaultView(model);
    }

    @GetMapping("/events/{id}")
    public String getEvents(@PathVariable("id") final Long eventId, final Model model) {
        return eventView(eventId, model);
    }

    @GetMapping("/events/edit/{id}")
    public String editEvent(@PathVariable("id") final Long eventId, final Model model) {
        log.info("Editing Event.");
        model.addAttribute("event", eventService.getEvent(eventId));
        log.info("Returning event id: {}", eventId);
        return "editevent"; //view
    }

    @GetMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable("id") final Long eventId, final Model model) {
        log.info("Deleting Event.");
        eventService.removeEvent(eventId);
        return defaultView(model);
    }

    @GetMapping("/events/new")
    public String newEvent(final Model model) {
        log.info("Creating a new Event.");
        model.addAttribute("event", new Event());
        return "newevent"; //view
    }

    @PostMapping("/events")
    public String createEvent(final Event event, final Model model) {
        final Event savedEvent = eventService.createOrUpdate(event);
        return eventView(savedEvent.getId(), model);
    }

    @PutMapping("/events")
    public String updateEvent(final Event event, final Model model) {
        final Event savedEvent = eventService.createOrUpdate(event);
        return eventView(savedEvent.getId(), model);
    }

    private String defaultView(final Model model) {
        final List<Event> events = eventService.getUpcoming();
        model.addAttribute("events", events);
        return "events";
    }

    private String eventView(final Long eventId, final Model model) {
        model.addAttribute("event", eventService.getEvent(eventId));
        return "event"; //view
    }

}
