/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.exception;

import java.util.List;

/**
 *
 * @author diego.matsuki
 */
public class VendavelException extends BaseException {
    public VendavelException(String message, List errors) {
        super(message, errors);
    }
}
