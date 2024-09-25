CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       email VARCHAR(255) NOT NULL,
                       senha VARCHAR(255) NOT NULL,
                       nome VARCHAR(255),
                       avatar VARCHAR(255),
                       role VARCHAR(50),
                       created_at TIMESTAMP,
                       updated_at TIMESTAMP
);