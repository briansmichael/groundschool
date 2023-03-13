package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Event;
import com.starfireaviation.groundschool.model.entity.Question;
import com.starfireaviation.groundschool.model.entity.Quiz;
import com.starfireaviation.groundschool.model.entity.QuizQuestion;
import com.starfireaviation.groundschool.model.repository.EventRepository;
import com.starfireaviation.groundschool.model.repository.QuizQuestionRepository;
import com.starfireaviation.groundschool.model.repository.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventService extends BaseService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getUpcoming() {
        return eventRepository
                .findAll()
                .stream()
                .filter(e -> e.getStartTime() != null && e.getStartTime().isAfter(LocalDateTime.now()))
                .filter(e -> !e.isPrivateEvent())
                .sorted()
                .limit(3)
                .collect(Collectors.toList());
    }

    public Event createOrUpdate(final Event event) {
        if (event != null) {
            if (event.getStartTime() == null) {
                event.setStartTime(LocalDateTime.now().plus(1, ChronoUnit.DAYS));
            }
            return eventRepository.save(event);
        }
        return null;
    }

    public void removeEvent(final Long eventId) {
        eventRepository
                .findById(eventId)
                .ifPresent(event -> eventRepository.delete(event));
    }

    public Event getEvent(final Long eventId) {
        final Event event = eventRepository.findById(eventId).orElse(null);
        return event;
    }

}
