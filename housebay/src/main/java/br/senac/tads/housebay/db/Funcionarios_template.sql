/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  j.anjos
 * Created: 17/10/2017
 */

CREATE TABLE funcionarios (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nome VARCHAR(255) NOT NULL,
    dataNascimento TIMESTAMP NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL,
    cargo_id INTEGER,
    ativo BOOLEAN,
    criado TIMESTAMP,
    modificado TIMESTAMP,
    FOREIGN KEY (cargo_id) REFERENCES Cargo (id)
);
