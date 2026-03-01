package com.example.notificationservice.event;

import java.time.Instant;

public record NotificationSentEvent(
        String orderId,
        String recipient,
        String channel,
        Instant sentAt,
        String status
) {
}
