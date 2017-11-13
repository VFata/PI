/**
 * Cargos ocupados por funcionários (inicialmente possui os valores gerente, vendedor, suporte e ti).
 */

CREATE TABLE cargos (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nome VARCHAR(255) NOT NULL,
    ativo SMALLINT, /*BOOLEAN,*/
    criado TIMESTAMP,
    modificado TIMESTAMP,
    CONSTRAINT unique_cargos_nome UNIQUE (nome)
);

INSERT INTO cargos (nome, ativo, criado, modificado) VALUES ('Gerente', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO cargos (nome, ativo, criado, modificado) VALUES ('Vendedor', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO cargos (nome, ativo, criado, modificado) VALUES ('Suporte', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO cargos (nome, ativo, criado, modificado) VALUES ('T.I.', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);