# RoomSync — scalable roommate matching platform

A production-oriented starter monorepo with independently deployable Angular and Spring Boot applications.

## Important capacity note
This repository is a foundation, not proof of 1M RPS. Throughput depends on request mix, payload size, cache hit rate, database access patterns, region count, network, compute and SLOs. A credible 1M RPS claim requires a benchmark report against production-like infrastructure. The design keeps stateless API nodes, Redis caching/rate limiting, Kafka event boundaries, PostgreSQL persistence and horizontal scaling in mind.

## Repository
- `frontend/` Angular standalone application, typed API client, theme service, responsive shell and feature modules.
- `backend/` Java 21 / Spring Boot REST API, domain/service/repository separation, validation, error contract, Redis cache and rate limiting, Kafka producer, Flyway schema.
- `infra/` local Docker Compose for PostgreSQL, Redis, Kafka (KRaft), Prometheus and Grafana.
- `load-tests/` k6 smoke/load/stress test examples.
- `docs/` architecture, ADRs, SLOs, deployment and capacity plan.

## Run locally
1. Install Docker, Java 21, Maven 3.9+, Node 20.19+ and npm.
2. Start dependencies: `docker compose -f infra/docker-compose.yml up -d`
3. Backend: `cd backend && mvn spring-boot:run`
4. Frontend: `cd frontend && npm install && npm start`
5. API health: `GET http://localhost:8080/actuator/health`; listings: `GET http://localhost:8080/api/v1/listings`.

Default local database values are in `backend/src/main/resources/application.yml`. Change all secrets and credentials before deploying. Authentication is intentionally not implemented yet, matching the requested build-first workflow; do not expose write endpoints publicly until authentication, authorization, abuse controls and ownership checks are added.

## Engineering principles
- Keep controllers thin; business rules belong in services/domain components.
- DTOs are API contracts; JPA entities never leave the persistence layer.
- Use pagination, bounded inputs and explicit sort allowlists.
- Cache only read-heavy listing queries; invalidate on writes.
- Kafka is for asynchronous side effects, not synchronous correctness.
- Database constraints remain the final guard against invalid state.
- All services are stateless; scale replicas horizontally behind a load balancer.
