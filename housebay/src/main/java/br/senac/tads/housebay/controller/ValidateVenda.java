/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.exception.VendaException;
import br.senac.tads.housebay.model.Venda;
import java.util.HashMap;
import java.util.Map;

public class ValidateVenda {    
    
    private final static String ERRO = "Erro na Validação.";
    
    
    public static boolean create(Venda venda) 
            throws VendaException {
        Map errors = new HashMap();
        
        if (venda == null) {
            //erro += "\nVenda nulo.";
            errors.put(Venda.class.getCanonicalName() + "_empty", "Venda nulo.");
        } else {
            //erro = geraMensagem(venda, erro);
            errors.putAll(geraMensagem(venda));
        }
        if (!errors.isEmpty()) {
            throw new VendaException(ERRO, errors);
        }
        /*
        if (!erro.equals(ERRO)) {
            throw new VendaException(erro);
        }
        */
        
        return true;
    }
    
    public static boolean update(Venda venda) 
            throws VendaException {
        Map errors = new HashMap();
        
        if (venda == null) {
            //erro += "\nVenda nulo.";
            errors.put(Venda.class.getCanonicalName() + "_empty", "Venda nulo.");
        } else {
            if (venda.getId() == null || venda.getId() <= 0 ) {
                //erro += "\nId vazio.";
                errors.put(Venda.ID + "_empty", "ID vazio.");
            }
            //erro = geraMensagem(venda, erro);
            errors.putAll(geraMensagem(venda));
        }
        
        if (!errors.isEmpty()) {
            throw new VendaException(ERRO, errors);
        }
        /*
        if (!erro.equals(ERRO)) {
            throw new VendaException(erro);
        }
        */
        
        return true;
    }
    
    private static Map geraMensagem(Venda venda) {
        Map errors = new HashMap(); 

        if (venda.getCliente()== null) {
            //erro += "\nNome vazio.";
            errors.put(Venda.CLIENTE + "_empty", "Campo cliente vazio.");
        }
        
        if (venda.getEmpresa()== null) {
            //erro += "\nNome vazio.";
            errors.put(Venda.EMPRESA + "_empty", "Campo empresa vazio");
        }
        
        
        
        return errors;
    }
    
}
