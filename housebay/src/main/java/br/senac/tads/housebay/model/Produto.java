
package br.senac.tads.housebay.model;

/**
 *
 * @author igor.soliveira11
 */
public class Produto extends Vendavel {
    
    private String codigoDeBarras;
    private int estoque;
    
    public Produto (){
        super();
    }

    public Produto(String codigoDeBarras, int estoque) {
        super();
        this.codigoDeBarras = codigoDeBarras;
        this.estoque = estoque;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
    
    
    
}
