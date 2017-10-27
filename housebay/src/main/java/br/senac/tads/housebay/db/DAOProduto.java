package br.senac.tads.housebay.db;

import br.senac.tads.housebay.model.Vendavel;
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

public class DAOProduto {
    /*
     * TODO: ARRUMAR impede compilação
     */
    
    public static Long create(Vendavel produto) {
        String sql = "INSERT INTO produto (produto, tipo, valor, codigodeBarras, ativo, criado, modificado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Long id = null;
        try (Connection connection = SQLUtils.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, produto.getProduto());
                statement.setString(2, produto.getTipo());
                statement.setString(3, produto.getValor());
                statement.setString(4, produto.getCodigoDeBarras());
                statement.setBoolean(5, produto.isAtivo());
                Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                statement.setTimestamp(6, now);
                statement.setTimestamp(7, now);
                
                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getLong(1);
                        produto.setId(id);
                    }
                }
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                System.err.println(ex.getMessage());
                Logger.getLogger(DAOProduto.class.getName()).log(Level.SEVERE, null, ex);
                return -1l;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return id;
    }

    public static Vendavel read(Long id) {
        String sql = "SELECT id, produto, tipo, valor, ativo FROM produtos WHERE (id=? AND ativo=?)";
        Vendavel produto = null;
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.setBoolean(2, true);

            try (ResultSet resultados = statement.executeQuery()) {
                if (resultados.next()) {
                    produto = new Vendavel();
                    produto.setId(resultados.getLong("id"));
                    produto.setProduto(resultados.getString("produto"));
                    produto.setValor(resultados.getString("valor"));
                    produto.setCodigoDeBarras(resultados.getString("codigoDeBarras"));
                    produto.setAtivo(resultados.getBoolean("ativo"));
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return produto;
    }

    public static List<Vendavel> search(String query) {
        String sql;
        if (query != null) {
            sql = "SELECT id, produto, tipo, valor, codigoDeBarras, ativo FROM produtos WHERE (UPPER(nome) LIKE UPPER(?) AND ativo=?)";
        } else {
            sql = "SELECT id, produto, tipo, valor, codigoDeBarras, ativo FROM produtos WHERE ativo=?";
        }
        List<Vendavel> list = null;
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
                    Vendavel produto = new Vendavel();
                    produto.setId(resultados.getLong("id"));
                    produto.setProduto(resultados.getString("produto"));
                    produto.setValor(resultados.getString("valor"));
                    produto.setCodigoDeBarras(resultados.getString("codigoDeBarras"));
                    produto.setAtivo(resultados.getBoolean("ativo"));
                    list.add(produto);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }

    public static boolean update(Vendavel produto) {
        if (produto != null && produto.getId() != null && produto.getId() > 0) {
            String sql = "UPDATE produtos SET nome=?, descricao=?, ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, produto.getProduto());
                    statement.setString(2, produto.getTipo());
                    statement.setString(3, produto.getValor());
                    statement.setString(4, produto.getCodigoDeBarras());
                    statement.setBoolean(5, produto.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(4, now);
                    statement.setLong(5, produto.getId());

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

    public static boolean delete(Vendavel produto) {
        if (produto != null && produto.getId() != null && produto.getId() > 0) {
            String sql = "UPDATE produtos SET ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setBoolean(1, !produto.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(2, now);
                    statement.setLong(3, produto.getId());
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
