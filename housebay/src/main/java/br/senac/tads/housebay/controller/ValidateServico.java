/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.exception.ServicoException;
import br.senac.tads.housebay.model.Servico;
import java.util.HashMap;
import java.util.Map;

public class ValidateServico {    
    
    private final static String ERRO = "Erro na Validação.";
    
    
    public static boolean create(Servico servico) 
            throws ServicoException {
        Map errors = new HashMap();
        
        if (servico == null) {
            //erro += "\nServico nulo.";
            errors.put(Servico.class.getCanonicalName() + "_empty", "Servico nulo.");
        } else {
            //erro = geraMensagem(servico, erro);
            errors.putAll(geraMensagem(servico));
        }
        if (!errors.isEmpty()) {
            throw new ServicoException(ERRO, errors);
        }
        /*
        if (!erro.equals(ERRO)) {
            throw new ServicoException(erro);
        }
        */
        
        return true;
    }
    
    public static boolean update(Servico servico) 
            throws ServicoException {
        Map errors = new HashMap();
        
        if (servico == null) {
            //erro += "\nServico nulo.";
            errors.put(Servico.class.getCanonicalName() + "_empty", "Servico nulo.");
        } else {
            if (servico.getId() == null || servico.getId() <= 0 ) {
                //erro += "\nId vazio.";
                errors.put(Servico.ID + "_empty", "ID vazio.");
            }
            //erro = geraMensagem(servico, erro);
            errors.putAll(geraMensagem(servico));
        }
        
        if (!errors.isEmpty()) {
            throw new ServicoException(ERRO, errors);
        }
        /*
        if (!erro.equals(ERRO)) {
            throw new ServicoException(erro);
        }
        */
        
        return true;
    }
    
    private static Map geraMensagem(Servico servico) {
        Map errors = new HashMap(); 
        /*
        if (servico.getServico()== null || servico.getServico().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Servico.PRODUTO + "_empty", "Nome vazio.");
        }
        */
        
        return errors;
    }
    
}
