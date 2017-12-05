/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.exception;

import java.util.List;

/**
 *
 * @author Diego
 */
public abstract class BaseException extends Exception { 
    private final List errors;
    
    public BaseException(String message, List errors) {
        super(message);
        this.errors = errors;
    }

    public List getErrors() {
        return errors;
    }
}
