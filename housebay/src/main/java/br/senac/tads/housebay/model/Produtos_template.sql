/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  j.anjos
 * Created: 17/10/2017
 */

CREATE TABLE produtos (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    produto VARCHAR(255) NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    valor NUMERIC(16,2) NOT NULL,
    codigoDeBarras VARCHAR(2048) NOT NULL,
    ativo BOOLEAN,
    criado TIMESTAMP,
    modificado TIMESTAMP
);
