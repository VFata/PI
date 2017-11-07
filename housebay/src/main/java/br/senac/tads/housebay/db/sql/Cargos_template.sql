/**
 * Cargos ocupados por funcionários (inicialmente possui os valores gerente, vendedor, suporte e ti).
 */

CREATE TABLE cargos (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nome VARCHAR(255) NOT NULL,
    ativo BOOLEAN,
    criado TIMESTAMP,
    modificado TIMESTAMP,
    CONSTRAINT unique_nome UNIQUE (nome)
);

INSERT INTO cargos (nome, ativo, criado, modificado) VALUES ('Gerente', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO cargos (nome, ativo, criado, modificado) VALUES ('Vendedor', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO cargos (nome, ativo, criado, modificado) VALUES ('Suporte', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO cargos (nome, ativo, criado, modificado) VALUES ('T.I.', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);