CREATE TABLE tarefas
(
    id           UUID PRIMARY KEY,
    titulo       VARCHAR(255) NOT NULL,
    descricao    VARCHAR(255),
    data_entrega TIMESTAMP,
    prioridade   VARCHAR(50),
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP,
    user_id      UUID,
    FOREIGN KEY (user_id) REFERENCES users (id)
);