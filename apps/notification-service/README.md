# Notification Service

This app demonstrates Kafka consumer/producer workflows.

## What it does
1. Consumes `OrderCreatedEvent` from `order.events`.
2. Simulates notification sending.
3. Publishes `NotificationSentEvent` to `notification.events`.

## Important production concepts to practice
- Retry policy + dead-letter topic
- Idempotent consumer pattern
- Correlation IDs and distributed tracing
- Outbox pattern in producer service

## Memory Note
Resource service handles synchronous API security.
Notification service handles asynchronous side effects.
This separation keeps systems scalable and resilient.
