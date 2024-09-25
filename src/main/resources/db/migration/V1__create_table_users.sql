CREATE TABLE users
(
    id UUID PRIMARY KEY,
    email      VARCHAR(255) NOT NULL,
    senha      VARCHAR(255) NOT NULL,
    nome       VARCHAR(255),
    avatar     VARCHAR(255),
    role       VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (id, email, senha, nome, avatar, role, created_at, updated_at)
VALUES
    (UUID(), 'johndoe@example.com', '$2a$12$.vJXcKfXZBGCI.DCECuY1eBfDw8lOxuYv2V9CiTlDXQ3XxNPljN3W', 'John Doe', 'https://avatar.iran.liara.run/username?username=John+Doe', 'ROLE_ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (id, email, senha, nome, avatar, role, created_at, updated_at)
VALUES
    (UUID(), 'janedoe@example.com', '$2a$12$qcwlpdtM/dtbgR91p1Jrj.iQlAF5rj5eBRJtRn4BvDhcssKb3812q', 'Jane Doe', 'https://avatar.iran.liara.run/username?username=John+Doe', 'ROLE_USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



