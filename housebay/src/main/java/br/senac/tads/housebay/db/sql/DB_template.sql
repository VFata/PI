/**
 * Author:  Diego
 * Created: Dec 8, 2017
 */


/**
 * Author:  ricardo.pleite
 * Created: 26/10/2017
 */
CREATE TABLE empresas (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nome VARCHAR(255) NOT NULL,
    cnpj varchar(50) NOT NULL,
    ativo SMALLINT NOT NULL,
    criado TIMESTAMP NOT NULL,
    modificado TIMESTAMP NOT NULL,
    CONSTRAINT unique_empresas_nome UNIQUE (nome),
    CONSTRAINT unique_empresas_cnpj UNIQUE (cnpj)
);

INSERT INTO empresas (nome, cnpj, ativo, criado, modificado)
	VALUES ('São Paulo - Matriz', '12.345.678/0001-64', 1, '2017-11-15 22:59:22.42', '2017-11-15 22:59:22.42');

INSERT INTO empresas (nome, cnpj, ativo, criado, modificado)
	VALUES ('Recife - Filial', '12.345.678/0002-27', 1, '2017-11-15 22:59:22.42', '2017-11-15 22:59:22.42');

INSERT INTO empresas (nome, cnpj, ativo, criado, modificado)
	VALUES ('Porto Alegre - Filial', '12.345.678/0003-93', 1, '2017-11-15 22:59:22.42', '2017-11-15 22:59:22.42');

/**
 * Author:  j.anjos
 * Created: 17/10/2017
 */
CREATE TABLE funcionarios (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nome VARCHAR(255) NOT NULL,
    data_nascimento TIMESTAMP NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    cpf VARCHAR(15) NOT NULL,
    email VARCHAR(255) NOT NULL,
    hash_senha VARCHAR(255) NOT NULL,
    cargo INTEGER NOT NULL,
    empresa_id INTEGER NOT NULL,
    ativo SMALLINT NOT NULL,
    criado TIMESTAMP NOT NULL,
    modificado TIMESTAMP NOT NULL,
    CONSTRAINT references_funcionarios_empresa FOREIGN KEY (empresa_id) REFERENCES empresas (id),
    CONSTRAINT unique_funcionarios_cpf UNIQUE (cpf),
    CONSTRAINT unique_funcionarios_email UNIQUE (email)
);

/* Senha: "senhasenha" */
INSERT INTO funcionarios (nome, data_nascimento, telefone, cpf, email, hash_senha, cargo, empresa_id, ativo, criado, modificado)
	VALUES ('Direta', '1970-01-01 00:01:00.0', '98765-4321', '123.456.789-20', 'dir@astec.com', '$2a$10$FBfNKSOw1zAQVpT6Ooqwj.yEuRVdtYVpdlhFuSishQL6Dzo/MdGkK', 1, 1, 1, '2017-11-15 22:59:22.42', '2017-11-15 22:59:22.42');
INSERT INTO funcionarios (nome, data_nascimento, telefone, cpf, email, hash_senha, cargo, empresa_id, ativo, criado, modificado)
	VALUES ('Venda', '1970-01-01 00:01:00.0', '96385-2741', '147.258.369-85', 'vend@astec.com', '$2a$10$T2zlG6y5cAiZ2U2T5O0czOn9zWglHz35e0o9.MOpFoL9cl8p1f7n2', 2, 1, 1, '2017-11-15 23:01:19.02', '2017-11-15 23:01:19.02');
INSERT INTO funcionarios (nome, data_nascimento, telefone, cpf, email, hash_senha, cargo, empresa_id, ativo, criado, modificado)
	VALUES ('Sup', '1970-01-01 00:01:00.0', '95162-7843', '159.267.348-95', 'sup@astec.com', '$2a$10$VqSMlkZSHzTMAdtmxDpUrOM7Uy0U2SNLApsE8O3JvX.hGEkPW.Fv.', 3, 1, 1, '2017-11-15 23:03:23.127', '2017-11-15 23:03:23.127');
INSERT INTO funcionarios (nome, data_nascimento, telefone, cpf, email, hash_senha, cargo, empresa_id, ativo, criado, modificado)
	VALUES ('Back', '1970-01-01 00:01:00.0', '95184-3627', '654.987.321-50', 'back@astec.com', '$2a$10$brW4l5m8J4v4RVLCtHwkTOEClxitlYIuoi9I.qEqZjVMUYlZlifmO', 4, 1, 1, '2017-11-15 23:04:36.082', '2017-11-15 23:04:36.082');


/**
 * Author:  j.anjos
 * Created: 17/10/2017
 */
CREATE TABLE clientes (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nome VARCHAR(255) NOT NULL,
    data_nascimento TIMESTAMP NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    cpf VARCHAR(15) NOT NULL,
    email VARCHAR(255) NOT NULL,
    ativo SMALLINT NOT NULL,
    criado TIMESTAMP NOT NULL,
    modificado TIMESTAMP NOT NULL,
    CONSTRAINT unique_clientes_cpf UNIQUE (cpf)
);


/**
 * Author:  Diego
 * Created: Oct 8, 2017
 */
CREATE TABLE pets (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(2048) NOT NULL,
    cliente_id INTEGER NOT NULL ,
    ativo SMALLINT NOT NULL,
    criado TIMESTAMP NOT NULL,
    modificado TIMESTAMP NOT NULL,
    CONSTRAINT references_pets_cliente FOREIGN KEY (cliente_id) REFERENCES clientes (id)
);


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
    ativo SMALLINT NOT NULL,
    criado TIMESTAMP NOT NULL,
    modificado TIMESTAMP NOT NULL
);


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


/**
 * Author:  ricardo.pleite
 * Created: 26/10/2017
 */
CREATE TABLE venda_vendaveis(
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    quantidade INTEGER NOT NULL,
    valor_total NUMERIC(16,2) NOT NULL, 
    venda_id INTEGER NOT NULL,
    vendavel_id INTEGER NOT NULL,
    ativo SMALLINT NOT NULL,
    criado TIMESTAMP NOT NULL,
    modificado TIMESTAMP NOT NULL,
    CONSTRAINT references_venda FOREIGN KEY (venda_id) REFERENCES vendas (id),
    CONSTRAINT references_vendavel FOREIGN KEY (vendavel_id) REFERENCES vendaveis (id)
);