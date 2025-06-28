package com.starfireaviation.groundschool.sms;

import com.starfireaviation.groundschool.events.EventCompletedEvent;
import com.starfireaviation.groundschool.events.EventCreatedEvent;
import com.starfireaviation.groundschool.events.EventStartedEvent;
import com.starfireaviation.groundschool.quiz.QuizCompletedEvent;
import com.starfireaviation.groundschool.quiz.QuizStartedEvent;
import com.starfireaviation.groundschool.users.UserCreatedEvent;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
public class SMS {

    @ApplicationModuleListener
    void userCreated(UserCreatedEvent uce) {
        System.out.println("User created [" + uce + "]");
    }

    @ApplicationModuleListener
    void eventCreated(EventCreatedEvent ece) {
        System.out.println("Event created [" + ece + "]");
    }

    @ApplicationModuleListener
    void eventStarted(EventStartedEvent ese) {
        System.out.println("Event started [" + ese + "]");
    }

    @ApplicationModuleListener
    void eventCompleted(EventCompletedEvent ece) {
        System.out.println("Event completed [" + ece + "]");
    }

    @ApplicationModuleListener
    void quizStarted(QuizStartedEvent qse) {
        System.out.println("Quiz started [" + qse + "]");
    }

    @ApplicationModuleListener
    void quizCompleted(QuizCompletedEvent qce) {
        System.out.println("Quiz completed [" + qce + "]");
    }
}
