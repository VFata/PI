/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller.validate;

import br.senac.tads.housebay.db.DAOEmpresa;
import br.senac.tads.housebay.exception.EmpresaException;
import br.senac.tads.housebay.model.Empresa;
import java.util.ArrayList;
import java.util.List;

public class ValidateEmpresa {
    private final static String ERRO = "Erro na Validação.";
    
    public static boolean create(Empresa empresa) 
            throws EmpresaException {
        List<String> errors = new ArrayList();
        
        if (empresa == null) {
            errors.add("Empresa nulo.");
        } else {
            if(DAOEmpresa.validaCNPJ(empresa.getCnpj()) != 0){
                errors.add("CNPJ existente no sistema.");
            }
            errors.addAll(geraMensagem(empresa));
        }
        
        if (!errors.isEmpty()) {
            throw new EmpresaException(ERRO, errors);
        }
        
        return true;
    }
    
    public static boolean update(Empresa empresa) 
            throws EmpresaException {
        List<String> errors = new ArrayList();
        
        if (empresa == null) {
            errors.add("Empresa nulo.");
        } else {
            if (empresa.getId() == null || empresa.getId() <= 0 ) {
                errors.add("ID vazio.");
            }
            if(DAOEmpresa.validaCNPJ(empresa.getCnpj()) != 0 &&
                    DAOEmpresa.validaCNPJ(empresa.getCnpj()) != empresa.getId()){
                errors.add("CNPJ existente no sistema.");
            }
            errors.addAll(geraMensagem(empresa));
        }
        
        if (!errors.isEmpty()) {
            throw new EmpresaException(ERRO, errors);
        }
        
        return true;
    }
    
    private static List<String> geraMensagem(Empresa empresa) {
        List<String> errors = new ArrayList();
        
        if (empresa.getNome()== null || empresa.getNome().equals("")) {
            errors.add("O campo nome vazio.");
        }
        
        if (empresa.getCnpj()== null || empresa.getCnpj().equals("")) {
            errors.add("O campo cnpj esta vazio.");
        } else if(empresa.getCnpj().length() != 18){
            errors.add("Campo com CNPJ invalido.");
        }
        
        return errors;
    }
    
}
