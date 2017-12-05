/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller.validate;

import br.senac.tads.housebay.exception.ServicoException;
import br.senac.tads.housebay.model.Servico;
import java.util.ArrayList;
import java.util.List;

public class ValidateServico {
    private final static String ERRO = "Erro na Validação.";
    
    public static boolean create(Servico servico) 
            throws ServicoException {
        List<String> errors = new ArrayList();
        
        if (servico == null) {
            errors.add("Servico nulo.");
        } else {
            errors.addAll(geraMensagem(servico));
        }
        if (!errors.isEmpty()) {
            throw new ServicoException(ERRO, errors);
        }
        
        return true;
    }
    
    public static boolean update(Servico servico) 
            throws ServicoException {
        List<String> errors = new ArrayList();
        
        if (servico == null) {
            errors.add("Servico nulo.");
        } else {
            if (servico.getId() == null || servico.getId() <= 0 ) {
                errors.add("ID vazio.");
            }
            errors.addAll(geraMensagem(servico));
        }
        
        if (!errors.isEmpty()) {
            throw new ServicoException(ERRO, errors);
        }
        
        return true;
    }
    
    private static List<String> geraMensagem(Servico servico) {
        List<String> errors = new ArrayList(); 
        
        if (servico.getDescricao()== null || servico.getDescricao().equals("")) {
            errors.add("O campo descrição esta Vazio");
        }
        if (servico.getNome()== null || servico.getNome().equals("")) {
            errors.add("O campo nome esta vazio.");
        }
        if (servico.getValor()== 0) {
            errors.add("O campo valor esta vazio.");
        }
        if (servico.getTipo() == null) {
            errors.add("O campo tipo esta vazio.");
        }
        
        return errors;
    }
    
}
