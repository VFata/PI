
package br.senac.tads.housebay.model;

/**
 *
 * @author igor.soliveira11
 */
public class Tipo extends TabelaDB{
    
    private String nome;
    
    public Tipo(){
        super();
    }

    public Tipo(String nome) {
        super();
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }               
    
}
