package br.senac.tads.housebay.model;

/**
 *
 * @author Igor
 */
//Classe relacionada com a tabela Vendavel do Banco de Dados...
public class Vendavel extends TabelaDB {

    private String produto;
    private String tipo;
    private double valor;
    private String codigoDeBarras;

    public Vendavel() {
        super();
    }

    public Vendavel(String produto, String tipo, double valor, String codigoDeBarras) {
        super();
        this.produto = produto;
        this.tipo = tipo;
        this.valor = valor;
        this.codigoDeBarras = codigoDeBarras;
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

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }
    
    
}
