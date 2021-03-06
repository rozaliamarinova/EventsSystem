package server;

public class User {
	private String name;
	private String lastName;
	private String pass;
	private String email;
	private int id;
	private String interests;
	
	public User() {}
	
	public User(String name, String lastName, String pass, String email, String interests) {
		this.name = name;
		this.lastName = lastName;
		this.pass = pass;
		this.email = email;
		this.interests = interests;
	}
	
	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getUsername() {
		return this.name;
	}
	
	public String getEmail() {
		return this.email;
	}
}
