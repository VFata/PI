package br.senac.tads.housebay.model;

/**
 *
 * @author Diego
 */
public class Pet extends TabelaDB {

    public static final String NOME = "nome";
    public final static String DESCRICAO = "descricao";
    public final static String CLIENTE_ID = "cliente id";

    private String nome;
    private String descricao;
    private Long clienteID;

    public Pet() {
        super();
    }

    public Pet(String nome, String descricao, Long clienteID) {
        super();
        this.nome = nome;
        this.descricao = descricao;
        this.clienteID = clienteID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getClienteID() {
        return clienteID;
    }

    public void setClienteID(Long clienteID) {
        this.clienteID = clienteID;
    }

}
