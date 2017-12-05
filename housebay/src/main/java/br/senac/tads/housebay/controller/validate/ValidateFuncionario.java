/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller.validate;

import br.senac.tads.housebay.db.DAOFuncionario;
import br.senac.tads.housebay.exception.FuncionarioException;
import br.senac.tads.housebay.model.Funcionario;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateFuncionario {private final static String ERRO = "Erro na Validação.";
    //TODO REFAZER TODOS OS VALIDADORES 
    
    public static boolean create(Funcionario funcionario) 
            throws FuncionarioException {
        List<String> errors = new ArrayList();
        
        if (funcionario == null) {
            errors.add("Funcionario nulo.");
        } else {
            Pattern email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher m = email.matcher(funcionario.getEmail());    
            if (funcionario.getEmail()== null || funcionario.getEmail().equals("")) {
                errors.add("Campo Email Vazio");
            } else if(!m.find()) {
                errors.add("Email incorreto");
            } else if(DAOFuncionario.validaEmail(funcionario.getEmail()) != 0 ){
                errors.add("Email existente no sistema.");
            }
         
            if(DAOFuncionario.validaCPF(funcionario.getCpf()) != 0){
                errors.add("CPF existente no sistema.");
            }
            
            if (funcionario.getSenha()== null || 
                    funcionario.getSenha().equals("") || 
                    funcionario.getSenha().length() <= 8) {
                errors.add("Erro campo senha.");
            }
            
            errors.addAll(geraMensagem(funcionario));
        }
        
        if (!errors.isEmpty()) {
            throw new FuncionarioException(ERRO, errors);
        }
        
        return true;
    }
    
    public static boolean update(Funcionario funcionario) 
            throws FuncionarioException {
        List<String> errors = new ArrayList();
        
        if (funcionario == null) {
            errors.add("Funcionario nulo.");
        } else {
            if (funcionario.getId() == null || funcionario.getId() <= 0 ) {
                errors.add("ID vazio.");
            }
         
            if(DAOFuncionario.validaCPF(funcionario.getCpf()) != 0 &&
                    DAOFuncionario.validaCPF(funcionario.getCpf()) != funcionario.getId()){
                errors.add("CPF existente no sistema.");
            }
            
            errors.addAll(geraMensagem(funcionario));
        }
        
        if (!errors.isEmpty()) {
            throw new FuncionarioException(ERRO, errors);
        }
        
        return true;
    }
    
    private static List<String> geraMensagem(Funcionario funcionario) {
        List<String> errors = new ArrayList(); 
        
        if (funcionario.getNome() == null || funcionario.getNome().equals("")) {
            errors.add("O campo nome esta vazio.");
        }
        
        if (funcionario.getCpf()== null || funcionario.getCpf().equals("")){
            errors.add("O campo CPF esta Vario.");
        } else if(funcionario.getCpf().length() != 14){
            errors.add("O campo CPF esta incorreto."); 
        }
        
        GregorianCalendar date = new GregorianCalendar();
        if (funcionario.getDataNascimento()== null) {
            errors.add("O campo nascimento esta vazio.");
        } else if((date.get(Calendar.YEAR) - funcionario.getDataNascimento().get(Calendar.YEAR)) >= 120){
            errors.add("A idade limite foi utrapassada.");
        } else if((date.get(Calendar.YEAR) - funcionario.getDataNascimento().get(Calendar.YEAR)) < 16){
            errors.add("A idade minima é de 16 anos.");
        }
        
        if (funcionario.getTelefone()== null || funcionario.getTelefone().equals("")) {
            errors.add("O campo telefone esta vazio");
        } else if(funcionario.getTelefone().length() >= 14){
            errors.add("O campo telefone deve conter ate 14 caracteres.");  
        } else if(funcionario.getTelefone().length() <= 8) {
            errors.add("O campo telefone deve conter no minimo 8 caracteres.");
        }

        return errors;
    }
    
}
