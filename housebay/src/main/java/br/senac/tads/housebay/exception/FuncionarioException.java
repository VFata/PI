/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.exception;

import java.util.Map;

/**
 *
 * @author Tron
 */
public class FuncionarioException extends BaseException {
    public FuncionarioException(String message, Map errors) {
        super(message, errors);
    }    
}
