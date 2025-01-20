CREATE TABLE IF NOT EXISTS password_reset_tokens
(
    id         SERIAL    PRIMARY KEY,
    token      UUID      NOT NULL UNIQUE DEFAULT GEN_RANDOM_UUID(),
    user_id    INT       NOT NULL REFERENCES users(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
