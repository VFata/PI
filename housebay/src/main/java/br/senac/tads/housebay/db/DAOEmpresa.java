package br.senac.tads.housebay.db;

import br.senac.tads.housebay.model.Empresa;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOEmpresa {
    /*
     * TODO: Change the product to instance of sellable.
     */
    
    public static Long create(Empresa empresa) {
        String sql = "INSERT INTO empresas (nome, cnpj, ativo, criado, modificado) VALUES (?, ?, ?, ?, ?)";
        Long id = null;
        try (Connection connection = SQLUtils.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, empresa.getNome());
                statement.setString(2, empresa.getCnpj());
                statement.setBoolean(3, empresa.isAtivo());
                Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                statement.setTimestamp(4, now);
                statement.setTimestamp(5, now);
                
                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getLong(1);
                        empresa.setId(id);
                    }
                }
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                System.err.println(ex.getMessage());
                Logger.getLogger(DAOEmpresa.class.getName()).log(Level.SEVERE, null, ex);
                return -1l;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return id;
    }

    public static Empresa read(Long id) {
        String sql = "SELECT id, nome, cnpj, ativo, criado, modificado FROM empresas WHERE (id=? AND ativo=?)";
        Empresa empresa = null;
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.setBoolean(2, true);

            try (ResultSet resultados = statement.executeQuery()) {
                if (resultados.next()) {
                    empresa = new Empresa();
                    empresa.setId(resultados.getLong("id"));
                    empresa.setNome(resultados.getString("nome"));
                    empresa.setCnpj(resultados.getString("cnpj"));
                    empresa.setAtivo(resultados.getBoolean("ativo"));
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return empresa;
    }
    
    public static boolean validaNome (String nome) {
        String sql = "SELECT nome FROM empresas WHERE (nome=?)";
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,nome);

            try (ResultSet resultados = statement.executeQuery()) {
                if (resultados.next()) {
                    return true;
                }
                else{
                    return false;
                    }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }
    
        public static boolean validaCNPJ (String cnpj) {
        String sql = "SELECT cnpj FROM empresas WHERE (cnpj=?)";
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,cnpj);

            try (ResultSet resultados = statement.executeQuery()) {
                if (resultados.next()) {
                    return true;
                }
                else{
                    return false;
                    }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }
    

    public static List<Empresa> search(String query) {
        String sql;
        if (query != null) {
            sql = "SELECT id, nome, cnpj, ativo, criado, modificado FROM empresas WHERE (UPPER(nome) LIKE UPPER(?) AND ativo=?)";
        } else {
            sql = "SELECT id, nome, cnpj, ativo, criado, modificado FROM empresas WHERE ativo=?";
        }
        List<Empresa> list = null;
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            if (query != null) {
                statement.setString(1, "%"+query.trim()+"%");
                statement.setBoolean(2, true);
            } else {
                statement.setBoolean(1, true);
            }

            try (ResultSet resultados = statement.executeQuery()) {
                list = new ArrayList<>();
                while (resultados.next()) {
                    Empresa empresa = new Empresa();
                    empresa.setId(resultados.getLong("id"));
                    empresa.setNome(resultados.getString("nome"));
                    empresa.setCnpj(resultados.getString("cnpj"));
                    empresa.setAtivo(resultados.getBoolean("ativo"));
                    list.add(empresa);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }

    public static boolean update(Empresa empresa) {
        if (empresa != null && empresa.getId() != null && empresa.getId() > 0) {
            String sql = "UPDATE empresas SET nome=?, cnpj=?, ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, empresa.getNome());
                statement.setString(2, empresa.getCnpj());
                statement.setBoolean(3, empresa.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(4, now);
                    statement.setLong(5, empresa.getId());

                    statement.execute();
                    connection.commit();
                } catch (SQLException ex) {
                    connection.rollback();
                    System.err.println(ex.getMessage());
                    return false;
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean delete(Empresa empresa) {
        if (empresa != null && empresa.getId() != null && empresa.getId() > 0) {
            String sql = "UPDATE empresas SET ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setBoolean(1, !empresa.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(2, now);
                    statement.setLong(3, empresa.getId());
                    statement.execute();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                return false;
            }
            return true;
        } else {
            return false;
        }
    }
}
