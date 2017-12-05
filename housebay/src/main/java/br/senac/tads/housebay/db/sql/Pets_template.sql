/**
 * Author:  Diego
 * Created: Oct 8, 2017
 */
CREATE TABLE pets (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(2048) NOT NULL,
    cliente_id INTEGER NOT NULL ,
    ativo SMALLINT NOT NULL,
    criado TIMESTAMP NOT NULL,
    modificado TIMESTAMP NOT NULL,
    CONSTRAINT references_pets_cliente FOREIGN KEY (cliente_id) REFERENCES clientes (id)
);