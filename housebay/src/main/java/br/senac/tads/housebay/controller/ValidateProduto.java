/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.exception.ProdutoException;
import br.senac.tads.housebay.model.Produto;

public class ValidateProduto {
    
    public static boolean create(Produto produto) 
            throws ProdutoException {
        String erro = "Erro: ";
        
        erro = geraMensagem(produto, erro);
        
        if (!erro.equals("Erro: ")) {
            throw new ProdutoException(erro);
        }
        
        return true;
    }
    
    public static boolean update(Produto produto) 
            throws ProdutoException {
        String erro = "Erro: ";
        
        if (produto.getId() == null || produto.getId() <= 0 ) {
            erro += "\nId vazio.";
        }
        
        erro = geraMensagem(produto, erro);
        
        if (!erro.equals("Erro: ")) {
            throw new ProdutoException(erro);
        }
        
        return true;
    }
    
    private static String geraMensagem(Produto produto, String mensagem) {
        String erro = "";
        
        if (produto == null) {
            erro += "\nProduto nulo.";
        }
        if (produto.getProduto()== null || produto.getProduto().equals("")) {
            erro += "\nProduto vazio.";
        }
        if (produto.getTipo()== null || produto.getTipo().equals("")) {
            erro += "\nTipo vazio.";
        }
        if (produto.getValor()== null || produto.getValor().equals("")) {
            erro += "\nValor vazio.";
        }
        if (produto.getCodigoDeBarras()== null || produto.getCodigoDeBarras().equals("")) {
            erro += "\nCodigoDeVarras vazio.";
        }
        
        return erro;
    }
}
