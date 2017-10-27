package br.senac.tads.housebay.model;

//Classe relacionada com a tabela Funcionario do Banco de Dados...

import java.util.GregorianCalendar;

public class Funcionario extends TabelaDB {
    
    public final static String NOME = "nome";
    public final static String DATA_NASCIMENTO = "data nascimento";
    public final static String TELEFONE = "telefone";
    public final static String CPF = "cpf";
    public final static String EMAIL = "email";
    public final static String CARGO_ID = "cargo id";
    public final static String SENHA = "senha";
    public final static String SALT = "salt";
    
    private String nome;
    private GregorianCalendar dataNascimento;
    private String telefone;
    private String cpf;
    private String email;
    private String senha;
    private String salt;
    private Long cargoId;

    public Funcionario() {
        super();
    }

    public Funcionario(String nome, GregorianCalendar dataNascimento, String telefone, String cpf, String email, String senha, String salt, Long cargoId) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.salt = salt;
        this.cargoId = cargoId;
    }

    public Long getCargoId() {
        return cargoId;
    }

    public void setCargoId(Long cargoId) {
        this.cargoId = cargoId;
    }

  
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public GregorianCalendar getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(GregorianCalendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  
}
