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

INSERT INTO HOUSEBAY.FUNCIONARIOS (NOME, DATA_NASCIMENTO, TELEFONE, CPF, EMAIL, HASH_SENHA, CARGO, EMPRESA_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Mila Trodel', '1970-05-22 00:00:00.0', '98765-4321', '123.456.789-20', 'dir@astec.com', '$2a$10$FBfNKSOw1zAQVpT6Ooqwj.yEuRVdtYVpdlhFuSishQL6Dzo/MdGkK', 1, 1, 1, '2017-11-15 22:59:22.42', '2017-12-08 18:28:06.51');
INSERT INTO HOUSEBAY.FUNCIONARIOS (NOME, DATA_NASCIMENTO, TELEFONE, CPF, EMAIL, HASH_SENHA, CARGO, EMPRESA_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Seu Francisco', '1962-07-23 00:00:00.0', '96385-2741', '147.258.369-85', 'vend@astec.com', '$2a$10$T2zlG6y5cAiZ2U2T5O0czOn9zWglHz35e0o9.MOpFoL9cl8p1f7n2', 1, 1, 1, '2017-11-15 23:01:19.02', '2017-12-08 18:28:58.138');
INSERT INTO HOUSEBAY.FUNCIONARIOS (NOME, DATA_NASCIMENTO, TELEFONE, CPF, EMAIL, HASH_SENHA, CARGO, EMPRESA_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Dona Amélia', '1970-01-01 00:00:00.0', '95162-7843', '159.267.348-95', 'sup@astec.com', '$2a$10$VqSMlkZSHzTMAdtmxDpUrOM7Uy0U2SNLApsE8O3JvX.hGEkPW.Fv.', 1, 1, 1, '2017-11-15 23:03:23.127', '2017-12-08 18:29:23.818');
INSERT INTO HOUSEBAY.FUNCIONARIOS (NOME, DATA_NASCIMENTO, TELEFONE, CPF, EMAIL, HASH_SENHA, CARGO, EMPRESA_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Philadélphio', '1970-01-01 00:00:00.0', '95184-3627', '654.987.321-50', 'back@astec.com', '$2a$10$brW4l5m8J4v4RVLCtHwkTOEClxitlYIuoi9I.qEqZjVMUYlZlifmO', 1, 1, 1, '2017-11-15 23:04:36.082', '2017-12-08 18:30:59.777');
INSERT INTO HOUSEBAY.FUNCIONARIOS (NOME, DATA_NASCIMENTO, TELEFONE, CPF, EMAIL, HASH_SENHA, CARGO, EMPRESA_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Enzo Artur', '2000-03-09 00:00:00.0', '9876-5456', '989.745.615-33', 'enzo@astec.com', '$2a$10$mRZx1c.SRdbO8j8oBmOtVOt.yCtDOa/g9XWLPzz.vv0tJ97ZkgojW', 2, 2, 1, '2017-12-08 18:32:18.679', '2017-12-08 18:32:18.679');
INSERT INTO HOUSEBAY.FUNCIONARIOS (NOME, DATA_NASCIMENTO, TELEFONE, CPF, EMAIL, HASH_SENHA, CARGO, EMPRESA_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Sophia Laura ', '2000-07-18 00:00:00.0', '8941-2948', '484.468.465-05', 'sofia@astec.com', '$2a$10$xefoM06CBX6Z9Z0u3wznGuq7e0J0dlfyM4a8QyJaOC4prJd0z60ne', 2, 3, 1, '2017-12-08 18:36:14.12', '2017-12-08 18:36:14.12');

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

INSERT INTO HOUSEBAY.CLIENTES (NOME, DATA_NASCIMENTO, TELEFONE, CPF, EMAIL, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Vinicius Fata', '2001-09-11 00:00:00.0', '4468-6151', '987.654.321-56', 'fatinha@gmail.com', 1, '2017-12-08 18:15:47.131', '2017-12-08 18:15:47.131');
INSERT INTO HOUSEBAY.CLIENTES (NOME, DATA_NASCIMENTO, TELEFONE, CPF, EMAIL, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Antonio Carlos Junior', '1998-04-15 00:00:00.0', '98765-4895', '465.132.798-52', 'carlinhos@hotmail.com.br', 1, '2017-12-08 18:17:56.5', '2017-12-08 18:40:17.341');
INSERT INTO HOUSEBAY.CLIENTES (NOME, DATA_NASCIMENTO, TELEFONE, CPF, EMAIL, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Ricardo Leite ', '1996-12-16 00:00:00.0', '9864-5156', '779.846.615-13', 'leitinho@yahoo.br', 1, '2017-12-08 18:20:54.48', '2017-12-08 18:20:54.48');
INSERT INTO HOUSEBAY.CLIENTES (NOME, DATA_NASCIMENTO, TELEFONE, CPF, EMAIL, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Igor Júnior', '2001-05-02 00:00:00.0', '9874-6513', '964.456.135-13', 'spiderpig@bol.com.br', 1, '2017-12-08 18:22:51.741', '2017-12-08 18:22:51.741');


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

INSERT INTO HOUSEBAY.PETS (NOME, DESCRICAO, CLIENTE_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Fifi', 'Cadelinha Poodle branca', 1, 1, '2017-12-08 18:15:47.141', '2017-12-08 18:15:47.141');
INSERT INTO HOUSEBAY.PETS (NOME, DESCRICAO, CLIENTE_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('James Rex', 'Cachorro Pug caramelo', 2, 1, '2017-12-08 18:17:56.503', '2017-12-08 18:40:17.351');
INSERT INTO HOUSEBAY.PETS (NOME, DESCRICAO, CLIENTE_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Nina', 'Gata Siamesa idosa.', 3, 1, '2017-12-08 18:20:54.486', '2017-12-08 18:20:54.486');
INSERT INTO HOUSEBAY.PETS (NOME, DESCRICAO, CLIENTE_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Lorde', 'Gato persa', 4, 1, '2017-12-08 18:22:51.743', '2017-12-08 18:22:51.743');


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

INSERT INTO HOUSEBAY.VENDAVEIS (NOME, DESCRICAO, ESTOQUE, TIPO, VALOR, CODIGO_DE_BARRAS, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Cama para cachorro', 'Cama extremamente confortável e prática;', 120, 0, 89.90, '3100338', 1, '2017-12-08 17:52:08.574', '2017-12-08 17:52:08.574');
INSERT INTO HOUSEBAY.VENDAVEIS (NOME, DESCRICAO, ESTOQUE, TIPO, VALOR, CODIGO_DE_BARRAS, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Ração Canina 15kg', 'Ração 15kg Cães Filhotes de Raças Grandes', 529, 0, 209.99, '71803', 1, '2017-12-08 17:55:03.863', '2017-12-08 17:55:03.863');
INSERT INTO HOUSEBAY.VENDAVEIS (NOME, DESCRICAO, ESTOQUE, TIPO, VALOR, CODIGO_DE_BARRAS, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Esquilo de brinquedo para gatos', 'Brinquedo de pelúcia com toque macio para momentos de carinho', 89, 0, 77.99, '1291423', 1, '2017-12-08 17:56:54.557', '2017-12-08 17:56:54.557');
INSERT INTO HOUSEBAY.VENDAVEIS (NOME, DESCRICAO, ESTOQUE, TIPO, VALOR, CODIGO_DE_BARRAS, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Ração Úmida de Atum e Camarão para Gatos - 70 g', 'É um alimento completo e balanceado, feito 100% com carnes frescas, sem adição de corante', 2500, 0, 12.90, '3105996', 1, '2017-12-08 17:59:02.685', '2017-12-08 17:59:02.685');
INSERT INTO HOUSEBAY.VENDAVEIS (NOME, DESCRICAO, ESTOQUE, TIPO, VALOR, CODIGO_DE_BARRAS, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Banho animais de pequeno porte', 'Banho de animais de pequeno porte com pelo curto', 0, 1, 45.00, '', 1, '2017-12-08 18:02:36.551', '2017-12-08 18:05:02.167');
INSERT INTO HOUSEBAY.VENDAVEIS (NOME, DESCRICAO, ESTOQUE, TIPO, VALOR, CODIGO_DE_BARRAS, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Tosa higienica', 'Tosa higienica com máquina', 0, 1, 30.00, '', 1, '2017-12-08 18:06:37.58', '2017-12-08 18:06:37.58');
INSERT INTO HOUSEBAY.VENDAVEIS (NOME, DESCRICAO, ESTOQUE, TIPO, VALOR, CODIGO_DE_BARRAS, ATIVO, CRIADO, MODIFICADO) 
	VALUES ('Atendimento veterinario', 'Consulta veterinária completa, inclui alguns remédios básicos.', 0, 1, 120.00, '', 1, '2017-12-08 18:09:30.888', '2017-12-08 18:09:30.888');



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

INSERT INTO HOUSEBAY.VENDAS (CLIENTE_ID, EMPRESA_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES (4, 1, 1, '2017-12-08 18:24:55.238', '2017-12-08 18:24:55.238');
INSERT INTO HOUSEBAY.VENDAS (CLIENTE_ID, EMPRESA_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES (2, 2, 1, '2017-12-08 18:39:00.669', '2017-12-08 18:39:00.669');
INSERT INTO HOUSEBAY.VENDAS (CLIENTE_ID, EMPRESA_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES (3, 3, 1, '2017-12-08 18:42:45.075', '2017-12-08 18:42:45.075');
INSERT INTO HOUSEBAY.VENDAS (CLIENTE_ID, EMPRESA_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES (1, 3, 1, '2017-12-08 18:43:46.821', '2017-12-08 18:43:46.821');


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

INSERT INTO HOUSEBAY.VENDA_VENDAVEIS (QUANTIDADE, VALOR_TOTAL, VENDA_ID, VENDAVEL_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES (2, 155.98, 1, 3, 1, '2017-12-08 18:24:55.242', '2017-12-08 18:24:55.242');
INSERT INTO HOUSEBAY.VENDA_VENDAVEIS (QUANTIDADE, VALOR_TOTAL, VENDA_ID, VENDAVEL_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES (2, 240.00, 2, 7, 1, '2017-12-08 18:39:00.676', '2017-12-08 18:39:00.676');
INSERT INTO HOUSEBAY.VENDA_VENDAVEIS (QUANTIDADE, VALOR_TOTAL, VENDA_ID, VENDAVEL_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES (1, 89.90, 3, 1, 1, '2017-12-08 18:42:45.077', '2017-12-08 18:42:45.077');
INSERT INTO HOUSEBAY.VENDA_VENDAVEIS (QUANTIDADE, VALOR_TOTAL, VENDA_ID, VENDAVEL_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES (1, 209.99, 3, 2, 1, '2017-12-08 18:42:45.079', '2017-12-08 18:42:45.079');
INSERT INTO HOUSEBAY.VENDA_VENDAVEIS (QUANTIDADE, VALOR_TOTAL, VENDA_ID, VENDAVEL_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES (1, 45.00, 4, 5, 1, '2017-12-08 18:43:46.823', '2017-12-08 18:43:46.823');
INSERT INTO HOUSEBAY.VENDA_VENDAVEIS (QUANTIDADE, VALOR_TOTAL, VENDA_ID, VENDAVEL_ID, ATIVO, CRIADO, MODIFICADO) 
	VALUES (1, 30.00, 4, 6, 1, '2017-12-08 18:43:46.824', '2017-12-08 18:43:46.824');