package com.example.resourceservice.controller;

import com.example.resourceservice.event.OrderCreatedEvent;
import com.example.resourceservice.service.OrderEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderEventPublisher eventPublisher;

    public OrderController(OrderEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * USER/ADMIN can create orders if token has orders.write scope.
     */
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_orders.write')")
    public Map<String, String> createOrder(@AuthenticationPrincipal Jwt jwt,
                                           @RequestBody Map<String, String> payload) {
        String orderId = UUID.randomUUID().toString();
        String item = payload.getOrDefault("item", "unknown-item");

        eventPublisher.publish(new OrderCreatedEvent(
                orderId,
                jwt.getSubject(),
                item,
                Instant.now()
        ));

        return Map.of(
                "message", "Order created and event published",
                "orderId", orderId,
                "createdBy", jwt.getSubject()
        );
    }

    /**
     * Read endpoint for clients with orders.read.
     */
    @GetMapping("/{orderId}")
    @PreAuthorize("hasAuthority('SCOPE_orders.read')")
    public Map<String, String> getOrder(@PathVariable String orderId) {
        return Map.of(
                "orderId", orderId,
                "status", "CREATED",
                "note", "Mock response. Replace with DB call as exercise."
        );
    }

    /**
     * Admin-only action by scope.
     */
    @PostMapping("/{orderId}/approve")
    @PreAuthorize("hasAuthority('SCOPE_orders.admin')")
    public Map<String, String> approveOrder(@PathVariable String orderId) {
        return Map.of(
                "orderId", orderId,
                "status", "APPROVED",
                "approvedBy", "Token with orders.admin scope"
        );
    }
}
