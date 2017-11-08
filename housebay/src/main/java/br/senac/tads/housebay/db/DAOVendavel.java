package br.senac.tads.housebay.db;

import br.senac.tads.housebay.model.Produto;
import br.senac.tads.housebay.model.Servico;
import br.senac.tads.housebay.model.Tipo;
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

public class DAOVendavel {
    /*
     * TODO: Change the product to instance of sellable.
     */
    
    public static Long createProduto(Produto produto) {
        String sql = "INSERT INTO produto (nome, descricao, estoque, tipo, valor, codigo_de_barras, ativo, criado, modificado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Long id = null;
        try (Connection connection = SQLUtils.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, produto.getNome());
                statement.setString(2, produto.getDescricao());
                statement.setInt(3, produto.getEstoque());
                statement.setInt(4, produto.getTipo().getValue());
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
                return -1l;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return id;
    }

    public static Long createServico(Servico servico) {
        String sql = "INSERT INTO produto (nome, descricao, estoque, tipo, valor, codigo_de_barras, ativo, criado, modificado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Long id = null;
        try (Connection connection = SQLUtils.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, servico.getNome());
                statement.setString(2, servico.getDescricao());
                statement.setInt(3, 0);
                statement.setInt(4, servico.getTipo().getValue());
                statement.setDouble(5, servico.getValor()) ;
                statement.setString(6, "");
                statement.setBoolean(7, servico.isAtivo());
                Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                statement.setTimestamp(8, now);
                statement.setTimestamp(9, now);
                
                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getLong(1);
                        servico.setId(id);
                    }
                }
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                System.err.println(ex.getMessage());
                return -1l;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return id;
    }
    
    public static Vendavel read(Long id) {
        String sql = "SELECT id, nome, descricao, estoque, tipo, valor, codigo_de_barras, ativo, criado, modificado FROM produto WHERE (id=? AND ativo=?)";
        Vendavel vendavel = null;
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.setBoolean(2, true);

            try (ResultSet resultados = statement.executeQuery()) {
                if (resultados.next()) {
                    int tipo = resultados.getInt("tipo");

                    if (tipo == Tipo.PRODUTO.getValue()) {
                        vendavel = new Produto();
                        vendavel.setId(resultados.getLong("id"));
                        vendavel.setNome(resultados.getString("nome"));
                        vendavel.setDescricao(resultados.getString("descricao"));
                        ((Produto) vendavel).setEstoque(resultados.getInt("estoque"));
                        vendavel.setValor(resultados.getDouble("valor"));
                        ((Produto) vendavel).setCodigoDeBarras(resultados.getString("codigo_de_barras"));
                        vendavel.setAtivo(resultados.getBoolean("ativo"));
                        vendavel.setCriado(resultados.getTimestamp("criado").getTime());
                        vendavel.setModificado(resultados.getTimestamp("modificado").getTime());
                    } else if (tipo == Tipo.SERVICO.getValue()) {
                        vendavel = new Servico();
                        vendavel.setId(resultados.getLong("id"));
                        vendavel.setNome(resultados.getString("nome"));
                        vendavel.setDescricao(resultados.getString("descricao"));
                        vendavel.setValor(resultados.getDouble("valor"));
                        vendavel.setAtivo(resultados.getBoolean("ativo"));
                        vendavel.setCriado(resultados.getTimestamp("criado").getTime());
                        vendavel.setModificado(resultados.getTimestamp("modificado").getTime());
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return vendavel;
    }

    public static List<Vendavel> search(String query) {
        String sql;
        if (query != null) {
            sql = "SELECT id, nome, descricao, estoque, tipo, valor, codigo_de_barras, ativo, criado, modificado FROM produto WHERE (UPPER(nome) LIKE UPPER(?) AND ativo=?)";
        } else {
            sql = "SELECT id, nome, descricao, estoque, tipo, valor, codigo_de_barras, ativo, criado, modificado FROM produto WHERE ativo=?";
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
                    int tipo = resultados.getInt("tipo");

                    if (tipo == Tipo.PRODUTO.getValue()) {
                        Produto produto = new Produto();
                        produto.setId(resultados.getLong("id"));
                        produto.setNome(resultados.getString("nome"));
                        produto.setDescricao(resultados.getString("descricao"));
                        produto.setEstoque(resultados.getInt("estoque"));
                        produto.setValor(resultados.getDouble("valor"));
                        produto.setCodigoDeBarras(resultados.getString("codigo_de_barras"));
                        produto.setAtivo(resultados.getBoolean("ativo"));
                        produto.setCriado(resultados.getTimestamp("criado").getTime());
                        produto.setModificado(resultados.getTimestamp("modificado").getTime());
                        
                        list.add(produto);
                    } else if (tipo == Tipo.SERVICO.getValue()) {
                        Servico servico = new Servico();
                        servico.setId(resultados.getLong("id"));
                        servico.setNome(resultados.getString("nome"));
                        servico.setDescricao(resultados.getString("descricao"));
                        servico.setValor(resultados.getDouble("valor"));
                        servico.setAtivo(resultados.getBoolean("ativo"));
                        servico.setCriado(resultados.getTimestamp("criado").getTime());
                        servico.setModificado(resultados.getTimestamp("modificado").getTime());
                        
                        list.add(servico);
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }

    public static boolean updateProduto(Produto produto) {
        if (produto != null && produto.getId() != null && produto.getId() > 0) {
            String sql = "UPDATE produto SET nome=?, descricao=?, estoque=?, tipo=?, valor=?, codigo_de_barras=?, ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, produto.getNome());
                    statement.setString(2, produto.getDescricao());
                    statement.setInt(3, produto.getEstoque());
                    statement.setInt(4, produto.getTipo().getValue());
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

    public static boolean updateServico(Servico produto) {
        if (produto != null && produto.getId() != null && produto.getId() > 0) {
            String sql = "UPDATE produto SET nome=?, descricao=?, tipo=?, valor=?, ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, produto.getNome());
                    statement.setString(2, produto.getDescricao());
                    statement.setInt(3, produto.getTipo().getValue());
                    statement.setDouble(4, produto.getValor());
                    statement.setBoolean(5, produto.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(6, now);
                    statement.setLong(7, produto.getId());

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
    public static boolean delete(Vendavel vendavel) {
        if (vendavel != null && vendavel.getId() != null && vendavel.getId() > 0) {
            String sql = "UPDATE produto SET ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setBoolean(1, !vendavel.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(2, now);
                    statement.setLong(3, vendavel.getId());
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
