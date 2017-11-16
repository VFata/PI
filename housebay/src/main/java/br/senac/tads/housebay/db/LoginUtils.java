package br.senac.tads.housebay.db;

import br.senac.tads.housebay.model.Funcionario;

/**
 *
 * @author Diego
 */
public class LoginUtils {
    public static Funcionario autenticar(String email, String senha) {
        return DAOFuncionario.autenticar(email, senha);
    }
}
