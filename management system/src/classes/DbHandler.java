package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DbHandler {
    protected Connection dbconnection;
    public Connection getConnection() {
        String host = "localhost";
        String port = "3306";
        String dbname = "studentmanagement";
        String user = "root";
        String password = "saim";
        final String ConnectionString = "jdbc:mysql://" + host + ":" + port + "/" + dbname;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        try {
            dbconnection = DriverManager.getConnection(ConnectionString, user, password);
            if(dbconnection != null) {
            	System.out.println("Connection Established");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return dbconnection;
    }

}
