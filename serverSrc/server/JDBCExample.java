import java.sql.*;

public class JDBCExample {
    // JDBC driver name and database URL

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/eventapplication";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "Trial123.";

    public static void main(String[] args) throws SQLException {
        
        String sql;
        String path = "./Resources/users.csv";
        JDBC test = new JDBC("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/eventapplication", "root", "Trial123.");

        //test.openConnection();
        //test.registerUser("Kalin", "Milushev", "kalin@gmail.com", "hello");

        sql = "SELECT * FROM users";
        //test.executeSelectQuery(sql);
        //test.changePassword("kalin@gmail.com", "hello", "hello2");
        //sql = "SELECT * FROM users WHERE email = 'kalin@gmail.com'";

        //test.executeSelectQuery(sql);

        //test.readUsersFromFile(path);
        
        test.readEventsFromFile("./Resources/events.csv");

        //sql = "SELECT * FROM events";

        test.executeSelectQuery(sql);

    }//end main
}//end JDBCExample
