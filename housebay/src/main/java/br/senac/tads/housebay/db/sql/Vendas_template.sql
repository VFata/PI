/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  antonio.cmajunior
 * Created: 25/10/2017
 */

CREATE TABLE vendas(
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    cliente_id INTEGER,
    empresa_id INTEGER,
    ativo BOOLEAN,
    criado TIMESTAMP,
    modificado TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES Clientes (id),
    FOREIGN KEY (empresa_id) REFERENCES Empresa (id)
);
