package br.senac.tads.housebay.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego
 */
public class DAOPet {
    public static Long create(Pet pet) {
        String sql = "INSERT INTO pets (nome, descricao, ativo) VALUES (?, ?, ?)";
        Long id = null;
        try (Connection connection = SQLUtils.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, pet.getNome());
                statement.setString(2, pet.getDescricao());
                statement.setBoolean(3, pet.isAtivo());

                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getLong(1);
                        pet.setId(id);
                    }
                }
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                System.err.println(ex.getMessage());
                Logger.getLogger(DAOPet.class.getName()).log(Level.SEVERE, null, ex);
                return -1l;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return id;
    }

    public static Pet read(Long id) {
        String sql = "SELECT id, nome, descricao FROM pets WHERE (id=? AND ativo=?)";
        Pet pet = null;
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.setBoolean(2, true);

            try (ResultSet resultados = statement.executeQuery()) {
                if (resultados.next()) {
                    pet = new Pet();
                    pet.setId(resultados.getLong("id"));
                    pet.setNome(resultados.getString("nome"));
                    pet.setDescricao(resultados.getString("descricao"));
                    pet.setAtivo(resultados.getBoolean("ativo"));
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return pet;
    }

    public static List<Pet> search(String query) {
        String sql;
        if (query != null) {
            sql = "SELECT id, nome, descricao, ativo FROM pets WHERE (UPPER(nome) LIKE UPPER(?) AND ativo=?)";
        } else {
            sql = "SELECT id, nome, descricao, ativo FROM pets WHERE ativo=?";
        }
        List<Pet> list = null;
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
                    Pet pet = new Pet();
                    pet.setId(resultados.getLong("id"));
                    pet.setNome(resultados.getString("nome"));
                    pet.setDescricao(resultados.getString("descricao"));
                    pet.setAtivo(resultados.getBoolean("ativo"));
                    list.add(pet);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }

    public static boolean update(Pet pet) {
        if (pet != null && pet.getId() != null && pet.getId() > 0) {
            String sql = "UPDATE pets SET nome=?, descricao=?, ativo=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, pet.getNome());
                    statement.setString(2, pet.getDescricao());
                    statement.setBoolean(3, pet.isAtivo());
                    statement.setLong(4, pet.getId());

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

    public static boolean delete(Pet pet) {
        if (pet != null && pet.getId() != null && pet.getId() > 0) {
            String sql = "UPDATE pets SET ativo=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setBoolean(1, !pet.isAtivo());
                    statement.setLong(2, pet.getId());
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
