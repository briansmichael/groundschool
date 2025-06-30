package com.starfireaviation.groundschool.users;

import com.starfireaviation.groundschool.common.NotificationPreference;
import com.starfireaviation.groundschool.common.Role;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@RequestMapping("/users")
class UsersController {

    private final Users users;

    UsersController(Users users) {
        this.users = users;
    }

    @PostMapping
    void create(@RequestBody User user) {
        this.users.create(user);
    }
}

@Service
@Transactional
class Users {

    private final ApplicationEventPublisher publisher;

    private final UserRepository userRepository;

    Users(ApplicationEventPublisher publisher, UserRepository repository) {
        this.publisher = publisher;
        this.userRepository = repository;
    }

    void create(User user) {
        var saved = this.userRepository.save(user);
        System.out.println("saved [" + saved + "]");
        this.publisher.publishEvent(new UserCreatedEvent(saved.id()));
    }
}

interface UserRepository extends ListCrudRepository<User, Long> {
}

@Table("users")
record User(@Id Long id, String email, boolean emailVerified, boolean emailEnabled, String sms, boolean smsVerified,
            boolean smsEnabled, String slack, boolean slackVerified, boolean slackEnabled, String username,
            String password, String firstName, String lastName, String certificateNumber, String code, Role role,
            NotificationPreference notificationPreference, Integer age) {
}

@Configuration
class AmqpIntegrationConfiguration {

    static final String USERS_Q = "users";

    @Bean
    Binding binding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(USERS_Q).noargs();
    }

    @Bean
    Exchange exchange() {
        return ExchangeBuilder.directExchange(USERS_Q).build();
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable(USERS_Q).build();
    }
}