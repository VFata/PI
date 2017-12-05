    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller.validate;

import br.senac.tads.housebay.db.DAOVendavel;
import br.senac.tads.housebay.exception.ProdutoException;
import br.senac.tads.housebay.model.Produto;
import java.util.ArrayList;
import java.util.List;

public class ValidateProduto {
    private final static String ERRO = "Erro na Validação.";
        
    public static boolean create(Produto produto) 
            throws ProdutoException {
        List<String> errors = new ArrayList();
        
        if (produto == null) {
            errors.add("Produto nulo.");
        } else {
            if (DAOVendavel.validaCodigoDeBarras(produto.getCodigoDeBarras()) != 0) {
                errors.add("Código de Barras existente no sistema.");
            }
            
            errors.addAll(geraMensagem(produto));
        }
        if (!errors.isEmpty()) {
            throw new ProdutoException(ERRO, errors);
        }
        
        return true;
    }
    
    public static boolean update(Produto produto) 
            throws ProdutoException {
        List<String> errors = new ArrayList();
        
        if (produto == null) {
            errors.add("Produto nulo.");
        } else {
            if (produto.getId() == null || produto.getId() <= 0 ) {
                errors.add("ID vazio.");
            }
            
            if (DAOVendavel.validaCodigoDeBarras(produto.getCodigoDeBarras()) != 0 && 
                    DAOVendavel.validaCodigoDeBarras(produto.getCodigoDeBarras()) != produto.getId()) {
                errors.add("Código de Barras existente no sistema.");
            }
            
            errors.addAll(geraMensagem(produto));
        }
        
        if (!errors.isEmpty()) {
            throw new ProdutoException(ERRO, errors);
        }
        
        return true;
    }
    
    private static List<String> geraMensagem(Produto produto) {
        List<String> errors = new ArrayList(); 
        
        if (produto.getNome()== null || produto.getNome().equals("")) {
            errors.add("O campo nome vazio.");
        }
        if (produto.getTipo()== null) {
            errors.add("O campo tipo vazio.");
        }
        if (produto.getEstoque()<= 0) {
            errors.add("O campo estoque vazio.");
        }
        if (produto.getDescricao()== null || produto.getDescricao().equals("")) {
            errors.add("O campo descrição vazio.");
        }
        if (produto.getCodigoDeBarras()== null || produto.getCodigoDeBarras().equals("")) {
            errors.add("O campo Codigo de Barras esta vazio.");
        }
        if (produto.getValor()== 0) {
            errors.add("O campo valor esta vazio.");
        }
        
        return errors;
    }    
}
