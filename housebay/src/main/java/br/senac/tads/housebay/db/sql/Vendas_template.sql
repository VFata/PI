/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  antonio.cmajunior
 * Created: 25/10/2017
 */

CREATE TABLE vendas (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    cliente_id INTEGER NOT NULL,
    empresa_id INTEGER NOT NULL,
    ativo SMALLINT NOT NULL,
    criado TIMESTAMP NOT NULL,
    modificado TIMESTAMP NOT NULL,
    CONSTRAINT references_vendas_cliente FOREIGN KEY (cliente_id) REFERENCES clientes (id),
    CONSTRAINT references_vendas_empresa FOREIGN KEY (empresa_id) REFERENCES empresas (id)
);
