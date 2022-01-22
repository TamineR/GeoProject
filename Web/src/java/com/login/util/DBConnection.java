package com.login.util;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class DBConnection {
 
    public static Connection createConnection()
    {
    	Connection con = null;
	    String url = "jdbc:mysql://localhost/jdbc?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	    String username = "debian-sys-maint";
	    String password = "KI2blgv9Hyap8w8y";
 
    try
    {
    	try {
    	    Class.forName("com.mysql.jdbc.Driver");
    	} catch (ClassNotFoundException e1) {
    	    e1.printStackTrace();
    	}
        
        con = DriverManager.getConnection(url, username, password);
        System.out.println("Post establishing a DB connection - "+con);
    }
    catch (SQLException e)
    {
    	 System.out.println("An error occurred. Maybe user/password is invalid");
         e.printStackTrace();
    }
    
	return con;
    }
}