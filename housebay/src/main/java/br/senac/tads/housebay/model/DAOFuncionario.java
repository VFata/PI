package br.senac.tads.housebay.model;

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

/**
 *
 * @author Diego
 */
public class DAOFuncionario {
    public static Long create(Funcionario funcionario) {
        String sql = "INSERT INTO funcionarios (nome, datadeNascimento, telefone, cpf, cargo, email, ativo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Long id = null;
        try (Connection connection = SQLUtils.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, funcionario.getNome());
                statement.setDate(2, funcionario.getdatadeNascimento());
                statement.setString(3, funcionario.getTelefone());
                statement.setString(4, funcionario.getCpf());
                statement.setString(5, funcionario.getCargo());
                statement.setString(6, funcionario.getEmail());
                statement.setBoolean(7, funcionario.isAtivo());
                Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                statement.setTimestamp(8, now);
                statement.setTimestamp(9, now);
                
                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getLong(1);
                        funcionario.setId(id);
                    }
                }
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                System.err.println(ex.getMessage());
                Logger.getLogger(DAOFuncionario.class.getName()).log(Level.SEVERE, null, ex);
                return -1l;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return id;
    }

    public static Funcionario read(Long id) {
        String sql = "SELECT id, nome, data de nascimento, telefone, cpf, cargo, email, ativo FROM funcionarios WHERE (id=? AND ativo=?)";
        Funcionario funcionario = null;
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.setBoolean(2, true);

            try (ResultSet resultados = statement.executeQuery()) {
                if (resultados.next()) {
                    funcionario = new Funcionario();
                    funcionario.setId(resultados.getLong("id"));
                    funcionario.setNome(resultados.getString("nome"));
                    funcionario.setDatanascimento(resultado.getDate("data de nascimento"));
                    funcionario.setTelefone(resultados.getString("telefone"));
                    funcionario.setCpf(resultados.getString("cpf"));
                    funcionario.setCargo(resultados.getString("cargo"));
                    funcionario.setEmail(resultados.getString("email"));
                    funcionario.setAtivo(resultados.getBoolean("ativo"));
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return funcionario;
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
            String sql = "UPDATE pets SET nome=?, descricao=?, ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, pet.getNome());
                    statement.setString(2, pet.getDescricao());
                    statement.setBoolean(3, pet.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(4, now);
                    statement.setLong(5, pet.getId());

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
            String sql = "UPDATE pets SET ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setBoolean(1, !pet.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(2, now);
                    statement.setLong(3, pet.getId());
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
