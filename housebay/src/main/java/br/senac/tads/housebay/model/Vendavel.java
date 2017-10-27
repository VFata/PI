package br.senac.tads.housebay.model;

/**
 *
 * @author Igor
 */
//Classe relacionada com a tabela Vendavel do Banco de Dados...
public class Vendavel extends TabelaDB {

    public final static String PRODUTO = "produto";
    public final static String TIPO = "tipo";
    public final static String VALOR = "valor";
    public final static String NOME = "nome";
    public final static String DESCRICAO = "descricao";
    
    private String produto;
    private Long idTipo;
    private double valor;
    private String nome;
    private String descricao;

    public Vendavel() {
        super();
    }

    public Vendavel(String produto, Long idTipo, double valor, String nome, String descricao) {
        super();
        this.produto = produto;
        this.idTipo = idTipo;
        this.valor = valor;
        this.nome = nome;
        this.descricao = descricao;
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

   

    public Long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }

    

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

   

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
