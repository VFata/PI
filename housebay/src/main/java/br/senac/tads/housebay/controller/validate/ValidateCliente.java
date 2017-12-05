/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller.validate;

import br.senac.tads.housebay.db.DAOCliente;
import br.senac.tads.housebay.exception.ClienteException;
import br.senac.tads.housebay.model.Cliente;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidateCliente {
    
    private final static String ERRO = "Erro na Validação.";
    
    
    public static boolean create(Cliente cliente) 
            throws ClienteException {
        List<String> errors = new ArrayList();
        
        if (cliente == null) {
            errors.add("Cliente nulo.");
        } else {
            if(DAOCliente.validaCPF(cliente.getCpf()) != 0) {
                errors.add("CPF existente no sistema");
            }
            errors.addAll(geraMensagem(cliente));
        }
        
        if (!errors.isEmpty()) {
            throw new ClienteException(ERRO, errors);
        }
        
        return true;
    }
    
    public static boolean update(Cliente cliente) 
            throws ClienteException {
        List<String> errors = new ArrayList();
        
        if (cliente == null) {
            errors.add("Cliente nulo.");
        } else {
            if (cliente.getId() == null || cliente.getId() <= 0 ) {
                errors.add("ID vazio.");
            }
            
            if(DAOCliente.validaCPF(cliente.getCpf()) != 0 &&
                    DAOCliente.validaCPF(cliente.getCpf()) != cliente.getId()) {
                errors.add("CPF existente no sistema");
            }
            
            errors.addAll(geraMensagem(cliente));
        }
        
        if (!errors.isEmpty()) {
            throw new ClienteException(ERRO, errors);
        }
        
        return true;
    }
    
    private static List<String> geraMensagem(Cliente cliente) {
        List<String> errors = new ArrayList(); 
        
        if (cliente.getNome() == null || cliente.getNome().equals("")) {
            errors.add("O campo nome esta vazio.");
        }
        
        Pattern email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher m = email.matcher(cliente.getEmail());
        if (cliente.getEmail()== null || cliente.getEmail().equals("")) {
            errors.add("O campo email esta vazio.");
        } else if(!m.find()){  
            errors.add("O campo email esta incorreto.");
        }
                
        if (cliente.getTelefone()== null || cliente.getTelefone().equals("")) {
            errors.add("O campo telefone esta vazio");
        } else if(cliente.getTelefone().length() >= 14){
            errors.add("O campo telefone deve ter até 14 caracteres.");
        } else if(cliente.getTelefone().length() <= 8){
            errors.add("O campo telefone deve ter no mínimo 8 caracteres.");
        }
        
        if (cliente.getCpf()== null || cliente.getCpf().equals("")) {
            errors.add("O campo CPF esta Vazio.");
        } else if(cliente.getCpf().length() != 14) {
            errors.add("O campo CPF esta incorreto.");
        }
        
        GregorianCalendar data = new GregorianCalendar();
        if (cliente.getDataNascimento()== null) {
            errors.add("Erro campo data nascimento.");
        } else if((data.get(Calendar.YEAR) - cliente.getDataNascimento().get(Calendar.YEAR)) >= 120){
            errors.add("Idade limite alcançado");
        } else if((data.get(Calendar.YEAR) - cliente.getDataNascimento().get(Calendar.YEAR)) < 16){
            errors.add("A Idade minima é de 16 anos");
        }
        
        /*
        if (cliente.getPets()== null) {
            errors.add("Campo Pets vazio");
        }
        */
        
        return errors;
    }
}
