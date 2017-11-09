/**
 * Author:  ricardo.pleite
 * Created: 26/10/2017
 */
CREATE TABLE empresas (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nome VARCHAR(255) NOT NULL,
    cnpj varchar(50) NOT NULL,
    ativo SMALLINT, /*BOOLEAN,*/
    criado TIMESTAMP,
    modificado TIMESTAMP,
    CONSTRAINT unique_empresas_nome UNIQUE (nome),
    CONSTRAINT unique_empresas_cnpj UNIQUE (cnpj)
);
