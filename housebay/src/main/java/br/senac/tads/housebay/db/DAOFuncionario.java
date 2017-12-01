package br.senac.tads.housebay.db;

import br.senac.tads.housebay.model.Cargo;
import br.senac.tads.housebay.model.Funcionario;
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
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Diego
 */
public class DAOFuncionario {    
    public static Long create(Funcionario funcionario) {
        String sql = "INSERT INTO funcionarios (nome, data_nascimento, telefone, cpf, cargo, email, hash_senha, ativo, criado, modificado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Long id = null;
        try (Connection connection = SQLUtils.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, funcionario.getNome());
                statement.setTimestamp(2,  new Timestamp(funcionario.getDataNascimento().getTimeInMillis()));
                statement.setString(3, funcionario.getTelefone());
                statement.setString(4, funcionario.getCpf());
                statement.setInt(5, funcionario.getCargo().getValue());
                statement.setString(6, funcionario.getEmail());
                statement.setString(7, funcionario.getSenha());
                
                statement.setBoolean(8, funcionario.isAtivo());
                Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                statement.setTimestamp(9, now);
                statement.setTimestamp(10, now);
                
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
            funcionario.setSenha(null);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return id;
    }

    public static Funcionario read(Long id) {
        String sql = "SELECT id, nome, data_nascimento, telefone, cpf, cargo, email, ativo, criado, modificado FROM funcionarios WHERE (id=? AND ativo=?)";
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
                    funcionario.setDataNascimento(resultados.getTimestamp("data_nascimento").getTime());
                    funcionario.setTelefone(resultados.getString("telefone"));
                    funcionario.setCpf(resultados.getString("cpf"));
                    funcionario.setCargo(Cargo.getCargo(resultados.getInt("cargo")));
                    funcionario.setEmail(resultados.getString("email"));
                    funcionario.setAtivo(resultados.getBoolean("ativo"));
                    funcionario.setCriado(resultados.getTimestamp("criado").getTime());
                    funcionario.setModificado(resultados.getTimestamp("modificado").getTime());
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return funcionario;
    }
    
    public static boolean validaCPF(String cpf) {
        String sql = "SELECT cpf FROM funcionarios WHERE (cpf=?)";
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,cpf);

            try (ResultSet resultados = statement.executeQuery()) {
                if (resultados.next()) {
                    return true;
                }
                else{
                    return false;
                    }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }
    
    public static boolean validaEmail(String email) {
        String sql = "SELECT email FROM funcionarios WHERE (email=?)";
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,email);

            try (ResultSet resultados = statement.executeQuery()) {
                if (resultados.next()) {
                    return true;
                }
                else{
                    return false;
                    }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    public static List<Funcionario> search(String query) {
        String sql;
        if (query != null) {
            sql = "SELECT id, nome, data_nascimento, telefone, cpf, cargo, email, ativo, criado, modificado FROM funcionarios WHERE (UPPER(nome) LIKE UPPER(?) AND ativo=?)";
        } else {
            sql = "SELECT id, nome, data_nascimento, telefone, cpf, cargo, email, ativo, criado, modificado FROM funcionarios WHERE ativo=?";
        }
        List<Funcionario> list = null;
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
                    Funcionario funcionario = new Funcionario();
                    funcionario.setId(resultados.getLong("id"));
                    funcionario.setNome(resultados.getString("nome"));
                    funcionario.setDataNascimento(resultados.getTimestamp("data_nascimento").getTime());
                    funcionario.setTelefone(resultados.getString("telefone"));
                    funcionario.setCpf(resultados.getString("cpf"));
                    funcionario.setCargo(Cargo.getCargo(resultados.getInt("cargo")));
                    funcionario.setEmail(resultados.getString("email"));
                    funcionario.setAtivo(resultados.getBoolean("ativo"));
                    funcionario.setCriado(resultados.getTimestamp("criado").getTime());
                    funcionario.setModificado(resultados.getTimestamp("modificado").getTime());
                    list.add(funcionario);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }

    public static boolean update(Funcionario funcionario) {
        if (funcionario != null && funcionario.getId() != null && funcionario.getId() > 0) {
            String sql = "UPDATE funcionarios SET nome=?, data_nascimento=?, telefone=?, cpf=?, cargo=?, ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, funcionario.getNome());
                    statement.setTimestamp(2, new Timestamp(funcionario.getDataNascimento().getTimeInMillis()));
                    statement.setString(3, funcionario.getTelefone());
                    statement.setString(4, funcionario.getCpf());
                    statement.setInt(5, funcionario.getCargo().getValue());
                    statement.setBoolean(6, funcionario.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(7, now);
                    statement.setLong(8, funcionario.getId());

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

    public static boolean delete(Funcionario funcionario) {
        if (funcionario != null && funcionario.getId() != null && funcionario.getId() > 0) {
            String sql = "UPDATE funcionarios SET ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setBoolean(1, !funcionario.isAtivo());
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(2, now);
                    statement.setLong(3, funcionario.getId());
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
    
    public static boolean updateSenha(Funcionario funcionario, String senha) {
        if (funcionario != null && funcionario.getId() != null && funcionario.getId() > 0) {
            String sql = "UPDATE funcionarios SET hash_senha=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, geraSenha(senha));
                    statement.setLong(2, funcionario.getId());

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
    
    public static String geraSenha(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }
    
    public static Funcionario autenticar(String email, String senha) {
        String sql = "SELECT id, hash_senha FROM funcionarios WHERE (UPPER(email)=UPPER(?) AND ativo=?)";
        long id = -1;
        String hashed = null;
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setBoolean(2, true);

            try (ResultSet resultados = statement.executeQuery()) {
                if (resultados.next()) {
                    id = resultados.getLong("id");
                    hashed = resultados.getString("hash_senha");
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        if (hashed == null || id == -1) { 
            return null;
        } else if (BCrypt.checkpw(senha, hashed)) {
            return read(id);
        } else {
            return null;
        }
    }
    
    /*
    public static Cargo getCargo(long id) {
        return DAOCargo.read(id);
    }
    */
    public static Cargo[] getCargoList() {
        return Cargo.values();
    }
}
