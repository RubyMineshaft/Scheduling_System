package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages database connection.
 */
public class DBConnection {

    //Connection settings
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ06RWU";

    //Build the connection string
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    //Driver: mysql-connector-java-8.0.22
    private static final String mySQLJDBCDriver = "com.mysql.cj.jdbc.Driver";

    //Username and password
    private static final String username = "U06RWU";
    private static String password = DBPassword.getPassword();

    private static Connection conn = null;

    /** Storts the database connection.
     * @return the database connection
     */
    public static Connection startConnection(){
        try {
            Class.forName(mySQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL,username,password);

            System.out.println("Connection successful!");
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /** Gets the database connection.
     * @return the database connection.
     */
    public static Connection getConnection(){
        return conn;
    }

    /**
     * Closes the database connection.
     */
    public static void closeConnection(){
        try {
            conn.close();
            System.out.println("Connection closed.");
        } catch (SQLException ignored) {

        }
    }
}

