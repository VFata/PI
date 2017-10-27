package br.senac.tads.housebay.model;

import java.util.ArrayList;

/**
 *
 * @author igor.soliveira11
 */
public class Venda extends TabelaDB {

    private long clienteId;
    private long produtoId;
    private ArrayList<relacao> list = new ArrayList();

    public Venda() {
        super();
    }

    public Venda(long cliente_id, long produto_id) {
        super();
        this.clienteId = cliente_id;
        this.produtoId = produto_id;
    }

    public long getClienteId() {
        return clienteId;
    }

    public void setClienteId(long clienteId) {
        this.clienteId = clienteId;
    }

    public long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(long produtoId) {
        this.produtoId = produtoId;
    }

    public ArrayList<relacao> getList() {
        return list;
    }

    public void setList(ArrayList<relacao> list) {
        this.list = list;
    }

    private class relacao {

        private long vendavelId;
        private int quantidade;

        public relacao(long vendavelId, int quantidade) {
            this.vendavelId = vendavelId;
            this.quantidade = quantidade;
        }

        public long getVendavelId() {
            return vendavelId;
        }

        public void setVendavelId(long vendavelId) {
            this.vendavelId = vendavelId;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }
    }

}
