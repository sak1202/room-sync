# Load tests
Install k6, start dependencies and API, then run `k6 run load-tests/listings.js` from repository root.

This is a smoke/load-test starting point, not a 1M RPS test. Run distributed generators from multiple machines and capture server, Redis, database, Kafka and network saturation. Do not jump directly to extreme traffic against a laptop or shared environment.
