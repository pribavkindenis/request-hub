CREATE TABLE IF NOT EXISTS request (
    id BIGSERIAL PRIMARY KEY,
    app_user_id BIGINT REFERENCES app_user (id),
    header VARCHAR(100),
    description VARCHAR(500)
);

