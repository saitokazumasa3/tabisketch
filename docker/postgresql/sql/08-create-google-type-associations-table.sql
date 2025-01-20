CREATE TABLE IF NOT EXISTS google_type_associations
(
    place_type_id INT NOT NULL,
    place_id      INT NOT NULL,
    PRIMARY KEY (place_type_id, place_id),
    FOREIGN KEY (place_type_id) REFERENCES google_place_types (id),
    FOREIGN KEY (place_id) REFERENCES google_places (id)
);
