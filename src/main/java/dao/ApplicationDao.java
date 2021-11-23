package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import util.PasswordHash;

public class ApplicationDao {
	
	PasswordHash passwordHash;
	
	public ApplicationDao() {
		passwordHash = new PasswordHash();
	}
	
	public boolean insert(User user) {
		try {
			String sql = "INSERT INTO USERS (email, username, password) VALUES(?, ?, ?);";
			String hashedPassword = passwordHash.hashPassword(user.getPassword());
			Connection connection = DBConnection.getConnectionToDatabase();
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getUsername());
			stmt.setString(3, hashedPassword);
			stmt.executeUpdate();
			connection.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkRegistrationEmail(String email) {
		String emailRegex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (!email.matches(emailRegex)) {
			System.out.println("ERROR: 3");
			return false;
		}
		return true;
	}
	
	public boolean checkRegistrationUsername(String username) {
		if (username.length() < 4 || username == null) {
			System.out.println("ERROR: 1");
			return false;
		}
		return true;
	}
	
	public boolean checkRegistrationPassword(String password) {
		if (password.length() < 8 || password == null) {
			System.out.println("ERROR: 2");
			return false;
		}
		return true;
	}
	
	public boolean checkIfAccountExists(User user) {
		try {
			Connection connection = DBConnection.getConnectionToDatabase();
			String emailEnteredByUser = user.getEmail();
			String usernameEnteredByUser = user.getUsername();
			String sqlEmail = "SELECT * FROM USERS WHERE email = ?";
			String sqlUsername = "SELECT * FROM USERS WHERE username = ?";
			
			PreparedStatement emailStmt = connection.prepareStatement(sqlEmail);
			PreparedStatement usernameStmt = connection.prepareStatement(sqlUsername);
			
			emailStmt.setString(1, emailEnteredByUser);
			usernameStmt.setString(1, usernameEnteredByUser);
			
			ResultSet emailResultSet = emailStmt.executeQuery();
			ResultSet usernameResultSet = usernameStmt.executeQuery();
			
			if (emailResultSet.next() || usernameResultSet.next()) {
				return true;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean verifyLogin(User user) {
		try {
			Connection connection = DBConnection.getConnectionToDatabase();
			
			String passwordEnteredByUser = user.getPassword();
			String sql = "SELECT * FROM USERS WHERE username = ?";
			
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			
			ResultSet rs = stmt.executeQuery();
			String passwordFromDatabase = "";
			while (rs.next()) {
				passwordFromDatabase = rs.getString("password");
			}
			connection.close();
			// Compare the password entered to the hashed password
			 if (passwordHash.verifyPassword(passwordFromDatabase, passwordEnteredByUser)) {
				 connection.close();
				 return true;
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void updatePassword(String email, String password) {
		
		try {
			Connection connection = DBConnection.getConnectionToDatabase();
			String hashedPassword = passwordHash.hashPassword(password);
			String sql = "UPDATE USERS "
					+ "SET password = ? "
					+ "WHERE email = ?;";
			
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, hashedPassword);
			stmt.setString(2, email);
			stmt.executeUpdate();
			connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public long getUserId(String username) {
		long userId = -1;
		try {
			Connection connection = DBConnection.getConnectionToDatabase();
			String sql = "SELECT ID FROM USERS "
					+ "WHERE username = ?;";
			
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				userId = rs.getLong("id");
			}
			connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userId;
	}
}
