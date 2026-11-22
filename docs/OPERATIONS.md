# Operations and production checklist

## Initial SLO proposal
- Availability: 99.9% monthly for public discovery APIs.
- Latency: p95 < 200 ms for cache-hit discovery; p99 < 800 ms (validate under load).
- Error budget: track 5xx, timeouts, saturation and dependency failures separately.

## Before production
- Add OIDC/JWT authentication, authorization and per-resource ownership checks.
- Add secrets manager; rotate DB/Redis/Kafka credentials; enforce TLS everywhere.
- Configure WAF, API gateway, bot protection and per-user/IP rate limits.
- Set resource requests/limits, HPA, PodDisruptionBudgets, topology spread and readiness/liveness probes.
- Configure PostgreSQL backups + point-in-time recovery; test restore drills.
- Configure Redis persistence/HA according to cache vs rate-limit durability needs.
- Configure Kafka replication factor >= 3, min.insync.replicas, retention, DLQ and consumer lag alerts.
- Add OpenTelemetry tracing, structured JSON logs, metrics dashboards and alert runbooks.
- Add dependency vulnerability scans, SBOM, SAST, DAST, container scanning and signed images.
- Run load, soak, spike, failover and chaos tests before claiming capacity.

## Failure behavior
- Redis cache unavailable: bounded fallback to database with concurrency limits; do not allow unbounded DB stampede.
- Kafka unavailable: fail or persist via transactional outbox for events that must not be lost. The starter includes an outbox table but not a dispatcher.
- Database unavailable: fail fast with 503; never report success for uncommitted writes.
- Downstream timeouts: use strict timeouts, bounded retries with jitter, circuit breakers and bulkheads.
