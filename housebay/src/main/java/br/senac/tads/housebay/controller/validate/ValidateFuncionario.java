/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller.validate;

import br.senac.tads.housebay.exception.FuncionarioException;
import br.senac.tads.housebay.model.Funcionario;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateFuncionario {private final static String ERRO = "Erro na Validação.";
    //TODO REFAZER TODOS OS VALIDADORES 
    
    public static boolean create(Funcionario funcionario) 
            throws FuncionarioException {
        Map errors = new HashMap();
        
        if (funcionario == null) {
            //erro += "\nFuncionario nulo.";
            errors.put(Funcionario.class.getCanonicalName() + "_empty", "Funcionario nulo.");
        } else {
            
            
            Pattern email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher m = email.matcher(funcionario.getEmail());    
            if (funcionario.getEmail()== null || funcionario.getEmail().equals("")) {
                //erro += "\nNome vazio.";
                errors.put(Funcionario.EMAIL + "_empty", "Campo Email Vazio");
            }else if(!m.find()){                
                errors.put(Funcionario.EMAIL + "_empty", "Email incorreto");
            }
            
            
            
            
            if (funcionario.getSenha()== null || funcionario.getSenha().equals("") || funcionario.getSenha().length() <= 8) {
                //erro += "\nNome vazio.";
                errors.put(Funcionario.SENHA + "_empty", "Erro campo senha.");
            }
            
            //erro = geraMensagem(funcionario, erro);
            errors.putAll(geraMensagem(funcionario));
        }
        if (!errors.isEmpty()) {
            throw new FuncionarioException(ERRO, errors);
        }
        /*
        if (!erro.equals(ERRO)) {
            throw new FuncionarioException(erro);
        }
        */
        
        return true;
    }
    
    public static boolean update(Funcionario funcionario) 
            throws FuncionarioException {
        Map errors = new HashMap();
        
        if (funcionario == null) {
            //erro += "\nFuncionario nulo.";
            errors.put(Funcionario.class.getCanonicalName() + "_empty", "Funcionario nulo.");
        } else {
            if (funcionario.getId() == null || funcionario.getId() <= 0 ) {
                //erro += "\nId vazio.";
                errors.put(Funcionario.ID + "_empty", "ID vazio.");
            }
            
            //erro = geraMensagem(funcionario, erro);
            errors.putAll(geraMensagem(funcionario));
        }
        
        if (!errors.isEmpty()) {
            throw new FuncionarioException(ERRO, errors);
        }
        /*
        if (!erro.equals(ERRO)) {
            throw new FuncionarioException(erro);
        }
        */
        
        return true;
    }
    
    private static Map geraMensagem(Funcionario funcionario) {
        Map errors = new HashMap(); 
        
        if (funcionario.getNome() == null || funcionario.getNome().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Funcionario.NOME + "_empty", "O campo nome esta vazio.");
        }
        
        if (funcionario.getCpf()== null || funcionario.getCpf().equals("")){
            //erro += "\nNome vazio.";
            errors.put(Funcionario.CPF + "_empty", "O campo CPF esta Vario.");
        }else if(funcionario.getCpf().length() != 14){
            errors.put(Funcionario.CPF + "_empty", "O campo CPF esta incorreto."); 
        }
        
        GregorianCalendar date = new GregorianCalendar();
        if (funcionario.getDataNascimento()== null || (date.get(Calendar.YEAR) - funcionario.getDataNascimento().get(Calendar.YEAR)) >= 120 || (date.get(Calendar.YEAR) - funcionario.getDataNascimento().get(Calendar.YEAR)) < 16) {
            //erro += "\nNome vazio.";
            errors.put(Funcionario.DATA_NASCIMENTO + "_empty", "O campo nascimento esta vazio.");
            
        }else if((date.get(Calendar.YEAR) - funcionario.getDataNascimento().get(Calendar.YEAR)) >= 120){
            
            errors.put(Funcionario.DATA_NASCIMENTO + "_empty", "A idade limite foi utrapassada.");
            
        }else if((date.get(Calendar.YEAR) - funcionario.getDataNascimento().get(Calendar.YEAR)) < 16){
            
            errors.put(Funcionario.DATA_NASCIMENTO + "_empty", "A idade minima é de 16 anos.");
        }
        
        if (funcionario.getTelefone()== null || funcionario.getTelefone().equals("")) {
            //erro += "\nNome vazio.";
            errors.put(Funcionario.TELEFONE + "_empty", "O campo telefone esta vazio");
        }else if(funcionario.getTelefone().length() >= 14){
            
            errors.put(Funcionario.TELEFONE + "_empty", "O campo telefone deve conter ate 14 caracteres.");
            
        }else if(funcionario.getTelefone().length() <= 8){
            
            errors.put(Funcionario.TELEFONE + "_empty", "O campo telefone deve conter no minimo 8 caracteres.");
        }

        
        return errors;
    }
    
}
