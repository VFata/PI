package br.senac.tads.housebay.model;

//Classe relacionada com a tabela Funcionario do Banco de Dados...
import java.util.GregorianCalendar;

public class Funcionario extends TabelaDB {

    private String nome;
    private GregorianCalendar datanascimento;
    private String telefone;
    private String cpf;
    private String email;
    private String cargo;

    public Funcionario() {
        super();
    }

    public Funcionario(String nome, GregorianCalendar datanascimento, String telefone, String cpf, String email, String cargo) {
        super();
        this.nome = nome;
        this.datanascimento = datanascimento;
        this.telefone = telefone;
        this.cpf = cpf;
        this.email = email;
        this.cargo = cargo;
    }

    public String getNome() {
        return nome;
    }

    public GregorianCalendar getDatanascimento() {
        return datanascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getCargo() {
        return cargo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDatanascimento(GregorianCalendar datanascimento) {
        this.datanascimento = datanascimento;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

}
