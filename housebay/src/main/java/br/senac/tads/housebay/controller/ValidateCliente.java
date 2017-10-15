/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.exception.ClienteException;
import br.senac.tads.housebay.model.Cliente;


public class ValidateCliente {
    
        public static boolean create(Cliente cliente) 
            throws ClienteException {
        String erro = "Erro: ";
        
        erro = geraMensagem(cliente, erro);
        
        if (!erro.equals("Erro: ")) {
            throw new ClienteException(erro);
        }
        
        return true;
    }
    
    public static boolean update(Cliente cliente) 
            throws ClienteException {
        String erro = "Erro: ";
        
        if (cliente.getId() == null || cliente.getId() <= 0 ) {
            erro += "\nId vazio.";
        }
        
        erro = geraMensagem(cliente, erro);
        
        if (!erro.equals("Erro: ")) {
            throw new ClienteException(erro);
        }
        
        return true;
    }
    
    private static String geraMensagem(Cliente cliente, String mensagem) {
        String erro = "";
        
        if (cliente == null) {
            erro += "\nCliente nulo.";
        }
        if (cliente.getNome() == null || cliente.getNome().equals("")) {
            erro += "\nNome vazio.";
        }
        if (cliente.getCpf()== null || cliente.getCpf().equals("")) {
            erro += "\nCPF vazia.";
        }
        if (cliente.getDataNascimento()== null || cliente.getDataNascimento().equals("")) {
            erro += "\nDataNascimento vazia.";
        }
        if (cliente.getEmail()== null || cliente.getEmail().equals("")) {
            erro += "\nEmail vazia.";
        }
        if (cliente.getTelefone()== null || cliente.getTelefone().equals("")) {
            erro += "\nTelefone vazia.";
        }
        
        return erro;
    }
    
}
