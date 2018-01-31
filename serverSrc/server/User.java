package server;

public class User {
	private String name;
	private String lastName;
	private String pass;
	private String email;
	private int id;
	private String interests;
	
	public User(String name, String lastName, String pass, String email, int id, String interests) {
		this.name = name;
		this.lastName = lastName;
		this.pass = pass;
		this.email = email;
		this.id = id;
		this.interests = interests;
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
