package br.senac.tads.housebay.db;

import br.senac.tads.housebay.model.Produto;
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

public class DAOVendavel {
    /*
     * TODO: Change the product to instance of sellable.
     */
    
    public static Long create(Produto produto) {
        String sql = "INSERT INTO produto (nome, descricao, estoque, tipo_id, valor, codigo_de_barras, ativo, criado, modificado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Long id = null;
        try (Connection connection = SQLUtils.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, produto.getNome());
                statement.setString(2, produto.getDescricao());
                statement.setInt(3, produto.getEstoque());
                statement.setLong(4, produto.getTipo().getId());
                statement.setDouble(5, produto.getValor()) ;
                statement.setString(6, produto.getCodigoDeBarras());
                statement.setBoolean(7, produto.isAtivo());
                Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                statement.setTimestamp(8, now);
                statement.setTimestamp(9, now);
                
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
                Logger.getLogger(DAOVendavel.class.getName()).log(Level.SEVERE, null, ex);
                return -1l;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return id;
    }

    public static Produto read(Long id) {
        String sql = "SELECT id, nome, descricao, estoque, tipo_id, valor, codigo_de_barras, ativo, criado, modificado FROM produto WHERE (id=? AND ativo=?)";
        Produto produto = null;
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.setBoolean(2, true);

            try (ResultSet resultados = statement.executeQuery()) {
                if (resultados.next()) {
                    produto = new Produto();
                    produto.setId(resultados.getLong("id"));
                    produto.setNome(resultados.getString("nome"));
                    produto.setDescricao(resultados.getString("descricao"));
                    produto.setEstoque(resultados.getInt("estoque"));
                    produto.setTipo(getTipo(resultados.getLong("tipo_id")));
                    produto.setValor(resultados.getDouble("valor"));
                    produto.setCodigoDeBarras(resultados.getString("codigoDeBarras"));
                    produto.setAtivo(resultados.getBoolean("ativo"));
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return produto;
    }

    public static List<Produto> search(String query) {
        String sql;
        if (query != null) {
            sql = "SELECT id, nome, descricao, estoque, tipo_id, valor, codigo_de_barras, ativo, criado, modificado FROM produto WHERE (UPPER(nome) LIKE UPPER(?) AND ativo=?)";
        } else {
            sql = "SELECT id, nome, descricao, estoque, tipo_id, valor, codigo_de_barras, ativo, criado, modificado FROM produto WHERE ativo=?";
        }
        List<Produto> list = null;
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
                    Produto produto = new Produto();
                    produto.setId(resultados.getLong("id"));
                    produto.setNome(resultados.getString("nome"));
                    produto.setDescricao(resultados.getString("descricao"));
                    produto.setEstoque(resultados.getInt("estoque"));
                    produto.setTipo(getTipo(resultados.getLong("tipo_id")));
                    produto.setValor(resultados.getDouble("valor"));
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

    public static boolean update(Produto produto) {
        if (produto != null && produto.getId() != null && produto.getId() > 0) {
            String sql = "UPDATE produto SET nome=?, descricao=?, estoque=?, tipo_id=?, valor=?, codigo_de_barras=?, ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, produto.getNome());
                    statement.setString(2, produto.getDescricao());
                    statement.setInt(3, produto.getEstoque());
                    statement.setLong(4, produto.getTipo().getId());
                    statement.setDouble(5, produto.getValor());
                    statement.setString(6, produto.getCodigoDeBarras());
                    statement.setBoolean(7, produto.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(8, now);
                    statement.setLong(9, produto.getId());

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

    public static boolean delete(Produto produto) {
        if (produto != null && produto.getId() != null && produto.getId() > 0) {
            String sql = "UPDATE produto SET ativo=?, modificado=? WHERE id=?";
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
    
    
    public static Tipo getTipo(long id) {
        return DAOTipo.read(id);
    }
    
    public static List<Tipo> getTipoList() {
        return DAOTipo.search(null);
    }
}
