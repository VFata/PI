package br.senac.tads.housebay.model;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

//Classe relacionada com a tabela Funcionario do Banco de Dados
public class Funcionario extends TabelaDB {
    /*
    public final static String NOME = "nome";
    public final static String DATA_NASCIMENTO = "data nascimento";
    public final static String TELEFONE = "telefone";
    public final static String CPF = "cpf";
    public final static String EMAIL = "email";
    public final static String CARGO = "cargo";
    public final static String SENHA = "senha";
    */
    
    private String nome;
    private GregorianCalendar dataNascimento;
    private String telefone;
    private String cpf;
    private String email;
    private String senha;
    private Cargo cargo;
    private Empresa empresa;

    public Funcionario() {
        super();
    }

    public Funcionario(String nome, GregorianCalendar dataNascimento, String cpf, String email) {
        super();
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.email = email;
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
    public String getInputDataNascimento() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(this.dataNascimento.getTime());
    }
    public String getFormatDataNascimento() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd / MM / yyyy");
        return sdf.format(this.dataNascimento.getTime());
    }
    public void setDataNascimento(GregorianCalendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public void setDataNascimento(long timeInMillis) {
        this.dataNascimento = new GregorianCalendar();
        this.dataNascimento.setTimeInMillis(timeInMillis);
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

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Cargo getCargo() {
        return cargo;
    }
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Empresa getEmpresa() {
        return empresa;
    }
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    
}
