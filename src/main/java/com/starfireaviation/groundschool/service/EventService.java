package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EventService extends BaseService {

    public List<Event> getUpcoming() {
        final List<Event> events = new ArrayList<>();
        return events;
//        return eventRepository
//                .findAll()
//                .stream()
//                .filter(e -> e.getStartTime() != null && e.getStartTime().isAfter(LocalDateTime.now()))
//                .filter(e -> !e.isPrivateEvent())
//                .sorted()
//                .limit(3)
//                .collect(Collectors.toList());
    }

    public Event createOrUpdate(final Event event) {
        return event;
//        if (event != null) {
//            if (event.getStartTime() == null) {
//                event.setStartTime(LocalDateTime.now().plus(1, ChronoUnit.DAYS));
//            }
//            return eventRepository.save(event);
//        }
//        return null;
    }

    public void removeEvent(final Long eventId) {
//        eventRepository
//                .findById(eventId)
//                .ifPresent(event -> eventRepository.delete(event));
    }

    public Event getEvent(final Long eventId) {
        //final Event event = eventRepository.findById(eventId).orElse(null);
        final Event event = new Event();
        return event;
    }

}
