/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller.validate;

import br.senac.tads.housebay.db.DAOEmpresa;
import br.senac.tads.housebay.exception.EmpresaException;
import br.senac.tads.housebay.model.Empresa;
import java.util.HashMap;
import java.util.Map;

public class ValidateEmpresa {    
    
    private final static String ERRO = "Erro na Validação.";
    
    
    public static boolean create(Empresa empresa) 
            throws EmpresaException {
        Map errors = new HashMap();
        
        if (empresa == null) {
            //erro += "\nEmpresa nulo.";
            errors.put(Empresa.class.getCanonicalName() + "_empty", "Empresa nulo.");
        } else {
            //erro = geraMensagem(empresa, erro);
            errors.putAll(geraMensagem(empresa));
        }
        if (!errors.isEmpty()) {
            throw new EmpresaException(ERRO, errors);
        }
        /*
        if (!erro.equals(ERRO)) {
            throw new EmpresaException(erro);
        }
        */
        
        return true;
    }
    
    public static boolean update(Empresa empresa) 
            throws EmpresaException {
        Map errors = new HashMap();
        
        if (empresa == null) {
            //erro += "\nEmpresa nulo.";
            errors.put(Empresa.class.getCanonicalName() + "_empty", "Empresa nulo.");
        } else {
            if (empresa.getId() == null || empresa.getId() <= 0 ) {
                //erro += "\nId vazio.";
                errors.put(Empresa.ID + "_empty", "ID vazio.");
            }
            //erro = geraMensagem(empresa, erro);
            errors.putAll(geraMensagem(empresa));
        }
        
        if (!errors.isEmpty()) {
            throw new EmpresaException(ERRO, errors);
        }
        /*
        if (!erro.equals(ERRO)) {
            throw new EmpresaException(erro);
        }
        */
        
        return true;
    }
    
    private static Map geraMensagem(Empresa empresa) {
        Map errors = new HashMap(); 
        
        if (empresa.getNome()== null || empresa.getNome().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Empresa.NOME + "_empty", "O campo nome vazio.");
        }
        if (empresa.getCnpj()== null || empresa.getCnpj().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Empresa.CNPJ + "_empty", "O campo cnpj esta vazio.");
        }else if(empresa.getCnpj().length() != 18){
            errors.put(Empresa.CNPJ + "_empty", "Campo com CNPJ invalido.");
        }else if(DAOEmpresa.validaCNPJ(empresa.getCnpj())){
            
            errors.put(Empresa.CNPJ + "_empty", "CNPF existente no sistema.");
        }
        
        
        
        
        return errors;
    }
    
}
