package dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	
	private static final String dbUser = "root";
    private static final String dbPassword = "password";
    private static final String CONN_STRING = "jdbc:mysql://127.0.0.1:3306/users";

    public static Connection getConnectionToDatabase() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection(CONN_STRING, dbUser, dbPassword);
		createTable(connection); // creates users table if it doesn't exist
        return connection;
    }
    
    private static void createTable(Connection connection) throws SQLException {
    	String sql = "CREATE TABLE users ("
    			+ "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
    			+ "email VARCHAR(255), "
    			+ "username VARCHAR(15), "
    			+ "password CHAR(60));";
    	
    	// check if the table already exists before attempting to create it
    	DatabaseMetaData dbm = connection.getMetaData();
		ResultSet table = dbm.getTables(null,  null, "users", null);
		if (!table.next()) {
			Statement stmt = connection.createStatement();
	    	stmt.execute(sql);
		}
    }
}
