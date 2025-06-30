package com.starfireaviation.groundschool.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/events")
class EventsController {

    private final Events events;

    EventsController(Events events) {
        this.events = events;
    }

    @PostMapping
    void create(@RequestBody Event event) {
        this.events.create(event);
    }

    @GetMapping("/{eventId}")
    Event get(@PathVariable("eventId") Long eventId) {
        return this.events.get(eventId);
    }

    @PutMapping("/{eventId}")
    void update(@PathVariable("eventId") Long eventId, Event event) {
        this.events.update(eventId, event);
    }

    @DeleteMapping("/{eventId}")
    void delete(@PathVariable("eventId") Long eventId) {
        this.events.delete(eventId);
    }

    @GetMapping
    List<Event> find() {
        return this.events.find();
    }

    @GetMapping("/{eventId}/instructors")
    List<Long> instructors(@PathVariable("eventId") Long eventId) {
        return this.events.instructors(eventId);
    }

    @GetMapping("/upcoming/{type}")
    List<Event> upcoming(@PathVariable("type") EventType type, @RequestParam("count") Long count) {
        return this.events.upcoming(type, count);
    }

    @GetMapping("/{eventId}/register/{userId}")
    void register(@PathVariable("eventId") Long eventId, @PathVariable("userId") Long userId) {
        this.events.register(eventId, userId);
    }

    @GetMapping("/{eventId}/unregister/{userId}")
    void unregister(@PathVariable("eventId") Long eventId, @PathVariable("userId") Long userId) {
        this.events.unregister(eventId, userId);
    }

    @GetMapping("/{eventId}/checkincode")
    String checkinCode(@PathVariable("eventId") Long eventId) {
        return this.events.getCheckinCode(eventId);
    }

    @GetMapping("/{eventId}/vote/{userId}/{lessonPlanId}")
    void vote(@PathVariable("eventId") Long eventId, @PathVariable("userId") Long userId,
              @PathVariable("lessonPlanId") Long lessonPlanId) {
        this.events.vote(eventId, userId, lessonPlanId);
    }

    @GetMapping("/{eventId}/vote/{userId}/{lessonPlanId}")
    void withdrawVote(@PathVariable("eventId") Long eventId, @PathVariable("userId") Long userId) {
        this.events.withdrawVote(eventId, userId);
    }
}

@Service
@Transactional
class Events {

    private final ApplicationEventPublisher publisher;

    private final EventRepository eventRepository;

    Events(ApplicationEventPublisher publisher, EventRepository repository) {
        this.publisher = publisher;
        this.eventRepository = repository;
    }

    void create(Event event) {
        var saved = this.eventRepository.save(event);
        System.out.println("saved [" + saved + "]");

        this.publisher.publishEvent(new EventCreatedEvent(saved.id()));
    }

    void start(Event event) {
        System.out.println("started [" + event + "]");

        this.publisher.publishEvent(new EventStartedEvent(event.id()));
    }

    void complete(Event event) {
        System.out.println("completed [" + event + "]");

        this.publisher.publishEvent(new EventCompletedEvent(event.id()));
    }

    public Event get(Long eventId) {
        return null;
    }

    public void update(Long eventId, Event event) {
    }

    public void delete(Long eventId) {
    }

    public List<Event> find() {
        return new ArrayList<>();
    }

    public List<Long> instructors(Long eventId) {
        return new ArrayList<>();
    }

    public List<Event> upcoming(EventType type, Long count) {
        return new ArrayList<>();
    }

    public void register(Long eventId, Long userId) {
    }

    public void unregister(Long eventId, Long userId) {
    }

    public String getCheckinCode(Long eventId) {
        return null;
    }

    public void vote(Long eventId, Long userId, Long lessonPlanId) {
    }

    public void withdrawVote(Long eventId, Long userId) {
    }
}

interface EventRepository extends ListCrudRepository<Event, Long> {
}

@Table("events")
record Event(@Id Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String title, LocalDateTime startTime,
             LocalDateTime endTime, String calendarUrl, String checkinCode, boolean checkinCodeRequired,
             boolean privateEvent, EventType type, Long lessonPlanId, Long eventLead) {
}

@Table("event_participants")
record EventParticipant(@Id Long id, LocalDateTime createdAt, LocalDateTime updatedAt, Long eventId, Long userId) {
}

@Table("votes")
record Vote(@Id Long id, LocalDateTime createdAt, LocalDateTime updatedAt, Long eventId, Long lessonPlanId,
            Long userId) {

}