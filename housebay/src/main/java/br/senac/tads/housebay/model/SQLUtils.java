package br.senac.tads.housebay.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Diego
 */
public class SQLUtils {
    private final static String DB_URI = "jdbc:derby://localhost:1527/housebay";
    private final static String DB_USER = "housebay";
    private final static String DB_PASSWORD = "senhahousebay";
    
    public static Connection getConnection() {
        Connection connection = null;
        
        try {
            Properties properties = new Properties();
            properties.put("user", DB_USER);
            properties.put("password", DB_PASSWORD);
            connection = DriverManager.getConnection(DB_URI, properties);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return connection;
    }
}
