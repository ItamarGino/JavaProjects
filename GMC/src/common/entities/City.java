package common.entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.image.Image;

/**
 * City class for a city that holds all the city information
 * @author Remez David,Itamar Gino,Amit Sinter,Dorin segall,Shahar Ronen
 */
public class City implements Serializable 
{
	private String CountryName;
	private String CityName;
	private String ViewsCount ;
	private String Price;
	private String NumberOfMaps;
	private String NumberOfPopularPlaces;
	private String NumberOfTours;
	private String VersionNumber;
	private String CityImageUrl;
	private ArrayList<Map> CityMaps;
	/**
	 * City constructor to initialize all the data of a city 
	 * @param rs the result set from the Data Base 
	 */
	public City(ResultSet rs){  
		try {
			File photo;
			CountryName=rs.getString("CountryName");
			CityName=rs.getString("CityName");
			Price=rs.getString("Price");
			NumberOfMaps=rs.getString("MapQuantity");
			NumberOfPopularPlaces=rs.getString("No. of POI");
			NumberOfTours=rs.getString("No. of Tours");
			VersionNumber=rs.getString("Version No.");
			ViewsCount=rs.getString("ViewCount");
			if((photo=new File("Citys/"+CityName+".jpg")).isFile()) {  //Checking if we already retrieve the picture of the city from the data base 
				CityImageUrl=(photo.toURI().toString());}
			else//else we create a new photo of the city 
			{
				InputStream is =rs.getBinaryStream("CitySymbol");
				 photo =new File("Citys/"+CityName+".jpg");
				OutputStream os = new FileOutputStream(photo);
				byte [] content =new byte[1024];
				int size=0;
				while((size=is.read(content))!=-1) {
					os.write(content, 0,size);	
				}
				CityImageUrl=(photo.toURI().toString());
				os.close();
				is.close();
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

	public String getViewsCount() {
		return ViewsCount;
	}

	public void setViewsCount(String viewsCount) {
		ViewsCount = viewsCount;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getNumberOfMaps() {
		return NumberOfMaps;
	}

	public void setNumberOfMaps(String numberOfMaps) {
		NumberOfMaps = numberOfMaps;
	}

	public String getNumberOfPopularPlaces() {
		return NumberOfPopularPlaces;
	}

	public void setNumberOfPopularPlaces(String numberOfPopularPlaces) {
		NumberOfPopularPlaces = numberOfPopularPlaces;
	}

	public String getNumberOfTours() {
		return NumberOfTours;
	}

	public void setNumberOfTours(String numberOfTours) {
		NumberOfTours = numberOfTours;
	}

	public String getVersionNumber() {
		return VersionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		VersionNumber = versionNumber;
	}

	public String getCityImageUrl() {
		return CityImageUrl;
	}

	public void setCityImageUrl(String cityImageUrl) {
		CityImageUrl = cityImageUrl;
	}

	public ArrayList<Map> getCityMaps() {
		return CityMaps;
	}

	public void setCityMaps(ArrayList<Map> cityMaps) {
		CityMaps = cityMaps;
	}
	
	

	
	
}
