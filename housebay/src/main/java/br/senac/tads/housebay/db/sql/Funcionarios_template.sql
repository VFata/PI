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
