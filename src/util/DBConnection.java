package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages database connection.
 */
public class DBConnection {

    /** Protocol */
    private static final String protocol = "jdbc";
    /** Vendor */
    private static final String vendorName = ":mysql:";
    /** DB IP Address */
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    /** Database Name */
    private static final String dbName = "WJ06RWU";

    /** Builds the connection string. */
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    /** Driver: mysql-connector-java-8.0.22 */
    private static final String mySQLJDBCDriver = "com.mysql.cj.jdbc.Driver";

    /** Username */
    private static final String username = "U06RWU";
    /** Password is retrieved from the git ignored DBPassword class. */
    private static String password = DBPassword.getPassword();

    /** Holds the database connection. */
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

