package common.entities;

/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * EmployeeDetails is an entity for storing data
 * We will use this entity to upload data to tables on the screens and Send data in the object to the server.
 */

public class EmployeeDetails
{
	private String id;
	private String fname;
	private String lname;
	private String email;
	private String erole;
	
	public EmployeeDetails(String id,String fname, String lname, String email,String erole)
	{
		this.id=id;
		this.fname=fname;
		this.lname=lname;
		this.email=email;
		this.erole=erole;
	}
	
	public EmployeeDetails() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getErole() {
		return erole;
	}
	public void setErole(String erole) {
		this.erole = erole;
	}
	
}
