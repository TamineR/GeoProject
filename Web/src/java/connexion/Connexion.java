package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {

        private static String login = "debian-sys-maint";
        private static String password = "KI2blgv9Hyap8w8y";
	private static String url = "jdbc:mysql://localhost/jee?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private Connection connection = null;
	private static Connexion instane;
	
	private Connexion() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, login, password);
		} catch (ClassNotFoundException e) {
			System.out.println("Driver introvable");
		} catch (SQLException e) {
			System.out.println("Connexion errror");
			System.out.println(e.getMessage());
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public static Connexion getInstane() {
		if(instane == null)
			instane = new Connexion();
		return instane;
	}
	
	
	
	
	
	

}
