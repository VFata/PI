package br.senac.tads.housebay.db;

import br.senac.tads.housebay.db.SQLUtils;
import br.senac.tads.housebay.model.Venda;
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

public class DAOVenda {
    /*
     * TODO: Change the product to instance of sellable.
     */
    
    public static Long create(Venda venda) {
        String sql = "INSERT INTO venda (id, cliente_id, empresa_id, ativo, criado, modificado) VALUES (?, ?, ?, ?, ?)";
        Long id = null;
        try (Connection connection = SQLUtils.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, venda.getClienteId());
                statement.setLong(2, venda.getEmpresaId());
                statement.setBoolean(3, venda.isAtivo());
                Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                statement.setTimestamp(4, now);
                statement.setTimestamp(5, now);
                
                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getLong(1);
                        venda.setId(id);
                    }
                }
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                System.err.println(ex.getMessage());
                Logger.getLogger(DAOVenda.class.getName()).log(Level.SEVERE, null, ex);
                return -1l;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return id;
    }

    public static Venda read(Long id) {
        String sql = "SELECT id, cliente_id, empresa_id, ativo, criado, modificado FROM venda WHERE (id=? AND ativo=?)";
        Venda venda = null;
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.setBoolean(2, true);

            try (ResultSet resultados = statement.executeQuery()) {
                if (resultados.next()) {
                    venda = new Venda();
                    venda.setId(resultados.getLong("id"));
                    venda.setClienteId(resultados.getLong("cliente_id"));
                    venda.setEmpresaId(resultados.getLong("empresa_id"));
                    venda.setAtivo(resultados.getBoolean("ativo"));
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return venda;
    }

    public static List<Venda> search(String query) {
        String sql;
        if (query != null) {
            sql = "SELECT id, cliente_id, empresa_id, ativo, criado, modificado FROM venda WHERE (UPPER(nome) LIKE UPPER(?) AND ativo=?)";
        } else {
            sql = "SELECT id, cliente_id, empresa_id, ativo, criado, modificado FROM venda WHERE ativo=?";
        }
        List<Venda> list = null;
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
                    Venda venda = new Venda();
                    venda.setId(resultados.getLong("id"));
                    venda.setClienteId(resultados.getLong("cliente_id"));
                    venda.setEmpresaId(resultados.getLong("empresa_id"));
                    venda.setAtivo(resultados.getBoolean("ativo"));
                    list.add(venda);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }

    public static boolean update(Venda venda) {
        if (venda != null && venda.getId() != null && venda.getId() > 0) {
            String sql = "UPDATE venda SET id=?, cliente_id=?, empresa_id=?, ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, venda.getClienteId());
                statement.setLong(2, venda.getEmpresaId());
                statement.setBoolean(3, venda.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(4, now);
                    statement.setLong(5, venda.getId());

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

    public static boolean delete(Venda venda) {
        if (venda != null && venda.getId() != null && venda.getId() > 0) {
            String sql = "UPDATE venda SET ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setBoolean(1, !venda.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(2, now);
                    statement.setLong(3, venda.getId());
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
