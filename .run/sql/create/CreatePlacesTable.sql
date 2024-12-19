CREATE TABLE IF NOT EXISTS places
(
    id                 SERIAL PRIMARY KEY,
    google_place_id    INT  NOT NULL,
    day_id             INT  NOT NULL,
    budget             INT,
    start_time         TIME NOT NULL,
    end_time           TIME NOT NULL,
    desired_start_time TIME,
    desired_end_time   TIME,
    to_poly_line       TEXT,
    to_travel_time     INT,
    to_transportation  transportations,
    FOREIGN KEY (google_place_id) REFERENCES google_places (id),
    FOREIGN KEY (day_id) REFERENCES days (id)
);
