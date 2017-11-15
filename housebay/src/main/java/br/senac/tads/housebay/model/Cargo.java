/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.model;

/**
 *
 * @author Diego
 */
public enum Cargo {
    GERENTE(1), VENDEDOR(2), SUPORTE(3), TI(4);
    
    private final int value;
    
    private Cargo (int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    @Override public String toString() {
        if (null == this) return null; 
        else switch (this) {
            case GERENTE:
                return "Gerente";
            case VENDEDOR:
                return "Vendedor";
            case SUPORTE:
                return "Suporte";
            case TI:
                return "TI";
            default:
                return null;
        }
    }
    
    public static Cargo getCargo(int value) {
        switch(value) {
            case(1):
                return GERENTE;
            case(2):
                return VENDEDOR;
            case(3):
                return SUPORTE;
            case(4):
                return TI;
            default:
                return null;
        }
    }
    
}
