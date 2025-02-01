CREATE TABLE IF NOT EXISTS start_places (
    id                  SERIAL PRIMARY KEY,
    google_map_place_id TEXT   NOT NULL,
    departure_time      TIME   NOT NULL,
    destination_list_id INT    NOT NULL
);
