package br.senac.tads.housebay.db;

import br.senac.tads.housebay.db.SQLUtils;
import br.senac.tads.housebay.db.SQLUtils;
import br.senac.tads.housebay.model.Tipo;
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

public class DAOTipo {
    /*
     * TODO: Change the product to instance of sellable.
     */
    
    public static Long create(Tipo tipo) {
        String sql = "INSERT INTO tipo (nome, ativo, criado, modificado) VALUES (?, ?, ?, ?)";
        Long id = null;
        try (Connection connection = SQLUtils.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, tipo.getNome());
                statement.setBoolean(2, tipo.isAtivo());
                Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                statement.setTimestamp(3, now);
                statement.setTimestamp(4, now);
                
                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getLong(1);
                        tipo.setId(id);
                    }
                }
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                System.err.println(ex.getMessage());
                Logger.getLogger(DAOTipo.class.getName()).log(Level.SEVERE, null, ex);
                return -1l;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return id;
    }

    public static Tipo read(Long id) {
        String sql = "SELECT id, nome, ativo, criado, modificado FROM tipo WHERE (id=? AND ativo=?)";
        Tipo tipo = null;
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.setBoolean(2, true);

            try (ResultSet resultados = statement.executeQuery()) {
                if (resultados.next()) {
                    tipo = new Tipo();
                    tipo.setId(resultados.getLong("id"));
                    tipo.setNome(resultados.getString("nome"));
                    tipo.setAtivo(resultados.getBoolean("ativo"));
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return tipo;
    }

    public static List<Tipo> search(String query) {
        String sql;
        if (query != null) {
            sql = "SELECT id, nome, ativo, criado, modificado FROM tipo WHERE (UPPER(nome) LIKE UPPER(?) AND ativo=?)";
        } else {
            sql = "SELECT id, nome, ativo, criado, modificado FROM tipo WHERE ativo=?";
        }
        List<Tipo> list = null;
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
                    Tipo tipo = new Tipo();
                    tipo.setId(resultados.getLong("id"));
                    tipo.setNome(resultados.getString("nome"));
                    tipo.setAtivo(resultados.getBoolean("ativo"));
                    list.add(tipo);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }

    public static boolean update(Tipo tipo) {
        if (tipo != null && tipo.getId() != null && tipo.getId() > 0) {
            String sql = "UPDATE tipo SET nome=?, ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, tipo.getNome());
                statement.setBoolean(2, tipo.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(3, now);
                    statement.setLong(4, tipo.getId());

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

    public static boolean delete(Tipo tipo) {
        if (tipo != null && tipo.getId() != null && tipo.getId() > 0) {
            String sql = "UPDATE tipo SET ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setBoolean(1, !tipo.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(2, now);
                    statement.setLong(3, tipo.getId());
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
