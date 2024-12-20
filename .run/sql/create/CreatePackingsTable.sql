CREATE TABLE IF NOT EXISTS packings
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(64) NOT NULL,
    plan_id INT         NOT NULL,
    FOREIGN KEY (plan_id) REFERENCES plans (id)
);
