package common.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;

import common.controllers.Message;

/**

* @author Shahar Ronen.
* @author Dorin Segall.
* @author Remez David.
* @author Itamar Gino.
* @author Amit Sinter.
* @version June 2019
* 
* Tour Entity Temporarily saves all Tour data
Data that exists in the Tour and Tour in map tables on the DB
We use it to transfer data between users and between the controller and the server 
*/
public class Tour implements Serializable
{private String countryname;
private String cityename;
private String tourname;
private String tdescription;
private String recommanded;
private ArrayList<String> Sites ;
private String IDMAP;


public Tour() {
}

public Tour(ResultSet rs_Tour,ResultSet rs_Sites) {
	try {
	countryname=rs_Tour.getString("CountryName");
	cityename=rs_Tour.getString("CityName");
	tourname=rs_Tour.getString("TourName");
	tdescription=rs_Tour.getString("Description");
	recommanded=rs_Tour.getString("Recommended");
	Sites=new ArrayList<String>();
	while(rs_Sites.next()) {
			Sites.add(rs_Sites.getString("SiteName"));
		}
	}
	catch(Exception e) {
		System.out.println("Cant Create Tour");
		e.printStackTrace();
	}
	
}

public Tour(String countryname, String cityename, String tourname, String tdescription, String recommanded,String IDMAP) {
	super();
	this.countryname = countryname;
	this.cityename = cityename;
	this.tourname = tourname;
	this.tdescription = tdescription;
	this.recommanded = recommanded;
	this.IDMAP = IDMAP;
	this.Sites = null;

}



public  ArrayList<String> getSites() {
	return Sites;
}


public  void setSites(ArrayList<String> sites) {
	Sites = sites;
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
public String getTourname() {
	return tourname;
}
public void setTourname(String tourname) {
	this.tourname = tourname;
}
public String getTdescription() {
	return tdescription;
}
public void setTdescription(String tdescription) {
	this.tdescription = tdescription;
}
public String isRecommanded() {
	return recommanded;
}
public void setRecommanded(String recommanded) {
	this.recommanded = recommanded;
}


public String getRecommanded() {
	return recommanded;
}




public String getIDMAP() {
	return IDMAP;
}


public void setIDMAP(String iDMAP) {
	IDMAP = iDMAP;
}

}
