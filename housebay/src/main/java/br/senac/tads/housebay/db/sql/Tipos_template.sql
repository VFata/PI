/**
 * Tipo de vendável (inicialmente possui os valores serviço ou produto).
 *
 * Author:  ricardo.pleite
 * Created: 26/10/2017
 */
CREATE TABLE tipos (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nome VARCHAR(255) NOT NULL,
    ativo SMALLINT, /*BOOLEAN,*/
    criado TIMESTAMP,
    modificado TIMESTAMP,
    CONSTRAINT unique_tipo_nome UNIQUE (nome)
);

INSERT INTO tipos (nome, ativo, criado, modificado) 
    VALUES  ('Produto', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
            ('Serviço', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);