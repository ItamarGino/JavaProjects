package common.entities;

import java.io.Serializable;

/**

* @author Shahar Ronen.
* @author Dorin Segall.
* @author Remez David.
* @author Itamar Gino.
* @author Amit Sinter.
* @version June 2019
* 
* VersionDetails Entity Temporarily saves all Version data
Data that exists in the Version table on the DB
We use it to transfer data between users and between the controller and the server 
*/
public class VersionDetails implements Serializable
{
	private String CountryName;
	private String CityName;
	private String MapID;
	private String EmployeeID;
	private String VersionNumber;

	public VersionDetails() {
	
	}


	public VersionDetails(String CountryName,String CityName, String MapID, String EmployeeID, String VersionNumber)
	{
		this.CountryName = CountryName;
		this.CityName = CityName;
		this.MapID = MapID;
		this.EmployeeID = EmployeeID;
		this.VersionNumber = VersionNumber;
	}
	

	public String getCountryName() {
		return CountryName;
	}

	public void setCountryName(String countryName) {
		CountryName = countryName;
	}

	public String getCityName() {
		return CityName;
	}

	public void setCityName(String cityName) {
		CityName = cityName;
	}

	public String getMapID() {
		return MapID;
	}

	public void setMapID(String mapID) {
		MapID = mapID;
	}

	public String getEmployeeID() {
		return EmployeeID;
	}

	public void setEmployeeID(String employeeID) {
		EmployeeID = employeeID;
	}

	public String getVersionNumber() {
		return VersionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		VersionNumber = versionNumber;
	}
}
