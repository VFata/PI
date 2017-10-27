/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  j.anjos
 * Created: 17/10/2017
 */

CREATE TABLE vendavel (
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
    FOREIGN KEY (tipo_id) REFERENCES Tipo (id)
);
