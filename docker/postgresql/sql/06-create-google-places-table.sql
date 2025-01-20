CREATE TABLE IF NOT EXISTS google_places
(
    id        SERIAL PRIMARY KEY,
    place_id  TEXT UNIQUE      NOT NULL,
    name      VARCHAR(64)      NOT NULL,
    latitude  DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL
);
