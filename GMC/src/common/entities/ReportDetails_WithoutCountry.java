package common.entities;

/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 * ReportDetails_WithoutCountry is an entity for storing data.
 */

public class ReportDetails_WithoutCountry 
{
	public String city;
	public String MapQuantity;
	public String NumberOfOneTimePurchase;
	public String NumberOfSubscription;
	public String NumberOfRenewal;
	public String views;
	public String Download_Quantity;

	public ReportDetails_WithoutCountry(String city ,String MapQuantity ,String NumberOfOneTimePurchase,
			String NumberOfSubscription ,String NumberOfRenewal ,String views,
			String Download_Quantity)
	{
		this.setCity(city);
		this.setMapQuantity(MapQuantity);
		this.setNumberOfOneTimePurchase(NumberOfOneTimePurchase);
		this.setNumberOfSubscription(NumberOfSubscription);
		this.setNumberOfRenewal(NumberOfRenewal);
		this.setViews(views);
		this.setDownloadQuantity(Download_Quantity);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMapQuantity() {
		return MapQuantity;
	}

	public void setMapQuantity(String mapQuantity) {
		MapQuantity = mapQuantity;
	}

	public String getNumberOfOneTimePurchase() {
		return NumberOfOneTimePurchase;
	}

	public void setNumberOfOneTimePurchase(String numberOfOneTimePurchase) {
		NumberOfOneTimePurchase = numberOfOneTimePurchase;
	}

	public String getNumberOfSubscription() {
		return NumberOfSubscription;
	}

	public void setNumberOfSubscription(String numberOfSubscription) {
		NumberOfSubscription = numberOfSubscription;
	}

	public String getNumberOfRenewal() {
		return NumberOfRenewal;
	}

	public void setNumberOfRenewal(String numberOfRenewal) {
		NumberOfRenewal = numberOfRenewal;
	}

	public String getViews() {
		return views;
	}

	public void setViews(String views) {
		this.views = views;
	}

	public String getDownloadQuantity() {
		return Download_Quantity;
	}

	public void setDownloadQuantity(String Download_Quantity) {
		Download_Quantity = Download_Quantity;
	}
}
