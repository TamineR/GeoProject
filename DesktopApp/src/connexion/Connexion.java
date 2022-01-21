package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Connexion {

	private static String url = "jdbc:mysql://localhost/jee?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String login ="debian-sys-maint";
	private static String password ="KI2blgv9Hyap8w8y";
	private Connection cn = null;
	private static Connexion instance = null;
	private Connexion() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			cn = DriverManager.getConnection(url, login, password);	
		} catch (ClassNotFoundException e) {
			System.out.println("Le driver est introvable");
		} catch (SQLException e) {
			System.out.println("Impossible de se connecter");
			System.out.println(e.getMessage());
		}
	}
	public static Connexion getInstance() {
		if(instance == null)
			return new Connexion();
		return instance;
	}
	public Connection getCn() {
		return cn;
	}
	
	
	
	
	
	
}
