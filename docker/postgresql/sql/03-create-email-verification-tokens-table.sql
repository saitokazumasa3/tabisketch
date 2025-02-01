CREATE TABLE IF NOT EXISTS email_verification_tokens (
    uuid       UUID      PRIMARY KEY DEFAULT GEN_RANDOM_UUID(),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    user_id    INT       NOT NULL REFERENCES users(id),
);
