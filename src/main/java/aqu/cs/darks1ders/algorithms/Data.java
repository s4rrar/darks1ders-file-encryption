package aqu.cs.darks1ders.algorithms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Data {
    Connection connection;
    
    public Data() {
        connection = getConnection();
    }
    
    public static Connection getConnection() {
        Connection connection = null;
        try {
            String USERNAME = "darks1ders";
            String PASSWORD = "8aP.7mG#7nK*7mP!3uY^8tU@1lK.1k";
            String DB_URL = "jdbc:derby:" + System.getProperty("user.dir") + "\\data";
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException c) {
        }
        return connection;
    }
    
    public User getUser(String username) {
        if(username == null || username.isEmpty()) {
            return null;
        }
        User user = null;
        try {
            if(connection == null || connection.isClosed()) {
                connection = getConnection();
            }
            Statement statement = connection.createStatement();
            String sql = "select * from users where username='" + username + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()) {
                user = new User(resultSet.getString("username"), resultSet.getString("password"));
            }
        } catch (SQLException c) {
            
        } finally {
        }
        return user;
    }
}
