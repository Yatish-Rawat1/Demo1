# Auth Server

This app is your **OAuth2 Authorization Server**.

## Why this matters
- Central place for login and token issuing.
- Teaches OAuth2 grant types and JWT signing.

## In-memory setup
- Users:
  - `alice/password` → ROLE_USER
  - `admin/password` → ROLE_USER, ROLE_ADMIN
- Clients:
  - `interactive-client/secret` → authorization_code + refresh_token
  - `service-client/service-secret` → client_credentials

## Useful endpoints
- Authorization: `http://localhost:9000/oauth2/authorize`
- Token: `http://localhost:9000/oauth2/token`
- JWK Set: `http://localhost:9000/oauth2/jwks`

## Memory note
Think of this app as a trusted gatekeeper:
1. Validates user/client credentials.
2. Issues signed JWT.
3. Publishes key metadata (JWK) so resource services can verify token signatures.
