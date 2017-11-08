
package br.senac.tads.housebay.model;

/**
 *
 * @author igor.soliveira11
 */
public class Produto extends Vendavel {
    public final static String CODIGO_DE_BARRAS = "codigo de barras";
    public final static String ESTOQUE = "estoque";
    
    private int estoque;
    private String codigoDeBarras;
       
    public Produto (){
        super(Tipo.PRODUTO);
    }

    public Produto(String nome, String descricao, double valor, int estoque, String codigoDeBarras) {
        super(nome, descricao, valor, Tipo.PRODUTO);
        this.estoque = estoque;
        this.codigoDeBarras = codigoDeBarras;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }
}
