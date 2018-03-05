package database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.Event;
import server.User;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalin
 */
public class JDBC {

	private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final static String DB_URL = "jdbc:mysql://127.0.0.1:3306/eventapplication";
	private final static String USER = "root";
	private final static String PASS = "root";

//	public JDBC(String JDBC_DRIVER, String DB_URL, String USER, String PASS) {
//		this.JDBC_DRIVER = JDBC_DRIVER;
//		this.DB_URL = DB_URL;
//		this.USER = USER;
//		this.PASS = PASS;
//		this.conn = null;
//		this.stmt = null;
//	}

	public static String getJDBC_DRIVER() {
		return JDBC_DRIVER;
	}

	public static String getDB_URL() {
		return DB_URL;
	}

	public static String getUSER() {
		return USER;
	}

	public static String getPASS() {
		return PASS;
	}
	
	protected static Map<Integer, String[]> executeSelectQuery(String sql) {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			Map<Integer, String[]> rows = new HashMap<Integer, String[]>();
			String[] row = new String[numberOfColumns];
			while (rs.next()) {
				System.out.println("Reading entry from DB");
				for (int i = 1; i < numberOfColumns; i++) {
					row[i - 1] = rs.getString(i);
				}
				rows.put(Integer.parseInt(row[0]), row);
			}

			rs.close();
			return rows;

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			return null;
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
	} // end executeQuery

	public static Map<Integer, User> getUers(String sql) {
		Map<Integer, String[]> usersStr = executeSelectQuery(sql);
		Map<Integer, User> users = new HashMap<Integer, User>();
		Iterator<Integer> iter = usersStr.keySet().iterator();
		while (iter.hasNext()) {
			User user = new User();
			String[] userData = usersStr.get(iter.next());
			user.setId(Integer.parseInt(userData[0]));
			user.setName(userData[1]);
			user.setLastName(userData[2]);
			user.setEmail(userData[3]);
			user.setPass(userData[4]);
			user.setInterests(userData[5]);
			users.put(user.getId(), user);
		}
		return users;
	}
	
	public static Map<Integer, Event> getEvents(String sql) {
		System.out.println("query: " + sql);
		Map<Integer, String[]> eventsStr = executeSelectQuery(sql);
		Map<Integer, Event> events = new HashMap<Integer, Event>();
		Iterator<Integer> iter = eventsStr.keySet().iterator();
		while (iter.hasNext()) {
			Event event = new Event();
			String[] eventData = eventsStr.get(iter.next());
			event.setId(Integer.parseInt(eventData[0]));
			event.setName(eventData[1]);
			event.setPlace(eventData[2]);
			event.setDate(eventData[3]);
			event.setLinkEvent(eventData[4]);
			event.setLinkPhoto(eventData[5]);
			events.put(event.getId(), event);
		}
		return events;
	}
	
	protected static void registerUser(String firstName, String lastName, String email, String password, String interests) {
		String sql;
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			sql = "INSERT IGNORE INTO users (first_name, last_name, email, password, interests) " + "VALUES (" + "'" + firstName
					+ "', '" + lastName + "', '" + email + "', '" + password + "', '" + interests + "'" + ")";
			stmt.executeUpdate(sql);

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	} // end registerUser

	protected static boolean isValidLogin(String email, String password) {
        String sql;
        Connection conn = null;
        Statement stmt = null;
        boolean isValidLogin = true;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            sql = "SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "'";
            System.out.println("SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "'");

            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.next()) {
                isValidLogin = false;
            }

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return isValidLogin;
    } // end registerUser
    
    protected static boolean isValidRegistration(String email, String password) {
        String sql;
        Connection conn = null;
        Statement stmt = null;
        boolean isValidRegistration = true;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            sql = "SELECT * FROM users WHERE email = '" + email + "'";

            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.next()) {
                isValidRegistration = false;
            }

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return isValidRegistration;
    } // end registerUser


	protected static void changePassword(String email, String password, String newPassword) {
		String sql;
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			sql = "UPDATE users SET password = '" + newPassword + "' WHERE email = '" + email + "'";
			stmt.executeUpdate(sql);

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	} // end changePassword

	public static void readUsersFromFile(String path) throws SQLException {
		String csvFile = path;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int counter = 0;
		String sql;
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				String[] user = line.split(cvsSplitBy);

				sql = "INSERT INTO users (first_name, last_name, email, password, interests, attended_per_category) "
						+ "VALUES (" + "'" + user[0] + "', '" + user[1] + "', '" + user[2] + "', '" + user[3] + "', '"
						+ user[4] + "', '" + user[5] + "'" + ")";

				stmt.executeUpdate(sql);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} // end try catch block

	}// end readUsersFromFile

	public static void readEventsFromFile(String path) throws SQLException {
		String csvFile = path;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int counter = 0;
		String sql;
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				String[] user = line.split(cvsSplitBy);

				sql = "insert into events (name, place, date, link, image_link) " + "VALUES (" + "'" + user[0] + "', '"
						+ user[1] + "', '" + user[2] + "', '" + user[3] + "', '" + user[4] + "'" + ")";

				stmt.executeUpdate(sql);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} // end try catch block

	}// end readEventsFromFile

	public static List<Event> read(String path) throws SQLException {
		String csvFile = path;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int id = 0;
		List<Event> events = new ArrayList<>();

		String[] user = new String[10];
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				user = line.split(cvsSplitBy);
				List<String> list = Arrays.asList(user);
				Event event = new Event();
				event.setId(id++);
				event.setName(list.get(0));
				event.setPlace(list.get(1));
				event.setDate(list.get(2));
				event.setLinkEvent(list.get(3));
				event.setLinkPhoto(list.get(4));

				System.out.println(event);
				events.add(event);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return events;
	}

}