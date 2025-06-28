package com.starfireaviation.groundschool.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}

interface EventRepository extends ListCrudRepository<Event, Integer> {
}

@Table("events")
record Event(@Id Integer id) {
}
