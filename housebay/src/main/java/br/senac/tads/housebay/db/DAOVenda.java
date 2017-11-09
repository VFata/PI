package br.senac.tads.housebay.db;

import br.senac.tads.housebay.db.SQLUtils;
import br.senac.tads.housebay.model.Cliente;
import br.senac.tads.housebay.model.Empresa;
import br.senac.tads.housebay.model.Pet;
import br.senac.tads.housebay.model.Venda;
import br.senac.tads.housebay.model.Vendavel;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOVenda {
    
    public static Long create(Venda venda) {
        String sql = "INSERT INTO vendas (cliente_id, empresa_id, ativo, criado, modificado) VALUES (?, ?, ?, ?, ?)";
        Long id = null;
        try (Connection connection = SQLUtils.getConnection()) {
            connection.setAutoCommit(false);
            Savepoint savepoint = connection.setSavepoint();
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, venda.getCliente().getId());
                statement.setLong(2, venda.getEmpresa().getId());
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
                nestedCreateCarrinho(connection, savepoint, venda.getCarrinho(), id);
                
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
        String sql = "SELECT id, cliente_id, empresa_id, ativo, criado, modificado FROM vendas WHERE (id=? AND ativo=?)";
        Venda venda = null;
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.setBoolean(2, true);

            try (ResultSet resultados = statement.executeQuery()) {
                if (resultados.next()) {
                    venda = new Venda();
                    venda.setId(resultados.getLong("id"));
                    venda.setCliente( getCliente(resultados.getLong("cliente_id")));
                    venda.setEmpresa( getEmpresa(resultados.getLong("empresa_id")));
                    venda.setAtivo(resultados.getBoolean("ativo"));
                    venda.setCriado(resultados.getTimestamp("criado").getTime());
                    venda.setModificado(resultados.getTimestamp("modificado").getTime());
                    venda.setCarrinho(referencesCliente(venda.getId()));
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
                    venda.setCliente( getCliente(resultados.getLong("cliente_id")));
                    venda.setEmpresa( getEmpresa(resultados.getLong("empresa_id")));
                    venda.setAtivo(resultados.getBoolean("ativo"));
                    venda.setCriado(resultados.getTimestamp("criado").getTime());
                    venda.setModificado(resultados.getTimestamp("modificado").getTime());
                    list.add(venda);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }

    /*
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
    */
    
    public static Cliente getCliente(Long id) {
        return DAOCliente.read(id);
    }
    
    public static Empresa getEmpresa(Long id) {
        return DAOEmpresa.read(id);
    }
    
    public static Vendavel getVendavel(Long id) {
        return DAOVendavel.read(id);
    }
    
    public static List<Venda.Relacao> referencesCliente(long venda_id) {
        String sql = "SELECT id, venda_id, vendavel_id, quantidade, valor_total, ativo FROM venda_vendaveis WHERE (venda_id=? AND ativo=?)";
        List<Venda.Relacao> list = null;
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setLong(1, venda_id);
            statement.setBoolean(2, true);

            try (ResultSet resultados = statement.executeQuery()) {
                list = new ArrayList<>();
                while (resultados.next()) {
                    Venda.Relacao relacao = new Venda.Relacao();
                    relacao.setQuantidade(resultados.getInt("quantidade"));
                    relacao.setValorTotal(resultados.getDouble("valor_total"));
                    relacao.setVendavel( getVendavel(resultados.getLong("vendavel_id")));
                    
                    list.add(relacao);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }
    
    private static void nestedCreateCarrinho(Connection connection, Savepoint savepoint, List<Venda.Relacao> relacoes, long id) throws SQLException {
        String sql = "INSERT INTO venda_vendaveis (venda_id, vendavel_id, quantidade, valor_total, ativo, criado, modificado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        for(Venda.Relacao relacao : relacoes) {
            try {
                statement = connection.prepareStatement(sql);
                
                statement.setLong(1, id);
                statement.setLong(2, relacao.getVendavel().getId());
                statement.setInt(3, relacao.getQuantidade());
                statement.setDouble(4, relacao.getValorTotal());
                
                statement.setBoolean(5, true);
                long now = Calendar.getInstance().getTime().getTime();
                statement.setTimestamp(6, new Timestamp(now));
                statement.setTimestamp(7, new Timestamp(now));
                
                statement.executeUpdate();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                connection.rollback(savepoint);
            }
        }
        if (statement != null) {
            statement.close();
        }
    }
}
