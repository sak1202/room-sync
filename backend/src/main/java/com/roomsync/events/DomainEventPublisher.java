package com.roomsync.events;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class DomainEventPublisher {
    private final KafkaTemplate<String, Object> kafka;
    public DomainEventPublisher(KafkaTemplate<String,Object> kafka) { this.kafka=kafka; }
    public void publish(String key, Object event) { kafka.send("roomsync.domain-events", key, event); }
}
