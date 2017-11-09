/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.exception.ClienteException;
import br.senac.tads.housebay.exception.ClienteException;
import br.senac.tads.housebay.model.Cliente;
import br.senac.tads.housebay.model.Cliente;
import java.util.HashMap;
import java.util.Map;


public class ValidateCliente {
    
    private final static String ERRO = "Erro na Validação.";
    
    
    public static boolean create(Cliente cliente) 
            throws ClienteException {
        Map errors = new HashMap();
        
        if (cliente == null) {
            //erro += "\nCliente nulo.";
            errors.put(Cliente.class.getCanonicalName() + "_empty", "Cliente nulo.");
        } else {
            //erro = geraMensagem(cliente, erro);
            errors.putAll(geraMensagem(cliente));
        }
        if (!errors.isEmpty()) {
            throw new ClienteException(ERRO, errors);
        }
        /*
        if (!erro.equals(ERRO)) {
            throw new ClienteException(erro);
        }
        */
        
        return true;
    }
    
    public static boolean update(Cliente cliente) 
            throws ClienteException {
        Map errors = new HashMap();
        
        if (cliente == null) {
            //erro += "\nCliente nulo.";
            errors.put(Cliente.class.getCanonicalName() + "_empty", "Cliente nulo.");
        } else {
            if (cliente.getId() == null || cliente.getId() <= 0 ) {
                //erro += "\nId vazio.";
                errors.put(Cliente.ID + "_empty", "ID vazio.");
            }
            //erro = geraMensagem(cliente, erro);
            errors.putAll(geraMensagem(cliente));
        }
        
        if (!errors.isEmpty()) {
            throw new ClienteException(ERRO, errors);
        }
        /*
        if (!erro.equals(ERRO)) {
            throw new ClienteException(erro);
        }
        */
        
        return true;
    }
    
    private static Map geraMensagem(Cliente cliente) {
        Map errors = new HashMap(); 
        
        if (cliente.getNome() == null || cliente.getNome().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Cliente.NOME + "_empty", "Campo nome vazio.");
        }
        if (cliente.getEmail()== null || cliente.getEmail().equals("")) {
            //erro += "\nDescrição vazia.";
            errors.put(Cliente.EMAIL + "_empty", "Campo email vazio.");
        }
        if (cliente.getTelefone()== null || cliente.getTelefone().equals("")) {
            //erro += "\nDescrição vazia.";
            errors.put(Cliente.TELEFONE + "_empty", "Campo telefone vazio.");
        }
        if (cliente.getCpf()== null || cliente.getCpf().equals("")) {
            //erro += "\nDescrição vazia.";
            errors.put(Cliente.CPF + "_empty", "Campo cpf vazio.");
        }
        if (cliente.getDataNascimento()== null) {
            //erro += "\nDescrição vazia.";
            errors.put(Cliente.DATA_NASCIMENTO + "_empty", "Campo data de nascimento vazio.");
        }
        if (cliente.getPets()== null) {
            //erro += "\nDescrição vazia.";
            errors.put(Cliente.PETS + "_empty", "Campo data de nascimento vazio.");
        }

        
        return errors;
    }
    
}
