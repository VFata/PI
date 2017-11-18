/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller.validate;

import br.senac.tads.housebay.exception.PetException;
import br.senac.tads.housebay.model.Pet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author diego.matsuki
 */
public class ValidatePet {
    private final static String ERRO = "Erro na Validação.";
    
    
    public static boolean create(Pet pet) 
            throws PetException {
        Map errors = new HashMap();
        
        if (pet == null) {
            errors.put(Pet.class.getCanonicalName() + "_empty", "Pet nulo.");
        } else {
            errors.putAll(geraMensagem(pet));
        }
        if (!errors.isEmpty()) {
            throw new PetException(ERRO, errors);
        }
        
        return true;
    }
    
    public static boolean update(Pet pet) 
            throws PetException {
        Map errors = new HashMap();
        
        if (pet == null) {
            errors.put(Pet.class.getCanonicalName() + "_empty", "Pet nulo.");
        } else {
            if (pet.getId() == null || pet.getId() <= 0 ) {
                errors.put(Pet.ID + "_empty", "ID vazio.");
            }
            if (pet.getClienteId()== null || pet.getClienteId().equals("")) {
                errors.put(Pet.CLIENTE_ID + "_empty", "Campo cliente vazia.");
            }
            errors.putAll(geraMensagem(pet));
        }
        
        if (!errors.isEmpty()) {
            throw new PetException(ERRO, errors);
        }
        
        return true;
    }
    
    private static Map geraMensagem(Pet pet) {
        Map errors = new HashMap(); 
        
        if (pet.getNome() == null || pet.getNome().equals("")) {
            errors.put(Pet.NOME + "_empty", "O campo nome PET esta vazio.");
        }
        if (pet.getDescricao() == null || pet.getDescricao().equals("")) {
            errors.put(Pet.DESCRICAO + "_empty", "O campo descrição PET esta vazio");
        }
        
        return errors;
    }
}
