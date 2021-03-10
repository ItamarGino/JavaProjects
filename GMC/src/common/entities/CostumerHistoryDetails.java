
package common.entities;

/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 * CostumerHistoryDetails is an entity for storing  the  Costumer History purchases 
 * We will use this entity to upload data to tables on the screens .
 */
public class CostumerHistoryDetails 
{
	public String userName;
	public String CityName;
	public String PurchaseType;
	public String PurchaseCurrentDate;
	public String PurchaseDateExpiration;
	public String numberOfVersion;
	
	public CostumerHistoryDetails(String userName, String CityName, String PurchaseType,
			String PurchaseCurrentDate, String PurchaseDateExpiration, String numberOfVersion)
	{
		this.userName = userName;
		this.CityName = CityName;
		this.PurchaseType = PurchaseType;
		this.PurchaseCurrentDate = PurchaseCurrentDate;
		this.PurchaseDateExpiration = PurchaseDateExpiration;
		this.numberOfVersion = numberOfVersion;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCityName() {
		return CityName;
	}
	public void setCityName(String cityName) {
		CityName = cityName;
	}
	public String getPurchaseType() {
		return PurchaseType;
	}
	public void setPurchaseType(String purchaseType) {
		PurchaseType = purchaseType;
	}
	public String getPurchaseCurrentDate() {
		return PurchaseCurrentDate;
	}
	public void setPurchaseCurrentDate(String purchaseCurrentDate) {
		PurchaseCurrentDate = purchaseCurrentDate;
	}
	public String getPurchaseDateExpiration() {
		return PurchaseDateExpiration;
	}
	public void setPurchaseDateExpiration(String purchaseDateExpiration) {
		PurchaseDateExpiration = purchaseDateExpiration;
	}

	public String getNumberOfVersion() {
		return numberOfVersion;
	}

	public void setNumberOfVersion(String numberOfVersion) {
		this.numberOfVersion = numberOfVersion;
	}
}