
package br.senac.tads.housebay.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Igor
 */

//Classe relacionada com a tabela Clientes do Banco de Dados...
public class Cliente extends TabelaDB {
    
    public final static String NOME = "nome";
    public final static String DATA_NASCIMENTO = "data nascimento";
    public final static String TELEFONE = "telefone";
    public final static String CPF = "cpf";
    public final static String EMAIL = "email";
    public final static String PET_ID = "pet id";
    
    private String nome;
    private GregorianCalendar dataNascimento;
    private String telefone;
    private String cpf;
    private String email;
    private List<Pet> pets;

    public Cliente(){
        super();
        this.pets = new ArrayList<>();        
    }

    public Cliente(String nome, GregorianCalendar dataNascimento, String telefone, String cpf, String email) {
        super();
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.cpf = cpf;
        this.email = email;
        this.pets = new ArrayList<>();
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        return sdf.format(this.dataNascimento.getTime());
    }
    public String getFormatDataNascimento() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd / mm / yyyy");
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
    
    public List<Pet> getPets() {
        return pets;
    }
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
    public void addPets(Pet pet) {
        this.pets.add(pet);
    }
}

