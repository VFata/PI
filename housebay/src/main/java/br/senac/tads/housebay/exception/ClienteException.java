/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.exception;

import java.util.List;

/**
 *
 * @author Tron
 */
public class ClienteException extends BaseException {
    public ClienteException(String message, List errors) {
        super(message, errors);
    }
}
