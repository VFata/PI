/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.exception.FuncionarioException;
import br.senac.tads.housebay.model.Funcionario;
import java.util.HashMap;
import java.util.Map;


public class ValidateFuncionario {private final static String ERRO = "Erro na Validação.";
    
    
    public static boolean create(Funcionario funcionario) 
            throws FuncionarioException {
        Map errors = new HashMap();
        
        if (funcionario == null) {
            //erro += "\nFuncionario nulo.";
            errors.put(Funcionario.class.getCanonicalName() + "_empty", "Funcionario nulo.");
        } else {
            //erro = geraMensagem(funcionario, erro);
            errors.putAll(geraMensagem(funcionario));
        }
        if (!errors.isEmpty()) {
            throw new FuncionarioException(ERRO, errors);
        }
        /*
        if (!erro.equals(ERRO)) {
            throw new FuncionarioException(erro);
        }
        */
        
        return true;
    }
    
    public static boolean update(Funcionario funcionario) 
            throws FuncionarioException {
        Map errors = new HashMap();
        
        if (funcionario == null) {
            //erro += "\nFuncionario nulo.";
            errors.put(Funcionario.class.getCanonicalName() + "_empty", "Funcionario nulo.");
        } else {
            if (funcionario.getId() == null || funcionario.getId() <= 0 ) {
                //erro += "\nId vazio.";
                errors.put(Funcionario.ID + "_empty", "ID vazio.");
            }
            //erro = geraMensagem(funcionario, erro);
            errors.putAll(geraMensagem(funcionario));
        }
        
        if (!errors.isEmpty()) {
            throw new FuncionarioException(ERRO, errors);
        }
        /*
        if (!erro.equals(ERRO)) {
            throw new FuncionarioException(erro);
        }
        */
        
        return true;
    }
    
    private static Map geraMensagem(Funcionario funcionario) {
        Map errors = new HashMap(); 
        
        if (funcionario.getNome() == null || funcionario.getNome().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Funcionario.NOME + "_empty", "Nome vazio.");
        }
        if (funcionario.getCpf()== null || funcionario.getCpf().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Funcionario.CPF + "_empty", "Nome vazio.");
        }
        if (funcionario.getDataNascimento()== null || funcionario.getDataNascimento().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Funcionario.DATA_NASCIMENTO + "_empty", "Nome vazio.");
        }
        if (funcionario.getEmail()== null || funcionario.getEmail().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Funcionario.EMAIL + "_empty", "Nome vazio.");
        }
        if (funcionario.getSalt()== null) {
            //erro += "\nNome vazio.";
            errors.put(Funcionario.SALT + "_empty", "Nome vazio.");
        }
        if (funcionario.getSenha()== null || funcionario.getSenha().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Funcionario.SENHA + "_empty", "Nome vazio.");
        }
        if (funcionario.getTelefone()== null || funcionario.getTelefone().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Funcionario.TELEFONE + "_empty", "Nome vazio.");
        }

        
        return errors;
    }
    
}
