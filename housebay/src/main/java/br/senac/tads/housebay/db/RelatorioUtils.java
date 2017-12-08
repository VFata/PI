/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.db;

import br.senac.tads.housebay.model.Empresa;
import br.senac.tads.housebay.model.Tipo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Diego
 */
public class RelatorioUtils {
    public static List<Map> geraRelatorio(Tipo tipo, Empresa empresa, GregorianCalendar inicio, GregorianCalendar fim) {
        
        /*
        String sql = "SELECT DISTINCT vendaveis.nome AS \"nome\", "
                + "SUM(venda_vendaveis.quantidade) AS \"quantidade\", "
                + "SUM(venda_vendaveis.valor_total) AS \"total\" "
                + "FROM vendaveis "
                + "JOIN venda_vendaveis ON venda_vendaveis.vendavel_id = vendaveis.id "
                + "JOIN vendas ON venda_vendaveis.venda_id = vendas.id "
                + "WHERE vendaveis.tipo = ? "
                + " AND vendas.empresa_id = ? "
                + " AND (venda_vendaveis.criado <= (?) AND venda_vendaveis.criado >= (?)) "
                + "GROUP BY nome";
        */
        StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT vendaveis.nome AS \"nome\", SUM(venda_vendaveis.quantidade) AS \"quantidade\", SUM(venda_vendaveis.valor_total) AS \"total\" FROM vendaveis JOIN venda_vendaveis ON venda_vendaveis.vendavel_id = vendaveis.id ");
        if (empresa != null) {
            sqlBuilder.append("JOIN vendas ON venda_vendaveis.venda_id = vendas.id ");
        }
        sqlBuilder.append("WHERE (venda_vendaveis.criado <= (?) AND venda_vendaveis.criado >= (?)) ");
        if (empresa != null) {
            sqlBuilder.append(" AND vendas.empresa_id = ? ");
        }
        if (tipo != null) {
            sqlBuilder.append(" AND vendaveis.tipo = ? ");
        }
        sqlBuilder.append("GROUP BY nome");
        String sql = sqlBuilder.toString();
        
        List<Map> list = null;
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            //statement.setInt(1, tipo);
            //statement.setLong(2, empresa.getId());
            statement.setTimestamp(1, new Timestamp(fim.getTimeInMillis()));
            statement.setTimestamp(2, new Timestamp(inicio.getTimeInMillis()));
            if (empresa != null) {
                statement.setLong(3, empresa.getId());
                if (tipo != null) {
                    statement.setInt(4, tipo.getValue());
                }
            } else if (tipo != null) {
                statement.setInt(3, tipo.getValue());
            }
            
            

            try (ResultSet resultados = statement.executeQuery()) {
                list = new ArrayList();
                while (resultados.next()) {
                    Map map = new HashMap();
                    map.put("nome", resultados.getString("nome"));
                    map.put("quantidade", resultados.getLong("quantidade"));
                    map.put("total", resultados.getDouble("total"));
                    map.put("dias", 1 + (fim.getTimeInMillis() - inicio.getTimeInMillis()) / (1000*60*60*24) );
                    
                    list.add(map);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }
    
    public static Tipo[] getTipoList() {
       return Tipo.values();
    }
    
    public static Tipo getTipo(int value) {
       return Tipo.getTipo(value);
    }

    public static List<Empresa> getEmpresaList() {
        return DAOEmpresa.search(null);
    } 
    
    public static Empresa getEmpresa(long id) {
        return DAOEmpresa.read(id);
    }
}
