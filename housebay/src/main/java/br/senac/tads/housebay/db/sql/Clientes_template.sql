/**
 * Author:  j.anjos
 * Created: 17/10/2017
 */
CREATE TABLE clientes (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nome VARCHAR(255) NOT NULL,
    data_nascimento TIMESTAMP NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    cpf VARCHAR(15) NOT NULL,
    email VARCHAR(255) NOT NULL,
    ativo BOOLEAN,
    criado TIMESTAMP,
    modificado TIMESTAMP,
    CONSTRAINT unique_cpf UNIQUE (cpf)
);
