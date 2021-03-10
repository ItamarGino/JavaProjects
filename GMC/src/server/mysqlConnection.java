package server;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TimeZone;

import client.controllers.ClientMessages;
import common.controllers.Message;
import common.controllers.OperationType;
import common.controllers.ReturnMsgType;
import common.entities.VersionDetails;
import javafx.scene.control.Alert.AlertType;


public class mysqlConnection {

	static Connection conn;
	public static void openConnection(String password)
	{
		try 
		{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {/* handle the error*/}
        
        try 
        {
        	conn = DriverManager.getConnection("jdbc:mysql://localhost/gcm?serverTimezone=IST","root",password);
            //Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.3.68/test","root","Root");
            System.out.println("SQL connection succeed");
            //createTableCourses(conn);
            //printCourses(conn);
     	} catch (SQLException ex) 
     	    {/* handle any errors*/
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            }
   	}
	
	
	public ResultSet getQuery (String query) {
	
		ResultSet rs=null;
		try {
			Statement stmt=conn.createStatement();
			rs=stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public boolean insertOrUpdate (String query) {
		
		try {
			Statement stmt=conn.createStatement();
			stmt.executeUpdate(query);
			System.out.println("my sql "+ query);
			return true;
		} catch (SQLException e) {
			System.out.println("insert or update exception !");
			return false;
		}
	}
		
	public void in (String sql,VersionDetails v,InputStream inputStream) {
		try {
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, v.getCountryName());
		statement.setString(2, v.getCityName());
		statement.setDouble(3, (Double.parseDouble(v.getVersionNumber())));
		statement.setBlob(4, inputStream);
		statement.executeUpdate();
		}
		catch(Exception e) {
			System.err.println("in Exception - DB");
		}

	}
	
	
}


