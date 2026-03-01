# Resource Service

This app demonstrates:
- JWT validation as a Resource Server
- Method-level authorization with OAuth scopes
- Event publication to Kafka

## Endpoint summary
- `POST /api/orders` requires `orders.write`
- `GET /api/orders/{id}` requires `orders.read`
- `POST /api/orders/{id}/approve` requires `orders.admin`

## Why scopes here instead of roles?
In OAuth2 APIs, scopes are commonly used as API permissions.
You can still map custom claims like `roles` and combine both patterns.

## Memory Notes
- **Token accepted?** Signature + expiry + issuer/audience checks.
- **Token authorized?** Scope/authority checked at method level.
- **Business action?** Publish domain event (`order.events`) for async processing.
