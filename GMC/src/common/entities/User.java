package common.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
/**

* @author Shahar Ronen.
* @author Dorin Segall.
* @author Remez David.
* @author Itamar Gino.
* @author Amit Sinter.
* @version June 2019
* 
* Tour Entity Temporarily saves all User data
We use it to transfer data between the controller and the server 
*/
public class User implements Serializable {
	
	private String UserName;
	private String FirstName;
	private String LastName;
	private String Email;
	private String PhoneNumber;
	private String Role;
	public User(ResultSet rs) {
		try {
			UserName = rs.getString("userName");

		FirstName = rs.getString("FirstName");
		LastName = rs.getString("LastName");
		Email = rs.getString("Email");
		PhoneNumber = rs.getString("PhoneNumber");
		Role = rs.getString("Role");

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.err.println("User entity exception");
	}
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public String getRole() {
		return Role;
	}
	public void setRole(String role) {
		Role = role;
	}
}
