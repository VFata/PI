/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller.validate;

import br.senac.tads.housebay.exception.PetException;
import br.senac.tads.housebay.model.Pet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author diego.matsuki
 */
public class ValidatePet {
    private final static String ERRO = "Erro na Validação.";
    
    
    public static boolean create(Pet pet) 
            throws PetException {
        List<String> errors = new ArrayList();
        
        if (pet == null) {
            errors.add("Pet nulo.");
        } else {
            errors.addAll(geraMensagem(pet));
        }
        if (!errors.isEmpty()) {
            throw new PetException(ERRO, errors);
        }
        
        return true;
    }
    
    public static boolean update(Pet pet) 
            throws PetException {
        List<String> errors = new ArrayList();
        
        if (pet == null) {
            errors.add("Pet nulo.");
        } else {
            if (pet.getId() == null || pet.getId() <= 0 ) {
                errors.add("ID vazio.");
            }
            if (pet.getClienteId()== null || pet.getClienteId().equals("")) {
                errors.add("Campo cliente vazia.");
            }
            errors.addAll(geraMensagem(pet));
        }
        
        if (!errors.isEmpty()) {
            throw new PetException(ERRO, errors);
        }
        
        return true;
    }
    
    private static List<String> geraMensagem(Pet pet) {
        List<String> errors = new ArrayList(); 
        
        if (pet.getNome() == null || pet.getNome().equals("")) {
            errors.add("O campo nome PET esta vazio.");
        }
        if (pet.getDescricao() == null || pet.getDescricao().equals("")) {
            errors.add("O campo descrição PET esta vazio");
        }
        
        return errors;
    }
}
