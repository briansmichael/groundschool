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
    public String getEvent(@PathParam("eventId") final Long eventId, final Model model) {
        try {
            return eventView(eventId, model);
        } catch (Exception e) {
            return eventView(1L, model);
        }
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
        return eventView(1L, model);
    }

    private String eventView(final Long eventId, final Model model) {
        final List<Event> events = eventService.getUpcoming();
        model.addAttribute("events", events);
        model.addAttribute("event", eventService.getEvent(eventId));
        final Selection selection = new Selection();
        selection.setEventId(eventId);
        model.addAttribute("selection", selection);
        return "events"; //view
    }

}
