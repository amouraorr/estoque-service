CREATE TABLE IF NOT EXISTS estoque (
                                       id BIGSERIAL PRIMARY KEY,
                                       sku VARCHAR(255) NOT NULL UNIQUE,
    quantidade_disponivel INTEGER NOT NULL
    );