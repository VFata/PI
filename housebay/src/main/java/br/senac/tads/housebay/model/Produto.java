package br.senac.tads.housebay.model;

/**
 *
 * @author Igor
 */
public class Produto extends TabelaDB {

    private String produto;
    private String tipo;
    private String valor;
    private String codigoDeBarras;

    public Produto() {
        super();
    }

    public Produto(String produto, String tipo, String valor, String codigoDeBarras) {
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }
    
    
}
