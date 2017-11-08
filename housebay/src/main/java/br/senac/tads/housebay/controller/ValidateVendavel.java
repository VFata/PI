/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.exception.VendavelException;
import br.senac.tads.housebay.model.Vendavel;
import java.util.HashMap;
import java.util.Map;

public class ValidateVendavel {    
    
    private final static String ERRO = "Erro na Validação.";
    
    
    public static boolean create(Vendavel vendavel) 
            throws VendavelException {
        Map errors = new HashMap();
        
        if (vendavel == null) {
            //erro += "\nVendavel nulo.";
            errors.put(Vendavel.class.getCanonicalName() + "_empty", "Vendavel nulo.");
        } else {
            //erro = geraMensagem(vendavel, erro);
            errors.putAll(geraMensagem(vendavel));
        }
        if (!errors.isEmpty()) {
            throw new VendavelException(ERRO, errors);
        }
        /*
        if (!erro.equals(ERRO)) {
            throw new VendavelException(erro);
        }
        */
        
        return true;
    }
    
    public static boolean update(Vendavel vendavel) 
            throws VendavelException {
        Map errors = new HashMap();
        
        if (vendavel == null) {
            //erro += "\nVendavel nulo.";
            errors.put(Vendavel.class.getCanonicalName() + "_empty", "Vendavel nulo.");
        } else {
            if (vendavel.getId() == null || vendavel.getId() <= 0 ) {
                //erro += "\nId vazio.";
                errors.put(Vendavel.ID + "_empty", "ID vazio.");
            }
            //erro = geraMensagem(vendavel, erro);
            errors.putAll(geraMensagem(vendavel));
        }
        
        if (!errors.isEmpty()) {
            throw new VendavelException(ERRO, errors);
        }
        /*
        if (!erro.equals(ERRO)) {
            throw new VendavelException(erro);
        }
        */
        
        return true;
    }
    
    private static Map geraMensagem(Vendavel vendavel) {
        Map errors = new HashMap(); 
        
        if (vendavel.getNome()== null || vendavel.getNome().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Vendavel.NOME + "_empty", "Campo nome vazio.");
        }
        if (vendavel.getProduto()== null || vendavel.getProduto().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Vendavel.PRODUTO + "_empty", "Campo produto vazio.");
        }
        if (vendavel.getTipoId()== null || vendavel.getTipoId().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Vendavel.TIPO + "_empty", "Campo tipo vazio.");
        }
        if (vendavel.getValor()== 0) {
            //erro += "\nNome vazio.";
            errors.put(Vendavel.VALOR + "_empty", "Campo valor vazio.");
        }
        if (vendavel.getDescricao()== null || vendavel.getDescricao().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Vendavel.DESCRICAO + "_empty", "Campo descrição vazio.");
        }      
        
        return errors;
    }
    
}
