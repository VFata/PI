
package br.senac.tads.housebay.model;

/**
 *
 * @author igor.soliveira11
 */
public class Empresa extends TabelaDB{
    
    public final static String NOME = "nome";
    public final static String CNPJ = "cnpj";
    
    private String nome;
    private String cnpj;
    
    public Empresa (){
        super();
    }

    public Empresa(String nome, String CNPJ) {
        super();
        this.nome = nome;
        this.cnpj = CNPJ;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
}
