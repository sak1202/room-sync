# Architecture decision records

## ADR-001: PostgreSQL is source of truth
Relational constraints and transactions suit profiles, listings, memberships and requests. Redis is an optimization, never the source of truth.

## ADR-002: Redis for cache and distributed rate limiting
Cache discovery responses with short TTLs. Rate-limit keys are bounded and expire. For multi-region deployments, revisit consistency and regional limits.

## ADR-003: Kafka for asynchronous workflows
Use events for notifications, analytics and search indexing. Consumers must be idempotent. Use transactional outbox before relying on guaranteed DB-to-Kafka delivery.

## ADR-004: Auth deferred
The initial build omits authentication by request. Before internet exposure, protect all user-specific operations with verified identity and ownership enforcement.
