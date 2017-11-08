/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.exception;

import java.util.Map;

/**
 *
<<<<<<< HEAD
 * @author diego.matsuki
 */
public class CargoException extends Exception {

    private final Map errors;
    
    public CargoException(String message, Map errors) {
        super(message);
        this.errors = errors;
    }

    public Map getErrors() {
        return errors;
    }
    
=======
 * @author Diego
 */
public class CargoException extends BaseException {
    public CargoException(String message, Map errors) {
        super(message, errors);
    }
>>>>>>> origin/master
}
