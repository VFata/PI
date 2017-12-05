package br.senac.tads.housebay.db;

import br.senac.tads.housebay.model.Cliente;
import br.senac.tads.housebay.model.Empresa;
import br.senac.tads.housebay.model.Produto;
import br.senac.tads.housebay.model.Servico;
import br.senac.tads.housebay.model.Tipo;
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
                    venda.setCarrinho(referencesVendaveis(venda.getId()));
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
            sql = "SELECT vendas.id, cliente_id, clientes.nome, empresa_id, vendas.ativo, vendas.criado, vendas.modificado "
                    + "FROM vendas INNER JOIN clientes ON clientes.id = vendas.cliente_id "
                    + "WHERE UPPER(clientes.nome) LIKE UPPER(?) AND vendas.ativo=?";
        } else {
            sql = "SELECT id, cliente_id, empresa_id, ativo, criado, modificado FROM vendas WHERE ativo=?";
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
                    venda.setCliente(getCliente(resultados.getLong("cliente_id")));
                    venda.setEmpresa(getEmpresa(resultados.getLong("empresa_id")));
                    venda.setAtivo(resultados.getBoolean("ativo"));
                    venda.setCriado(resultados.getTimestamp("criado").getTime());
                    venda.setModificado(resultados.getTimestamp("modificado").getTime());
                    venda.setCarrinho(referencesVendaveis(venda.getId()));
                    list.add(venda);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }
    
    public static List<Cliente> getClienteList(String query) {
        return DAOCliente.search(query);
    }    
    public static Cliente getCliente(Long id) {
        return DAOCliente.read(id);
    }

    public static List<Empresa> getEmpresaList(String query) {
        return DAOEmpresa.search(query);
    }    
    public static Empresa getEmpresa(Long id) {
        return DAOEmpresa.read(id);
    }
    
    public static Vendavel getVendavel(Long id) {
        return DAOVendavel.read(id);
    }
    
    public static List<Produto> getProdutoList(String query) {
        return DAOVendavel.searchProduto(query);
    }
    public static Produto getProduto(Long id) {
        return DAOVendavel.readProduto(id);
    }
    
    public static List<Servico> getServicoList(String query) {
        return DAOVendavel.searchServico(query);
    }    
    public static Servico getServico(Long id) {
        return DAOVendavel.readServico(id);
    }
    
    public static List<Venda.Relacao> referencesVendaveis(long venda_id) {
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
                
                if (relacao.getVendavel().getTipo() == Tipo.PRODUTO) {
                    nestedAtulizaEstoque(connection, savepoint, relacao);
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                connection.rollback(savepoint);
            } finally {
                if (statement != null) {
                    statement.close();
                }
            }
        }
    }
    
    private static void nestedAtulizaEstoque(Connection connection, Savepoint savepoint, Venda.Relacao relacoes) throws SQLException {
        DAOVendavel.atualizaEstoqueVenda(connection, savepoint, (Produto) relacoes.getVendavel(), relacoes.getQuantidade());        
    }
}
