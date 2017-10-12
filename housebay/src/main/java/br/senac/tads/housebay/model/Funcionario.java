
package br.senac.tads.housebay.model;


public class Funcionario {
    
    private String nome;
    private String datanascimento;
    private String telefone;
    private String cpf;
    private String email;
    private String cargo;

    public Funcionario(String nome, String datanascimento, String telefone, String cpf, String email, String cargo) {
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

    public String getDatanascimento() {
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

    public void setDatanascimento(String datanascimento) {
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
