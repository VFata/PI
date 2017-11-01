/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  ricardo.pleite
 * Created: 26/10/2017
 */
CREATE TABLE venda_vendaveis(
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    quantidade INTEGER NOT NULL,
    venda_id INTEGER NOT NULL,
    vendavel_id INTEGER NOT NULL,
    ativo BOOLEAN,
    criado TIMESTAMP,
    modificado TIMESTAMP,
    CONSTRAINT references_venda FOREIGN KEY (venda_id) REFERENCES vendas (id),
    CONSTRAINT references_vendavel FOREIGN KEY (vendavel_id) REFERENCES vendaveis (id)
);
