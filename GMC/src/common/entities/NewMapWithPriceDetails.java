package common.entities;
/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 * NewMapWithPriceDetails is an entity for storing  the  New Map deatils
 * We will use this entity to Send data in the object to the server. .
 */
public class NewMapWithPriceDetails extends NewMapDetails
{
	public String price;
	
	public NewMapWithPriceDetails(String CountryName, String CityName, String Discription, String Image, String price) 
	{
		super(CountryName, CityName, Discription, Image);
		this.price = price;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
}
