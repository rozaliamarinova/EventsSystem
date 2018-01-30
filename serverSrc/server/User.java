package server;

public class User {
	private String name;
	private String lastName;
	private String pass;
	private String email;
	
	public User(String name, String lastName, String pass, String email) {
		this.name = name;
		this.lastName = lastName;
		this.pass = pass;
		this.email = email;
	}
	
	public String getUsername() {
		return this.name;
	}
	
	public String getLastname() {
		return this.lastName;
	}
	
	public String getEmail() {
		return this.email;
	}
}
