/**
 * 
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author 
 * 
 */

public class DBConnection {
	public static Connection doConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/terminaldb?useSSL=false","root","test");//"host::port/database, user, password"
		return conn;
	}
	
	//Try the connection
	public static void main(String[] args) {
		try {
			System.out.println(DBConnection.doConnection());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
