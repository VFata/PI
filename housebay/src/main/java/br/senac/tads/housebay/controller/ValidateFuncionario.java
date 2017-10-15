/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.exception.FuncionarioException;
import br.senac.tads.housebay.model.Funcionario;


public class ValidateFuncionario {
    
    public static boolean create(Funcionario funcionario) 
            throws FuncionarioException {
        String erro = "Erro: ";
        
        erro = geraMensagem(funcionario, erro);
        
        if (!erro.equals("Erro: ")) {
            throw new FuncionarioException(erro);
        }
        
        return true;
    }
    
    public static boolean update(Funcionario funcionario) 
            throws FuncionarioException {
        String erro = "Erro: ";
        
        if (funcionario.getId() == null || funcionario.getId() <= 0 ) {
            erro += "\nId vazio.";
        }
        
        erro = geraMensagem(funcionario, erro);
        
        if (!erro.equals("Erro: ")) {
            throw new FuncionarioException(erro);
        }
        
        return true;
    }
    
    private static String geraMensagem(Funcionario funcionario, String mensagem) {
        String erro = "";
        
        if (funcionario == null) {
            erro += "\nPet nulo.";
        }
        if (funcionario.getNome() == null || funcionario.getNome().equals("")) {
            erro += "\nNome vazio.";
        }
        if (funcionario.getCpf()== null || funcionario.getCpf().equals("")) {
            erro += "\nCPF vazio.";
        }
        if (funcionario.getDatanascimento()== null || funcionario.getDatanascimento().equals("")) {
            erro += "\nDataNascimento vazio.";
        }
        if (funcionario.getEmail()== null || funcionario.getEmail().equals("")) {
            erro += "\nEmail vazio.";
        }
        if (funcionario.getTelefone()== null || funcionario.getTelefone().equals("")) {
            erro += "\nTelefone vazio.";
        }
        
        return erro;
    }
    
}
