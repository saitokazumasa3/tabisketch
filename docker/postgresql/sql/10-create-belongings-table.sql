CREATE TABLE IF NOT EXISTS belongings (
    id             SERIAL      PRIMARY KEY,
    name           VARCHAR(64) NOT NULL,
    travel_plan_id INT         NOT NULL REFERENCES travel_plans(id),
);
