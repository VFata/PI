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
    tipo_id INTEGER NOT NULL,
    valor NUMERIC(16,2) NOT NULL,
    codigo_de_barras VARCHAR(26) NOT NULL,
    ativo BOOLEAN,
    criado TIMESTAMP,
    modificado TIMESTAMP,
    CONSTRAINT references_tipo FOREIGN KEY (tipo_id) REFERENCES tipos (id),
    CONSTRAINT unique_codigo_de_barras UNIQUE (codigo_de_barras)
);
