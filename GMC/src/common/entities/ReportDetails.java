package common.entities;

/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 * ReportDetails is an entity for storing data.
 * We will use this entity  to Send data in the object to the server for the reports. 

 */

public class ReportDetails extends ReportDetails_WithoutCountry
{
	public String CountryName;

	public ReportDetails(String CountryName ,String city ,String MapQuantity ,String NumberOfOneTimePurchase,
			String NumberOfSubscription ,String NumberOfRenewal ,String views,
			String Download_Quantity){
		super(city, MapQuantity, NumberOfOneTimePurchase, NumberOfSubscription, NumberOfRenewal, views, Download_Quantity);
		this.setCountryName(CountryName);
	}

	public String getCountryName() {
		return CountryName;
	}

	public void setCountryName(String countryName) {
		CountryName = countryName;
	}
}
