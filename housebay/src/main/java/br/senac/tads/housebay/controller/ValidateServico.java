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
    // TODO: check all variables
    
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
        
        if (servico.getDescricao()== null || servico.getDescricao().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Servico.DESCRICAO + "_empty", "Campo descrição Vazio");
        }
        if (servico.getNome()== null || servico.getNome().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Servico.NOME + "_empty", "Campo nome vazio.");
        }
        if (servico.getValor()== 0) {
            //erro += "\nNome vazio.";
            errors.put(Servico.VALOR + "_empty", "Campo valor vazio.");
        }
        if (servico.getTipo()!= null) {
        } else {
            //erro += "\nNome vazio.";
            errors.put(Servico.TIPO + "_empty", "Campo tipo vazio.");
        }

        
        return errors;
    }
    
}
