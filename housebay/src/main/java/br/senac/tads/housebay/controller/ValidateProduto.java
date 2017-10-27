/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.exception.ProdutoException;
import br.senac.tads.housebay.model.Produto;
import java.util.HashMap;
import java.util.Map;

public class ValidateProduto {    
    
    private final static String ERRO = "Erro na Validação.";
    
    
    public static boolean create(Produto produto) 
            throws ProdutoException {
        Map errors = new HashMap();
        
        if (produto == null) {
            //erro += "\nProduto nulo.";
            errors.put(Produto.class.getCanonicalName() + "_empty", "Produto nulo.");
        } else {
            //erro = geraMensagem(produto, erro);
            errors.putAll(geraMensagem(produto));
        }
        if (!errors.isEmpty()) {
            throw new ProdutoException(ERRO, errors);
        }
        /*
        if (!erro.equals(ERRO)) {
            throw new ProdutoException(erro);
        }
        */
        
        return true;
    }
    
    public static boolean update(Produto produto) 
            throws ProdutoException {
        Map errors = new HashMap();
        
        if (produto == null) {
            //erro += "\nProduto nulo.";
            errors.put(Produto.class.getCanonicalName() + "_empty", "Produto nulo.");
        } else {
            if (produto.getId() == null || produto.getId() <= 0 ) {
                //erro += "\nId vazio.";
                errors.put(Produto.ID + "_empty", "ID vazio.");
            }
            //erro = geraMensagem(produto, erro);
            errors.putAll(geraMensagem(produto));
        }
        
        if (!errors.isEmpty()) {
            throw new ProdutoException(ERRO, errors);
        }
        /*
        if (!erro.equals(ERRO)) {
            throw new ProdutoException(erro);
        }
        */
        
        return true;
    }
    
    private static Map geraMensagem(Produto produto) {
        Map errors = new HashMap(); 
        
        if (produto.getProduto()== null || produto.getProduto().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Produto.PRODUTO + "_empty", "Nome vazio.");
        }

        
        return errors;
    }
    
}
