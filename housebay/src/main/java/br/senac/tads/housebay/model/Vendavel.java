package br.senac.tads.housebay.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 *
 * @author diego
 */
public abstract class Vendavel extends TabelaDB {
    public final static String NOME = "nome";
    public final static String DESCRICAO = "descricao";
    public final static String VALOR = "valor";
    public final static String TIPO = "tipo";

    private String nome;
    private String descricao;
    private double valor;
    private final Tipo tipo;

    public Vendavel(Tipo tipo) {
        super();
        this.tipo = tipo;
    }

    public Vendavel(String nome, String descricao, double valor, Tipo tipo) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }
    public String getFormatValor() {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", decimalFormatSymbols);
        return "R$" + decimalFormat.format(valor);
    }
    public void setValor(double valor) {
        this.valor = valor;
    }

    public Tipo getTipo() {
        return tipo;
    }
}
