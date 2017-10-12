
package br.senac.tads.housebay.model;

import java.util.GregorianCalendar;

/**
 *
 * @author Igor
 */
public class Cliente extends TabelaDB{
    private String nome;
    private GregorianCalendar dataNascimento;
    private String telefone;
    private String cpf;
    private String email;
    private Pet animal;
    
    public Cliente(){
        super();        
    }

    public Cliente(String nome, GregorianCalendar dataNascimento, String telefone, String cpf, String email, Pet animal) {
        super();
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.cpf = cpf;
        this.email = email;
        this.animal = animal;
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

    public Pet getAnimal() {
        return animal;
    }

    public void setAnimal(Pet animal) {
        this.animal = animal;
    }
    
    
    
    
    
    
}
