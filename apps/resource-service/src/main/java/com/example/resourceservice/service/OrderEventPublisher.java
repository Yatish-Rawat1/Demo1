package com.example.resourceservice.service;

import com.example.resourceservice.event.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventPublisher {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderEventPublisher(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publish order events so downstream services can react asynchronously.
     */
    public void publish(OrderCreatedEvent event) {
        kafkaTemplate.send("order.events", event.orderId(), event);
    }
}
