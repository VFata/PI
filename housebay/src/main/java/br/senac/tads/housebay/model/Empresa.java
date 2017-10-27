
package br.senac.tads.housebay.model;

/**
 *
 * @author igor.soliveira11
 */
public class Empresa extends TabelaDB{
    private String nome;
    private String CNPJ;
    
    public Empresa (){
        super();
    }

    public Empresa(String nome, String CNPJ) {
        super();
        this.nome = nome;
        this.CNPJ = CNPJ;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }
    
}
