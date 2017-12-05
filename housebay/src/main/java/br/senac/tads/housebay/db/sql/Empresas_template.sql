/**
 * Author:  ricardo.pleite
 * Created: 26/10/2017
 */
CREATE TABLE empresas (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nome VARCHAR(255) NOT NULL,
    cnpj varchar(50) NOT NULL,
    ativo SMALLINT NOT NULL,
    criado TIMESTAMP NOT NULL,
    modificado TIMESTAMP NOT NULL,
    CONSTRAINT unique_empresas_nome UNIQUE (nome),
    CONSTRAINT unique_empresas_cnpj UNIQUE (cnpj)
);

INSERT INTO empresas (nome, cnpj, ativo, criado, modificado)
	VALUES ('Matriz', '12.345.678/0001-64', 1, '2017-11-15 22:59:22.42', '2017-11-15 22:59:22.42');