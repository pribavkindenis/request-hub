CREATE TABLE IF NOT EXISTS app_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS role (
    id INT PRIMARY KEY,
    role VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS privilege (
    id INT PRIMARY KEY,
    privilege VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS app_user_role (
    app_user_id BIGINT REFERENCES app_user (id),
    role_id INT REFERENCES role (id)
);

CREATE TABLE IF NOT EXISTS role_privilege (
    role_id INT REFERENCES role (id),
    privilege_id INT REFERENCES privilege (id)
);
