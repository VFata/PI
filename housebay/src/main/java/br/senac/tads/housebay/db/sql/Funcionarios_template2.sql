/**
 * Author:  j.anjos
 * Created: 17/10/2017
 */
CREATE TABLE funcionarios (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nome VARCHAR(255) NOT NULL,
    data_nascimento TIMESTAMP NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    cpf VARCHAR(15) NOT NULL,
    email VARCHAR(255) NOT NULL,
    hash_senha VARCHAR(255) NOT NULL,
    cargo INTEGER NOT NULL,
    ativo SMALLINT, /*BOOLEAN,*/
    criado TIMESTAMP,
    modificado TIMESTAMP,
    /*CONSTRAINT references_cargo FOREIGN KEY (cargo_id) REFERENCES cargos (id),*/
    CONSTRAINT unique_funcionarios_cpf UNIQUE (cpf),
    CONSTRAINT unique_funcionarios_email UNIQUE (email)
);
