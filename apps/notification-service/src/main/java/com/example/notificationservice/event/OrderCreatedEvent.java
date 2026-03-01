package com.example.notificationservice.event;

import java.time.Instant;

public record OrderCreatedEvent(
        String orderId,
        String createdBy,
        String item,
        Instant createdAt
) {
}
