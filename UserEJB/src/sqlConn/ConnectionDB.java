package sqlConn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.User;

public class ConnectionDB {

	private static Connection conn;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public ConnectionDB()
	{
		
		 try {
		      // This will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // Setup the connection with the DB
		      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chatapp",
		        		  						 "root","root");
		      
		      System.out.println("Kreirana konekcija " + conn.getClientInfo());
		      
		    } catch (Exception e) {
		      //throw e;
		    	e.printStackTrace();
		    } finally {
		      //close();
		    }
		
	}
	
	public void addUser(User user)
	{
		
		try {
			preparedStatement = conn.prepareStatement("insert into  users values (default, ?, ?)");
			
			preparedStatement.setString(1, user.getUsername());
		    preparedStatement.setString(2, user.getPassword());
		    preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<User> getAllUsers()
	{
		List<User> users = new ArrayList<User>();
		
		try {
			// Statements allow to issue SQL queries to the database
		    statement = conn.createStatement();
		    // Result set get the result of the SQL query		      
		    resultSet = statement.executeQuery("select * from users");
		    
		    User user;
		    
		    while(resultSet.next())
		    {
		    	user = new User(resultSet.getString("username"), resultSet.getString("password"));
		    	users.add(user);
		    	
		    }
		    
		    return users;
		    
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	public void removeUser(User user)
	{
		
		try {
			preparedStatement = conn.prepareStatement("delete from users where id_user= ? ; ");
			preparedStatement.setInt(1, user.getId());
 	        preparedStatement.executeUpdate();
		}catch (SQLException e) {
			//TODO
			e.printStackTrace();
		}
		
	}

	public static Connection getConn() {
		return conn;
	}

	public static void setConn(Connection conn) {
		ConnectionDB.conn = conn;
	}
	
}
