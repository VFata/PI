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
    
    private String produto;
    private String tipo;
    private double valor;

    public Vendavel() {
        super();
    }

    public Vendavel(String produto, String tipo, double valor) {
        super();
        this.produto = produto;
        this.tipo = tipo;
        this.valor = valor;

    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
