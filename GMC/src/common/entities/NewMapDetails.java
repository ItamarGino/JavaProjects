package common.entities;

import java.io.Serializable;
/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 * NewMapDetails is an entity for storing  the  New Map deatils
 * We will use this entity to Send data in the object to the server. .
 */
public class NewMapDetails implements Serializable
{
	public String CountryName;
	public String CityName;
	public String Discription;
	public String Image;

	public NewMapDetails(String CountryName, String CityName, String Discription, String Image)
	{
		this.CountryName = CountryName;
		this.CityName = CityName;
		this.Discription = Discription;
		this.Image = Image;
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
	public String getDiscription() {
		return Discription;
	}
	public void setDiscription(String discription) {
		Discription = discription;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
}
