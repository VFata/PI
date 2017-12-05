package br.senac.tads.housebay.db;

import br.senac.tads.housebay.exception.PetException;
import br.senac.tads.housebay.model.Pet;
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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Diego
 */
public class DAOPet {
    public static Long create(Pet pet) throws PetException {
        String sql = "INSERT INTO pets (nome, descricao, cliente_id, ativo, criado, modificado) VALUES (?, ?, ?, ?, ?, ?)";
        Long id = null;
        try (Connection connection = SQLUtils.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, pet.getNome());
                statement.setString(2, pet.getDescricao());
                statement.setLong(3, pet.getClienteId());
                statement.setBoolean(4, pet.isAtivo());
                
                long now = Calendar.getInstance().getTime().getTime();
                statement.setTimestamp(5, new Timestamp(now));
                pet.setCriado(now);
                statement.setTimestamp(6, new Timestamp(now));
                pet.setModificado(now);
                
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
                return -1l;
                //System.err.println(ex.getMessage());
                //pet.setId(-1l);
                //Map erros = new HashMap();
                //erros.put("SQLException", "SQL Exception");
                //System.err.println(ex.getMessage());
                //throw new PetException(ex.getMessage(), erros);
                //Logger.getLogger(DAOPet.class.getName()).log(Level.SEVERE, null, ex);
                //return -1l;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return id;
    }

    public static Pet read(Long id) {
        String sql = "SELECT id, nome, descricao, cliente_id, ativo, criado, modificado FROM pets WHERE (id=? AND ativo=?)";
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
                    pet.setClienteId(resultados.getLong("cliente_id"));
                    pet.setAtivo(resultados.getBoolean("ativo"));
                    pet.setCriado(resultados.getTimestamp("criado").getTime());
                    pet.setModificado(resultados.getTimestamp("modificado").getTime());
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
            sql = "SELECT id, nome, descricao, cliente_id, ativo, criado, modificado FROM pets WHERE (UPPER(nome) LIKE UPPER(?) AND ativo=?)";
        } else {
            sql = "SELECT id, nome, descricao, cliente_id, ativo, criado, modificado FROM pets WHERE ativo=?";
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
                    pet.setClienteId(resultados.getLong("cliente_id"));
                    pet.setAtivo(resultados.getBoolean("ativo"));
                    pet.setCriado(resultados.getTimestamp("criado").getTime());
                    pet.setModificado(resultados.getTimestamp("modificado").getTime());
                    list.add(pet);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }

    public static boolean update(Pet pet) throws PetException {
        if (pet != null && pet.getId() != null && pet.getId() > 0) {
            String sql = "UPDATE pets SET nome=?, descricao=?, cliente_id=?, ativo=?, modificado=? WHERE id=?";
            try (Connection connection = SQLUtils.getConnection()) {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, pet.getNome());
                    statement.setString(2, pet.getDescricao());
                    statement.setLong(3, pet.getClienteId());
                    statement.setBoolean(4, pet.isAtivo());
                    
                    Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
                    statement.setTimestamp(5, now);
                    statement.setLong(6, pet.getId());

                    statement.execute();
                    connection.commit();
                } catch (SQLException ex) {
                    connection.rollback();
                    //Map erros = new HashMap();
                    //erros.put("SQLException", "SQL Exception");
                    //System.err.println(ex.getMessage());
                    //throw new PetException(ex.getMessage(), erros);
                    //return false;
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

    public static boolean delete(Pet pet) throws PetException {
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
                //return false;
                //Map erros = new HashMap();
                //erros.put("SQLException", "SQL Exception");
                //System.err.println(ex.getMessage());
                //throw new PetException(ex.getMessage(), erros);
                
                System.err.println(ex.getMessage());
                return false;
            }
            return true;
        } else {
            return false;
        }
    }
    
    public static List<Pet> referencesCliente(long cliente_id) {
        String sql;
        if (cliente_id > 0) {
            sql = "SELECT id, nome, descricao, cliente_id, ativo, criado, modificado FROM pets WHERE (cliente_id=? AND ativo=?)";
        } else {
            return new ArrayList();
        }
        List<Pet> list = null;
        try (Connection connection = SQLUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            if (cliente_id > 0) {
                statement.setLong(1, cliente_id);
                statement.setBoolean(2, true);
            }

            try (ResultSet resultados = statement.executeQuery()) {
                list = new ArrayList<>();
                while (resultados.next()) {
                    Pet pet = new Pet();
                    pet.setId(resultados.getLong("id"));
                    pet.setNome(resultados.getString("nome"));
                    pet.setDescricao(resultados.getString("descricao"));
                    pet.setClienteId(resultados.getLong("cliente_id"));
                    pet.setAtivo(resultados.getBoolean("ativo"));
                    pet.setCriado(resultados.getTimestamp("criado").getTime());
                    pet.setModificado(resultados.getTimestamp("modificado").getTime());
                    list.add(pet);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }
    
    public static void nestedCreatePets(Connection connection, Savepoint savepoint, List<Pet> pets, long id) throws SQLException {
        String sql = "INSERT INTO pets (nome, descricao, cliente_id, ativo, criado, modificado) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        for(Pet pet : pets) {
            try {
                statement = connection.prepareStatement(sql);
                
                statement.setString(1, pet.getNome());
                statement.setString(2, pet.getDescricao());
                statement.setLong(3, id);
                statement.setBoolean(4, pet.isAtivo());
                
                long now = Calendar.getInstance().getTime().getTime();
                statement.setTimestamp(5, new Timestamp(now));
                pet.setCriado(now);
                statement.setTimestamp(6, new Timestamp(now));
                pet.setModificado(now);
                
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
    
    public static void nestedUpdatePets(Connection connection, Savepoint savepoint, List<Pet> pets, long id) throws SQLException, PetException {
        String sqlCreate = "INSERT INTO pets (nome, descricao, cliente_id, ativo, modificado, criado) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlUpdate = "UPDATE pets SET nome=?, descricao=?, cliente_id=?, ativo=?, modificado=? WHERE id=?";
        PreparedStatement statement = null;
        
        List<Pet> oldPets = referencesCliente(id);
        for (Pet velho: oldPets) {
            boolean encontrado = false;
            for (Pet novo: pets) {
                if(novo.getId().equals(velho.getId())) {
                    encontrado = true;
                    break;
                }
            }
            if(!encontrado) {
                delete(velho);
            }
        }
        
        for(Pet pet : pets) {
            try {
                if(pet.getId() == -1) {
                    statement = connection.prepareStatement(sqlCreate);
                } else {
                    statement = connection.prepareStatement(sqlUpdate);
                }
                
                statement.setString(1, pet.getNome());
                statement.setString(2, pet.getDescricao());
                statement.setLong(3, id);
                statement.setBoolean(4, pet.isAtivo());
                
                long now = Calendar.getInstance().getTime().getTime();
                statement.setTimestamp(5, new Timestamp(now));
                pet.setModificado(now);
                
                if(pet.getId() == -1) {
                    statement.setTimestamp(6, new Timestamp(now));
                    pet.setCriado(now);
                } else {
                    statement.setLong(6, pet.getId());
                }
                
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
