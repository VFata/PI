package br.senac.tads.housebay.db;

import br.senac.tads.housebay.model.Cargo;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOCargo {
    /*
     * TODO: Change the product to instance of sellable.
     */
    /*
    public static Long create(Cargo cargo) {
        String sql = "INSERT INTO cargo (id, nome, ativo, criado, modificado) VALUES (?, ?, ?, ?, ?)";
        Long id = null;
        try (Connection connection = SQLUtils.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, cargo.getNome());
                statement.setBoolean(2, cargo.isAtivo());
                Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                statement.setTimestamp(3, now);
                statement.setTimestamp(4, now);
                
                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getLong(1);
                        cargo.setId(id);
                    }
                }
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                System.err.println(ex.getMessage());
                Logger.getLogger(DAOCargo.class.getName()).log(Level.SEVERE, null, ex);
                return -1l;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return id;
    }
    */

    public static Cargo read(Long id) {
        String sql = "SELECT id, nome, ativo, criado, modificado FROM cargos WHERE (id=? AND ativo=?)";
        Cargo cargo = null;
        try (Connection connection = SQLUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.setBoolean(2, true);

            try (ResultSet resultados = statement.executeQuery()) {
                if (resultados.next()) {
                    cargo = new Cargo();
                    cargo.setId(resultados.getLong("id"));
                    cargo.setNome(resultados.getString("nome"));
                    cargo.setAtivo(resultados.getBoolean("ativo"));
                    cargo.setCriado(resultados.getTimestamp("criado").getTime());
                    cargo.setModificado(resultados.getTimestamp("modificado").getTime());
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return cargo;
    }

    public static List<Cargo> search(String query) {
        //TODO: remove query parameter ?
        String sql;
        if (query != null) {
            sql = "SELECT id, nome, ativo, criado, modificado FROM cargos WHERE (UPPER(nome) LIKE UPPER(?) AND ativo=?)";
        } else {
            sql = "SELECT id, nome, ativo, criado, modificado FROM cargos WHERE ativo=?";
        }
        List<Cargo> list = null;
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
                    Cargo cargo = new Cargo();
                    cargo.setId(resultados.getLong("id"));
                    cargo.setNome(resultados.getString("nome"));
                    cargo.setAtivo(resultados.getBoolean("ativo"));
                    cargo.setCriado(resultados.getTimestamp("criado").getTime());
                    cargo.setModificado(resultados.getTimestamp("modificado").getTime());
                    list.add(cargo);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }
    
    /*
    public static boolean update(Cargo cargo) {
        if (cargo != null && cargo.getId() != null && cargo.getId() > 0) {
            String sql = "UPDATE cargo SET nome=?, ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, cargo.getNome());
                statement.setBoolean(2, cargo.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(3, now);
                    statement.setLong(4, cargo.getId());

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

    public static boolean delete(Cargo cargo) {
        if (cargo != null && cargo.getId() != null && cargo.getId() > 0) {
            String sql = "UPDATE cargo SET ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setBoolean(1, !cargo.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(2, now);
                    statement.setLong(3, cargo.getId());
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
}
