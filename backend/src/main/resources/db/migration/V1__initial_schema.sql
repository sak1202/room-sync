CREATE TABLE listings (
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR(120) NOT NULL,
  city VARCHAR(80) NOT NULL,
  locality VARCHAR(120) NOT NULL,
  monthly_rent NUMERIC(12,2) NOT NULL CHECK (monthly_rent > 0),
  available_slots INTEGER NOT NULL CHECK (available_slots BETWEEN 1 AND 20),
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_listing_city_active ON listings(city, active);
CREATE INDEX idx_listing_rent ON listings(monthly_rent);
CREATE TABLE outbox_events (
  id UUID PRIMARY KEY,
  aggregate_type VARCHAR(100) NOT NULL,
  aggregate_id VARCHAR(100) NOT NULL,
  event_type VARCHAR(150) NOT NULL,
  payload JSONB NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  published_at TIMESTAMPTZ
);
CREATE INDEX idx_outbox_unpublished ON outbox_events(created_at) WHERE published_at IS NULL;
