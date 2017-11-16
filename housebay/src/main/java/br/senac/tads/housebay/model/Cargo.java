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
    DIRETORIA(1), VENDEDOR(2), SUPORTE(3), BACKOFFICE(4);
    
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
            case DIRETORIA:
                return "Diretoria";
            case VENDEDOR:
                return "Vendedor";
            case SUPORTE:
                return "Suporte";
            case BACKOFFICE:
                return "BackOffice";
            default:
                return null;
        }
    }
    
    public static Cargo getCargo(int value) {
        switch(value) {
            case(1):
                return DIRETORIA;
            case(2):
                return VENDEDOR;
            case(3):
                return SUPORTE;
            case(4):
                return BACKOFFICE;
            default:
                return null;
        }
    }
    
}
