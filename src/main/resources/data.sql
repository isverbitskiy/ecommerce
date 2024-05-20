CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(255),
    email    VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role     VARCHAR(255)
);
INSERT INTO users (id, name, email, password, role)
VALUES (1, 'Test User 1', 'test1@example.com', 'password', 'USER');
INSERT INTO users (id, name, email, password, role)
VALUES (2, 'Admin User 1', 'admin1@example.com', 'password', 'ADMIN');