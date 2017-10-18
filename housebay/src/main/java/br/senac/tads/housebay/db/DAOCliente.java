package br.senac.tads.housebay.db;

import br.senac.tads.housebay.model.Cliente;
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

/**
 *
 * @author Diego
 */
public class DAOCliente {
    /*
     * TODO: ARRUMAR impede compilação
     */
    
    
    public static Long create(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, descricao, ativo, criado, modificado) VALUES (?, ?, ?, ?, ?)";
        Long id = null;
        try (Connection connection = SQLUtils.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, cliente.getNome());
                //statement.setString(2, cliente.getDescricao());
                statement.setBoolean(3, cliente.isAtivo());
                Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                statement.setTimestamp(4, now);
                statement.setTimestamp(5, now);
                
                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getLong(1);
                        cliente.setId(id);
                    }
                }
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                System.err.println(ex.getMessage());
                Logger.getLogger(DAOCliente.class.getName()).log(Level.SEVERE, null, ex);
                return -1l;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return id;
    }

    public static Cliente read(Long id) {
        String sql = "SELECT id, nome, descricao, ativo FROM clientes WHERE (id=? AND ativo=?)";
        Cliente cliente = null;
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.setBoolean(2, true);

            try (ResultSet resultados = statement.executeQuery()) {
                if (resultados.next()) {
                    cliente = new Cliente();
                    cliente.setId(resultados.getLong("id"));
                    cliente.setNome(resultados.getString("nome"));
                    //cliente.setDescricao(resultados.getString("descricao"));
                    cliente.setAtivo(resultados.getBoolean("ativo"));
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return cliente;
    }

    public static List<Cliente> search(String query) {
        String sql;
        if (query != null) {
            sql = "SELECT id, nome, descricao, ativo FROM clientes WHERE (UPPER(nome) LIKE UPPER(?) AND ativo=?)";
        } else {
            sql = "SELECT id, nome, descricao, ativo FROM clientes WHERE ativo=?";
        }
        List<Cliente> list = null;
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
                    Cliente cliente = new Cliente();
                    cliente.setId(resultados.getLong("id"));
                    cliente.setNome(resultados.getString("nome"));
                    //cliente.setDescricao(resultados.getString("descricao"));
                    cliente.setAtivo(resultados.getBoolean("ativo"));
                    list.add(cliente);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }

    public static boolean update(Cliente cliente) {
        if (cliente != null && cliente.getId() != null && cliente.getId() > 0) {
            String sql = "UPDATE clientes SET nome=?, descricao=?, ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, cliente.getNome());
                    //statement.setString(2, cliente.getDescricao());
                    statement.setBoolean(3, cliente.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(4, now);
                    statement.setLong(5, cliente.getId());

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

    public static boolean delete(Cliente cliente) {
        if (cliente != null && cliente.getId() != null && cliente.getId() > 0) {
            String sql = "UPDATE clientes SET ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setBoolean(1, !cliente.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(2, now);
                    statement.setLong(3, cliente.getId());
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

