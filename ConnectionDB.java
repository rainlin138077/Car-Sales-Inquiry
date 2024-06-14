import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    // JDBC URL, username and password of MySQL server
    private static final String URL = "jdbc:mariadb://0.tcp.jp.ngrok.io:11051/411177020";
    private static final String USER = "411177020";
    private static final String PASSWORD = "411177020";

    // JDBC variables for opening and managing connection

    public static Connection Connection() throws SQLException {
    	return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
