package model;

public class User {
	
	private String email;
	private String username;
	private String password;
	private String code;

	public User(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public User(String email, String username, String password, String code) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.code = code;
	}

	public User(String code) {
		this.code = code;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
