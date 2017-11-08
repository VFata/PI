package br.senac.tads.housebay.model;

/**
 *
 * @author igor.soliveira11
 */
public class Servico extends Vendavel {
      
    public Servico  (){
        super(Tipo.SERVICO);
    }
    
    public Servico(String nome, String descricao, double valor) {
        super(nome, descricao, valor, Tipo.SERVICO);
    }
}
