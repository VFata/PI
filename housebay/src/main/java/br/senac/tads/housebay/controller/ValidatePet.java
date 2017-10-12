/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.exception.PetException;
import br.senac.tads.housebay.model.Pet;

/**
 *
 * @author diego.matsuki
 */
public class ValidatePet {
    public static boolean create(Pet pet) 
            throws PetException {
        String erro = "Erro: ";
        
        erro = geraMensagem(pet, erro);
        
        if (!erro.equals("Erro: ")) {
            throw new PetException(erro);
        }
        
        return true;
    }
    
    public static boolean update(Pet pet) 
            throws PetException {
        String erro = "Erro: ";
        
        if (pet.getId() == null || pet.getId() <= 0 ) {
            erro += "\nId vazio.";
        }
        
        erro = geraMensagem(pet, erro);
        
        if (!erro.equals("Erro: ")) {
            throw new PetException(erro);
        }
        
        return true;
    }
    
    private static String geraMensagem(Pet pet, String mensagem) {
        String erro = "";
        
        if (pet == null) {
            erro += "\nPet nulo.";
        }
        if (pet.getNome() == null || pet.getNome().equals("")) {
            erro += "\nNome vazio.";
        }
        if (pet.getDescricao()== null || pet.getDescricao().equals("")) {
            erro += "\nDescrição vazia.";
        }
        
        return erro;
    }
}
