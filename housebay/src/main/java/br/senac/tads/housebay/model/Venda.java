package br.senac.tads.housebay.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author igor.soliveira11
 */
public class Venda extends TabelaDB {

    public static final String CLIENTE = "cliente";
    public static final String EMPRESA = "empresa";
    public static final String CARRINHO = "carrinho";
    
    private Cliente cliente;
    private Empresa empresa;
    private List<Relacao> carrinho;

    public Venda() {
        super();
        this.carrinho = new ArrayList();
    }

    public Venda(Cliente cliente, Empresa empresa) {
        this.cliente = cliente;
        this.empresa = empresa;
        this.carrinho = new ArrayList();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    public List<Relacao> getCarrinho() {
        return carrinho;
    }
    public void setCarrinho(List<Relacao> carrinho) {
        this.carrinho = carrinho;
    }
    public void addCarrinho(Relacao relacao) {
        carrinho.add(relacao);
    }
    
    public int getQuantidade() {
        int quantidade = 0;
        for (Relacao rel : this.carrinho) {
            quantidade += rel.getQuantidade();
        }
        return quantidade;
    }
    
    public int getValorTotal() {
        int valor = 0;
        for (Relacao rel : this.carrinho) {
            valor += rel.getValorTotal();
        }
        return valor;
    }
    
    public String getFormatValorTotal() {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", decimalFormatSymbols);
        return "R$" + decimalFormat.format(getValorTotal());
    }
    
    
    public static class Relacao {
        private Vendavel vendavel;
        private int quantidade;
        private double valorTotal;

        public Relacao() {
        }
        
        public Relacao(Vendavel vendavel, int quantidade, double valorTotal) {
            this.vendavel = vendavel;
            this.quantidade = quantidade;
            this.valorTotal = valorTotal;
        }

        public Vendavel getVendavel() {
            return vendavel;
        }
        public void setVendavel(Vendavel vendavel) {
            this.vendavel = vendavel;
        }
        
        public int getQuantidade() {
            return quantidade;
        }
        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        public double getValorTotal() {
            return valorTotal;
        }
        public void setValorTotal(double valorTotal) {
            this.valorTotal = valorTotal;
        }
        public String getFormatValorTotal() {
            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
            decimalFormatSymbols.setDecimalSeparator(',');
            decimalFormatSymbols.setGroupingSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", decimalFormatSymbols);
            return "R$" + decimalFormat.format(getValorTotal());
        }
    }
}
