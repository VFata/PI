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

public class DAOVendavel {
    /*
     * TODO: ARRUMAR impede compilação
     */
    
    public static Long create(Vendavel vendavel) {
        String sql = "INSERT INTO vendavel (nome, descricao, estoque, tipo_id, valor, codigo_de_barras, ativo, criado, modificado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Long id = null;
        try (Connection connection = SQLUtils.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, vendavel.getNome());
                statement.setString(2, vendavel.getDescricao());
                statement.setInt(3, vendavel.getEstoque());
                statement.setLong(4, vendavel.getTipoId());
                statement.setString(5, vendavel.getValor());
                statement.setString(6, vendavel.getCodigoDeBarras());
                statement.setBoolean(7, vendavel.isAtivo());
                Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                statement.setTimestamp(8, now);
                statement.setTimestamp(9, now);
                
                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getLong(1);
                        vendavel.setId(id);
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

    public static Vendavel read(Long id) {
        String sql = "SELECT id, nome, descricao, estoque, tipo_id, valor, codigo_de_barras, ativo, criado, modificado FROM vendavel WHERE (id=? AND ativo=?)";
        Vendavel vendavel = null;
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.setBoolean(2, true);

            try (ResultSet resultados = statement.executeQuery()) {
                if (resultados.next()) {
                    vendavel = new Vendavel();
                    vendavel.setId(resultados.getLong("id"));
                    vendavel.setNome(resultados.getString("nome"));
                    vendavel.setDescricao(resultados.getString("descricao"));
                    vendavel.setEstoque(resultados.getInt("estoque"));
                    vendavel.setTipoId(resultados.getInt("tipo_id"));
                    vendavel.setValor(resultados.getString("valor"));
                    vendavel.setCodigoDeBarras(resultados.getString("codigoDeBarras"));
                    vendavel.setAtivo(resultados.getBoolean("ativo"));
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
            sql = "SELECT id, nome, descricao, estoque, tipo_id, valor, codigo_de_barras, ativo, criado, modificado FROM vendavel WHERE (UPPER(nome) LIKE UPPER(?) AND ativo=?)";
        } else {
            sql = "SELECT id, nome, descricao, estoque, tipo_id, valor, codigo_de_barras, ativo, criado, modificado FROM vendavel WHERE ativo=?";
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
                    Vendavel vendavel = new Vendavel();
                    vendavel.setId(resultados.getLong("id"));
                    vendavel.setNome(resultados.getString("nome"));
                    vendavel.setDescricao(resultados.getString("descricao"));
                    vendavel.setEstoque(resultados.getInt("estoque"));
                    vendavel.setTipoId(resultados.getInt("tipo_id"));
                    vendavel.setValor(resultados.getString("valor"));
                    vendavel.setCodigoDeBarras(resultados.getString("codigoDeBarras"));
                    vendavel.setAtivo(resultados.getBoolean("ativo"));
                    list.add(vendavel);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }

    public static boolean update(Vendavel vendavel) {
        if (vendavel != null && vendavel.getId() != null && vendavel.getId() > 0) {
            String sql = "UPDATE vendavel SET nome=?, descricao=?, estoque=?, tipo_id=?, valor=?, codigo_de_barras=?, ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, vendavel.getNome());
                statement.setString(2, vendavel.getDescricao());
                statement.setInt(3, vendavel.getEstoque());
                statement.setInt(4, vendavel.getTipoId());
                statement.setString(5, vendavel.getValor());
                statement.setString(6, vendavel.getCodigoDeBarras());
                statement.setBoolean(7, vendavel.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(8, now);
                    statement.setLong(9, vendavel.getId());

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
            String sql = "UPDATE vendavel SET ativo=?, modificado=? WHERE id=?";
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
