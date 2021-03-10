package common.entities;

/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 * CostumerDetails is an entity for storing  the  Costumer Details.
 * We will use this entity to upload data to tables on the screens .
 */

public class CostumerDetails
{
	private String userName;
	private String FirstName;
	private String LastName;
	private String Email;
	private String PhoneNumber;

	public CostumerDetails(String userName ,String FirstName ,String LastName ,String Email, String PhoneNumber)
	{
		this.setUserName(userName);
		this.setFirstName(FirstName);
		this.setLastName(LastName);
		this.setEmail(Email);
		this.setPhoneNumber(PhoneNumber);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
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

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

}
