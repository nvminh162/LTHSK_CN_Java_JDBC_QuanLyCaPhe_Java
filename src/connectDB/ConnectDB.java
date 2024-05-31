package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	public static Connection connection = null;
	public static ConnectDB instance = new ConnectDB();
	
	public static ConnectDB getInstance() {
		return instance;
	}
	
	public void connect() throws SQLException {
		String url = "jdbc:sqlserver://localhost:1433;databaseName=N11_QuanLyCaPhe";
		String user = "sa";
		String password = "sapassword";
		connection = DriverManager.getConnection(url, user, password);
	}
	
	public void disconnect() {
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
}
