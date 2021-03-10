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
* Site Entity Temporarily saves all site data
Data that exists in the site and site in map tables on the DB
We use it to transfer data between users and between the controller and the server 
*/
public class Site implements Serializable
{
private String countryname;
private String cityename;
private String sitename;
private String location_X;
private String location_y;
private String catagory;
private String description;
private String accessibility;
private String IDMAP;
private String RecommendedVisitTime;

public Site() {	
}
public Site(ResultSet rs) {
	try {
		countryname=rs.getString("CountryName");
		cityename=rs.getString("cityname");
		sitename=rs.getString("SiteName");
		location_X=Double.toString(rs.getDouble("SiteLocationX"));
		location_y=Double.toString(rs.getDouble("SiteLocationY"));
		catagory=rs.getString("Category");
		description=rs.getString("Description");
		accessibility=(rs.getString("Accessibility"));
		RecommendedVisitTime=(rs.getString("RecommendedVisitTime"));
	} catch (SQLException e) {
		System.out.println("Faild To Create New Site!");
		e.printStackTrace();
	}	
}

public Site(String countryname, String cityename, String sitename, String location_x, String location_y,String catagory,
			String description, String accessibility,String IDMAP, String RecommendedVisitTime) {
		super();
		this.countryname = countryname;
		this.cityename = cityename;
		this.sitename = sitename;
		this.location_X = location_x;
		this.location_y = location_y;
		this.catagory = catagory;
		this.description = description;
		this.accessibility = accessibility;
		this.IDMAP=IDMAP;
        this.RecommendedVisitTime=RecommendedVisitTime;
	}


public String getCountryname() {
	return countryname;
}

public void setCountryname(String countryname) {
	this.countryname = countryname;
}

public String getCityename() {
	return cityename;
}

public void setCityename(String cityename) {
	this.cityename = cityename;
}

public String getSitename() {
	return sitename;
}

public void setSitename(String sitename) {
	this.sitename = sitename;
}

public String getLocation_X() {
	return location_X;
}

public void setLocation_X(String location_X) {
	this.location_X = location_X;
}

public String getLocation_y() {
	return location_y;
}

public void setLocation_y(String location_y) {
	this.location_y = location_y;
}

public String getCatagory() {
	return catagory;
}

public void setCatagory(String catagory) {
	this.catagory = catagory;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String isAccessibility() {
	return accessibility;
}

public void setAccessibility(String accessibility) {
	this.accessibility = accessibility;
}


public String getIDMAP() {
	return IDMAP;
}


public void setIDMAP(String iDMAP) {
	IDMAP = iDMAP;
}


public String getRecommendedVisitTime() {
	return RecommendedVisitTime;
}


public void setRecommendedVisitTime(String recommendedVisitTime) {
	RecommendedVisitTime = recommendedVisitTime;
}
	 
	
}
