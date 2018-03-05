package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

	private static DBManager instance;
	private static final String DB_IP = "localhosty";
	private static final String DB_PORT = "3306";
	private static final String DB_NAME = "eventapplication";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "root";
	
	private Connection con = null;
	
	private DBManager(){
		//create the connection
		try {
			con = DriverManager.getConnection("jdbc:mysql://"+DB_IP+":"+DB_PORT+"/"+DB_NAME,DB_USER, DB_PASS);
		} catch (SQLException e) {
			System.out.println("Error connecting to Database - " + e.getMessage());
		}
	}
	
	public static synchronized DBManager getInstance(){
		if(instance == null){
			instance = new DBManager();
		}
		return instance;
	}
	
	public Connection getConnection() {
		return con;
	}
}
