CREATE TABLE IF NOT EXISTS destination_lists (
    id                                   SERIAL     PRIMARY KEY,
    day                                  INT        NOT NULL,
    availabel_transportation_list_binary VARCHAR(4) NOT NULL DEFAULT '1000',
    travel_plan_id                       INT NOT NULL REFERENCES travel_plans(id),
);
