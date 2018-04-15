package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import exceptions.AlreadyRegisteredUserException;
import server.Event;
import server.User;

/**
 *
 * @author kalin
 */
public class JDBC {

	private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final static String DB_URL = "jdbc:mysql://127.0.0.1:3306/eventapplication";
	private final static String USER = "root";
	private final static String PASS = "root";
	private Connection conn;
	private Statement stmt;

	public JDBC() {
		try {
			Class.forName(JDBC_DRIVER);
			this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("conn");
			this.setStmt(this.conn.createStatement());
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Cannot find the driver in the classpath!", e);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void close() throws SQLException {
		this.conn.close();
	}

	 private void setStmt(Statement stmt) {
		 this.stmt = stmt;
	 }

	public int getEventID(String name) {
		int id = 0;
		String sql = "Select events_id From events Where name = '" + name + "'";
		try (ResultSet rs = this.stmt.executeQuery(sql)) {
			while (rs.next()) {

				// Retrieve by column name
				id = rs.getInt("events_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public Map<Integer, String[]> executeSelectQuery(String sql) {
		Map<Integer, String[]> rows = new HashMap<Integer, String[]>();

		try (ResultSet rs = stmt.executeQuery(sql)) {
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			String[] row = new String[numberOfColumns];
			while (rs.next()) {
				System.out.println("Reading entry from DB");
				for (int i = 1; i < numberOfColumns; i++) {
					row[i - 1] = rs.getString(i);
				}
				rows.put(Integer.parseInt(row[0]), row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows;

	}

	public Map<Integer, User> getUsers(String sql) {
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

	public Map<Integer, Event> getEvents(String sql) {
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

	public void registerUser(User user) throws SQLException,
		AlreadyRegisteredUserException {
		String sql = "INSERT IGNORE INTO users (first_name, last_name, email, password, interests) " + "VALUES ('"
				+ user.getName() + "', '" + user.getLastName() + "', '" + user.getEmail() + "', '" + user.getPass()
				+ "', '" + user.getInterests() + "')";
		
			int count = stmt.executeUpdate(sql);
			if (count < 1) {
				throw new AlreadyRegisteredUserException();
			}
	}

	public User getInfoForUserBasedOnMail(String email, String password) {
		User user = null;
		String sql = "SELECT first_name FROM users WHERE email = '" + email + "' AND password = '" + password + "'";
		System.out.println("SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "'");

		try (ResultSet rs = stmt.executeQuery(sql)) {
			if (rs.next()) {
	            String name = rs.getString("first_name");
	            user = new User(name, email);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public boolean isValidLogin(String email, String password) {
		String sql = "SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "'";
		System.out.println("SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "'");

		boolean isValidLogin = false;
		try (ResultSet rs = stmt.executeQuery(sql)) {
			isValidLogin = rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isValidLogin;
	}

	public void changePassword(String email, String password, String newPassword) {
		String sql = "UPDATE users SET password = '" + newPassword + "' WHERE email = '" + email + "'";
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void readUsersFromFile(String path) throws SQLException {
		String csvFile = path;
		String line = "";
		String cvsSplitBy = ",";
		String sql;
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			while ((line = br.readLine()) != null) {

				String[] user = line.split(cvsSplitBy);

				sql = "INSERT INTO users (first_name, last_name, email, password, interests, attended_per_category) "
						+ "VALUES (" + "'" + user[0] + "', '" + user[1] + "', '" + user[2] + "', '" + user[3] + "', '"
						+ user[4] + "', '" + user[5] + "'" + ")";

				stmt.executeUpdate(sql);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void readEventsFromFile(String path) throws SQLException {
		String csvFile = path;
		String line = "";
		String cvsSplitBy = ",";
		String sql;
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			while ((line = br.readLine()) != null) {

				String[] user = line.split(cvsSplitBy);

				sql = "insert into events (name, place, date, link, image_link) " + "VALUES (" + "'" + user[0] + "', '"
						+ user[1] + "', '" + user[2] + "', '" + user[3] + "', '" + user[4] + "'" + ")";

				stmt.executeUpdate(sql);
			}
		} catch (IOException|SQLException e) {
			e.printStackTrace();
		}

	}

	public static List<Event> read(String path) throws SQLException {
		String csvFile = path;
		String line = "";
		String cvsSplitBy = ",";
		int id = 0;
		List<Event> events = new ArrayList<>();

		String[] user = new String[10];
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		return events;
	}

}