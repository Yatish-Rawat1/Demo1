package com.example.notificationservice.consumer;

import com.example.notificationservice.event.NotificationSentEvent;
import com.example.notificationservice.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class OrderEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);
    private final KafkaTemplate<String, NotificationSentEvent> kafkaTemplate;

    public OrderEventConsumer(KafkaTemplate<String, NotificationSentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * This consumer simulates notification handling.
     *
     * Learning hook:
     * - Add retry topic or dead-letter topic for failures.
     * - Add idempotency store to avoid duplicate notification side effects.
     */
    @KafkaListener(topics = "order.events", groupId = "notification-service")
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("Received OrderCreatedEvent: orderId={}, user={}, item={}",
                event.orderId(), event.createdBy(), event.item());

        NotificationSentEvent sentEvent = new NotificationSentEvent(
                event.orderId(),
                event.createdBy(),
                "EMAIL",
                Instant.now(),
                "SENT"
        );

        kafkaTemplate.send("notification.events", event.orderId(), sentEvent);
        log.info("Published NotificationSentEvent for orderId={}", event.orderId());
    }
}
