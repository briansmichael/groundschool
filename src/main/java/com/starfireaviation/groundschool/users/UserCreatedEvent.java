package com.starfireaviation.groundschool.users;

import org.springframework.modulith.events.Externalized;

@Externalized(target = AmqpIntegrationConfiguration.USERS_Q)
public record UserCreatedEvent(int id) {
}
