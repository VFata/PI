
package br.senac.tads.housebay.model;

/**
 *
 * @author igor.soliveira11
 */
public class Produto extends Vendavel {
    public final static String CODIGO_DE_BARRAS = "codigo de barras";
    public final static String ESTOQUE = "estoque";
    
    private double estoque;
    private String codigoDeBarras;
       
    public Produto (){
        super();
    }

    public Produto(String nome, String descricao, int estoque, double valor, String codigoDeBarras, Tipo tipo) {
        super(nome, descricao, valor, tipo);
        this.estoque = estoque;
        this.codigoDeBarras = codigoDeBarras;
    
}
