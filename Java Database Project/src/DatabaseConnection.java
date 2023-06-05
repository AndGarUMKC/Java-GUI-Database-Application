import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/KC_EnergyCompany";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "password@123"+ "";

	public static Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		return connection;
	}
}
