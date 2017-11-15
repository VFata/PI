/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.db;

import br.senac.tads.housebay.model.Funcionario;

/**
 *
 * @author Diego
 */
public class Login {
    public static Funcionario autenticar(String email, String senha) {
        return DAOFuncionario.autenticar(email, senha);
    }
}
