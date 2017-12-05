/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller.validate;

import br.senac.tads.housebay.exception.VendaException;
import br.senac.tads.housebay.model.Venda;
import java.util.ArrayList;
import java.util.List;

public class ValidateVenda {
    private final static String ERRO = "Erro na Validação.";
    
    public static boolean create(Venda venda) 
            throws VendaException {
        List<String> errors = new ArrayList();
        
        if (venda == null) {
            errors.add("Venda nulo.");
        } else {
            errors.addAll(geraMensagem(venda));
        }
        if (!errors.isEmpty()) {
            throw new VendaException(ERRO, errors);
        }
        
        return true;
    }
    
    public static boolean update(Venda venda) 
            throws VendaException {
        List<String> errors = new ArrayList();
        
        if (venda == null) {
            errors.add("Venda nulo.");
        } else {
            if (venda.getId() == null || venda.getId() <= 0 ) {
                errors.add("ID vazio.");
            }
            errors.addAll(geraMensagem(venda));
        }
        
        if (!errors.isEmpty()) {
            throw new VendaException(ERRO, errors);
        }
        
        return true;
    }
    
    private static List<String> geraMensagem(Venda venda) {
        List<String> errors = new ArrayList(); 

        if (venda.getCliente()== null) {
            errors.add("O campo cliente esta vazio.");
        }        
        if (venda.getEmpresa()== null) {
            errors.add("O campo empresa esta vazio");
        }
        if (venda.getCarrinho().isEmpty()) {
            errors.add("O carrinho de venda esta vazio");
        }
                
        return errors;
    }
    
}
