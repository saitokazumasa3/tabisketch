CREATE TABLE IF NOT EXISTS new_email_verification_tokens (
    uuid       UUID         PRIMARY KEY DEFAULT GEN_RANDOM_UUID(),
    new_email  VARCHAR(254) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
    user_id    INT          NOT NULL REFERENCES users(id),
);
