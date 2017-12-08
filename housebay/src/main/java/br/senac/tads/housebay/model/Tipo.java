/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.model;

/**
 *
 * @author diego
 */
public enum Tipo {
    PRODUTO(0), SERVICO(1);
    
    private final int value;
    
    private Tipo (int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }

    @Override public String toString() {
        if (this == PRODUTO) {
            return "Produto";
        } else if(this == SERVICO) {
            return "Serviço";
        }
        return null;
    }
    
    public static Tipo getTipo(int value) {
        switch(value) {
            case(0):
                return PRODUTO;
            case(1):
                return SERVICO;
            default:
                return null;
        }
    }
}
