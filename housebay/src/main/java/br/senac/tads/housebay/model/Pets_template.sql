/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Diego
 * Created: Oct 8, 2017
 */

CREATE TABLE pets (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nome VARCHAR(255),
    descricao VARCHAR(2048),
    ativo BOOLEAN
);