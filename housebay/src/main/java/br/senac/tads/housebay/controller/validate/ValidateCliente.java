/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller.validate;

import br.senac.tads.housebay.exception.ClienteException;
import br.senac.tads.housebay.model.Cliente;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidateCliente {
    
    private final static String ERRO = "Erro na Validação.";
    
    
    public static boolean create(Cliente cliente) 
            throws ClienteException {
        Map errors = new HashMap();
        
        if (cliente == null) {
            //erro += "\nCliente nulo.";
            errors.put(Cliente.class.getCanonicalName() + "_empty", "Cliente nulo.");
        } else {
            //erro = geraMensagem(cliente, erro);
            errors.putAll(geraMensagem(cliente));
        }
        if (!errors.isEmpty()) {
            throw new ClienteException(ERRO, errors);
        }
        /*
        if (!erro.equals(ERRO)) {
            throw new ClienteException(erro);
        }
        */
        
        return true;
    }
    
    public static boolean update(Cliente cliente) 
            throws ClienteException {
        Map errors = new HashMap();
        
        if (cliente == null) {
            //erro += "\nCliente nulo.";
            errors.put(Cliente.class.getCanonicalName() + "_empty", "Cliente nulo.");
        } else {
            if (cliente.getId() == null || cliente.getId() <= 0 ) {
                //erro += "\nId vazio.";
                errors.put(Cliente.ID + "_empty", "ID vazio.");
            }
            //erro = geraMensagem(cliente, erro);
            errors.putAll(geraMensagem(cliente));
        }
        
        if (!errors.isEmpty()) {
            throw new ClienteException(ERRO, errors);
        }
        /*
        if (!erro.equals(ERRO)) {
            throw new ClienteException(erro);
        }
        */
        
        return true;
    }
    
    private static Map geraMensagem(Cliente cliente) {
        Map errors = new HashMap(); 
        
        if (cliente.getNome() == null || cliente.getNome().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Cliente.NOME + "_empty", "O campo nome esta vazio.");
        }
        
        
        
        Pattern email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher m = email.matcher(cliente.getEmail());  
        if (cliente.getEmail()== null || cliente.getEmail().equals("")) {
            //erro += "\nDescrição vazia.";
            errors.put(Cliente.EMAIL + "_empty", "O campo email esta vazio.");
        }else if(!m.find()){  
            errors.put(Cliente.EMAIL + "_empty", "O campo email esta incorreto.");            
        }
        
        
        
        
        
        if (cliente.getTelefone()== null || cliente.getTelefone().equals("")) {
            //erro += "\nDescrição vazia.";
            errors.put(Cliente.TELEFONE + "_empty", "O campo telefone esta vazio");
        }else if(cliente.getTelefone().length() >= 14){
            
            errors.put(Cliente.TELEFONE + "_empty", "O campo telefone deve conter ate 14 caracteres.");
            
        }else if(cliente.getTelefone().length() <= 8){
            
            errors.put(Cliente.TELEFONE + "_empty", "O campo telefone deve conter no minimo 8 caracteres.");
            
        }
        
        if (cliente.getCpf()== null || cliente.getCpf().equals("")) {
            //erro += "\nDescrição vazia.";
            errors.put(Cliente.CPF + "_empty", "O campo CPF esta Vazio.");
            
        }else if(cliente.getCpf().length() != 14){
            
            errors.put(Cliente.CPF + "_empty", "O campo CPF esta incorreto.");
            
        }
        
        GregorianCalendar data = new GregorianCalendar();   
        if (cliente.getDataNascimento()== null) {
            //erro += "\nDescrição vazia.";
            errors.put(Cliente.DATA_NASCIMENTO + "_empty", "Erro campo data nascimento.");
            
        }else if((data.get(Calendar.YEAR) - cliente.getDataNascimento().get(Calendar.YEAR)) >= 120){
            
            errors.put(Cliente.DATA_NASCIMENTO + "_empty", "Idade limite alcançado");
            
        }else if((data.get(Calendar.YEAR) - cliente.getDataNascimento().get(Calendar.YEAR)) < 16){
            
            errors.put(Cliente.DATA_NASCIMENTO + "_empty", "A Idade minima é de 16 anos");
        }
        
        if (cliente.getPets()== null) {
            //erro += "\nDescrição vazia.";
            errors.put(Cliente.PETS + "_empty", "Campo Pets vazio");
        }

        
        return errors;
    }
    
}
