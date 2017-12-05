/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller.validate;

import br.senac.tads.housebay.exception.VendavelException;
import br.senac.tads.housebay.model.Vendavel;
import java.util.ArrayList;
import java.util.List;

public class ValidateVendavel {
    private final static String ERRO = "Erro na Validação.";
    
    
    public static boolean create(Vendavel vendavel) 
            throws VendavelException {
        List<String> errors = new ArrayList();
        
        if (vendavel == null) {
            errors.add("Vendavel nulo.");
        } else {
            errors.addAll(geraMensagem(vendavel));
        }
        if (!errors.isEmpty()) {
            throw new VendavelException(ERRO, errors);
        }
        
        return true;
    }
    
    public static boolean update(Vendavel vendavel) 
            throws VendavelException {
        List<String> errors = new ArrayList();
        
        if (vendavel == null) {
            errors.add("Vendavel nulo.");
        } else {
            if (vendavel.getId() == null || vendavel.getId() <= 0 ) {
                errors.add("ID vazio.");
            }
            errors.addAll(geraMensagem(vendavel));
        }
        
        if (!errors.isEmpty()) {
            throw new VendavelException(ERRO, errors);
        }
        
        return true;
    }
    
    private static List<String> geraMensagem(Vendavel vendavel) {
        List<String> errors = new ArrayList(); 
        
        if (vendavel.getNome()== null || vendavel.getNome().equals("")) {
            errors.add("O campo nome esta vazio.");
        }
        if (vendavel.getTipo()== null) {
            errors.add("O campo tipo esta vazio.");
        }
        if (vendavel.getValor()== 0) {
            errors.add("O campo valor esta vazio.");
        }
        if (vendavel.getDescricao()== null || vendavel.getDescricao().equals("")) {
            errors.add("O campo descrição esta vazio.");
        }      
        
        return errors;
    }
    
}
