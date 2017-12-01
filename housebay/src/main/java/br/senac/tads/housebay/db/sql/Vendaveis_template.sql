/**
 *
 *
 * Author:  j.anjos
 * Created: 17/10/2017
 */

CREATE TABLE vendaveis (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(2048) NOT NULL,
    estoque INTEGER NOT NULL,
    tipo INTEGER NOT NULL,
    valor NUMERIC(16,2) NOT NULL,
    codigo_de_barras VARCHAR(26) NOT NULL,
    ativo SMALLINT,/*BOOLEAN,*/
    criado TIMESTAMP,
    modificado TIMESTAMP/*
    CONSTRAINT unique_vendaveis_codigo_de_barras UNIQUE (codigo_de_barras)*/ 
);
