
package br.senac.tads.housebay.model;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 *
 * @author Igor
 */

//Classe relacionada com a tabela Clientes do Banco de Dados...
public class Cliente extends TabelaDB{
    private String nome;
    private GregorianCalendar dataNascimento;
    private String telefone;
    private String cpf;
    private String email;
    private ArrayList <Long> id_Pet;
    
    public Cliente(){
        super();        
    }

    public Cliente(String nome, GregorianCalendar dataNascimento, String telefone, String cpf, String email, ArrayList<Long> id_Pet) {
        super();
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.cpf = cpf;
        this.email = email;
        this.id_Pet = id_Pet;
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

    public ArrayList<Long> getId_Pet() {
        return id_Pet;
    }

    public void setId_Pet(ArrayList<Long> id_Pet) {
        this.id_Pet = id_Pet;
    }
    
}

