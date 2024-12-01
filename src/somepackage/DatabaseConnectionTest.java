package somepackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnectionTest is a simple class to test the connection to a Derby database.
 * It attempts to establish a connection to the specified database URL and prints
 * a message indicating whether the connection was successful or not.
 */
public class DatabaseConnectionTest {
    public static void main(String[] args) {
        // Database connection URL
        String dbUrl = "jdbc:derby:/usr/local/DerbyDB;create=true";

        // Database user and password (empty for Derby)
        String dbUser = "";
        String dbPassword = "";

        try {
            // Load the Derby driver class
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // Check if the connection is successful
            if (connection != null) {
                System.out.println("Database connected successfully!");
            } else {
                System.out.println("Failed to connect to the database.");
            }

            // Close the connection
            connection.close();
        } catch (ClassNotFoundException e) {
            // Handle the case where the Derby driver class is not found
            System.err.println("Derby driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            // Handle SQL exceptions, such as connection failures
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
}