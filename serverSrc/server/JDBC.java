
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private final String JDBC_DRIVER;
    private final String DB_URL;
    private final String USER;
    private final String PASS;
    private Connection conn;
    private Statement stmt;

    public JDBC(String JDBC_DRIVER, String DB_URL, String USER, String PASS) {
        this.JDBC_DRIVER = JDBC_DRIVER;
        this.DB_URL = DB_URL;
        this.USER = USER;
        this.PASS = PASS;
        this.conn = null;
        this.stmt = null;
    }

    public String getJDBC_DRIVER() {
        return this.JDBC_DRIVER;
    }

    public String getDB_URL() {
        return this.DB_URL;
    }

    public String getUSER() {
        return this.USER;
    }

    public String getPASS() {
        return this.PASS;
    }

    public Connection getConn() {
        return this.conn;
    }

    public Statement getStmt() {
        return this.stmt;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    protected void openConnection() {
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            this.conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            this.stmt = this.conn.createStatement();
            System.out.println("Created statement...");

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
    } // end openConnection

    protected int getEventID(String name) {
        int id = 0;
        try {
            //STEP 2: Register JDBC driver
            
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
            this.stmt = this.conn.createStatement();
            ResultSet rs = this.stmt.executeQuery("Select events_id From events Where name = '" + name + "'");
            while (rs.next()) {

                //Retrieve by column name
                id = rs.getInt("events_id");
            }

            rs.close();

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
        return id;
    } // end executeQuery

    protected void executeSelectQuery(String sql) {
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
            this.stmt = this.conn.createStatement();
            ResultSet rs = this.stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();

            //PrintColumnTypes.printColTypes(rsmd);
            System.out.println("");

            int numberOfColumns = rsmd.getColumnCount();

            for (int i = 1; i <= numberOfColumns; i++) {
                if (i > 1) {
                    System.out.print(",  ");
                }
                String columnName = rsmd.getColumnName(i);
                System.out.print(columnName);
            }
            System.out.println("");

            while (rs.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    if (i > 1) {
                        System.out.print(",  ");
                    }
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println("");
            }

            rs.close();

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
    } // end executeQuery

    protected void registerUser(String firstName, String lastName, String email, String password) {
        String sql;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
            this.stmt = this.conn.createStatement();

            sql = "INSERT IGNORE INTO users (first_name, last_name, email, password) "
                    + "VALUES (" + "'" + firstName + "', '" + lastName + "', '" + email + "', '" + password + "'" + ")";
            stmt.executeUpdate(sql);

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
    } // end registerUser

    protected boolean isRegistered(String email, String password) {
        String sql;
        boolean isRegistered = true;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            this.conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
            this.stmt = this.conn.createStatement();

            sql = "SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "'";
            System.out.println("SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "'");

            ResultSet rs = this.stmt.executeQuery(sql);

            if (!rs.next()) {
                isRegistered = false;
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
        return isRegistered;
    } // end registerUser

    protected void changePassword(String email, String password, String newPassword) {
        String sql;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
            this.stmt = this.conn.createStatement();

            sql = "UPDATE users SET password = '" + newPassword + "' WHERE email = '" + email + "'";
            stmt.executeUpdate(sql);

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
    } // end changePassword

    public void readUsersFromFile(String path) throws SQLException {
        String csvFile = path;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int counter = 0;
        String sql;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
            this.stmt = this.conn.createStatement();
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                String[] user = line.split(cvsSplitBy);

                sql = "INSERT INTO users (first_name, last_name, email, password, interests, attended_per_category) "
                        + "VALUES (" + "'" + user[0] + "', '" + user[1] + "', '" + user[2] + "', '" + user[3] + "', '" + user[4] + "', '" + user[5] + "'" + ")";

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

    public void readEventsFromFile(String path) throws SQLException {
        String csvFile = path;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int counter = 0;
        String sql;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
            this.stmt = this.conn.createStatement();
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                String[] user = line.split(cvsSplitBy);

                sql = "insert into events (name, place, date, link, image_link) "
                        + "VALUES (" + "'" + user[0] + "', '" + user[1] + "', '" + user[2] + "', '" + user[3] + "', '" + user[4] + "'" + ")";

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

}