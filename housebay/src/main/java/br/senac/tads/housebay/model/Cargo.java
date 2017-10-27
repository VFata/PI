
package br.senac.tads.housebay.model;

/**
 *
 * @author igor.soliveira11
 */
public class Cargo extends TabelaDB {
    
    public final static String NOME = "nome";
        
    private String nome;
    
    public Cargo (){
        super ();
    }

    public Cargo(String nome) {
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
