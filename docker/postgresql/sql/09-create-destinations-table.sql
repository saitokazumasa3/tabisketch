CREATE TABLE IF NOT EXISTS destinations (
    id                    SERIAL PRIMARY KEY,
    google_map_place_id   TEXT   NOT NULL,
    order                 INT    NOT NULL,
    start_time            TIME   NOT NULL,
    specified_start_time  TIME,
    transportation_method transportations,
    duration              INT    NOT NULL,
    budget                INT    NOT NULL,
    destination_list_id   INT    NOT NULL
);
