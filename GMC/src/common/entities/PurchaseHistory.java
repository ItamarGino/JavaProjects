
package common.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 * PurchaseHistory is an entity for storing  the Purchase History
 * We will use this entity Send data in the object to the server,To the reports And the history of Purchase.
 */
public class PurchaseHistory 
{
	//String PurchaseCurrentDate;
	private String username;
	public String cityName;
	public String purchaseType;
	public String PurchaseCurrentDate;
	private String download="no";
	

	public PurchaseHistory(ResultSet rs) throws SQLException {
		cityName=rs.getString("CityName");
		purchaseType=rs.getString("purchaseType");
		download=rs.getString("Download");
		PurchaseCurrentDate=rs.getString("PurchaseDateExpiration");
		username=rs.getString("userName");
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public PurchaseHistory(String cityName, String purchasetype,String PurchaseCurrentDate)
	{
	//	this.PurchaseCurrentDate=PurchaseCurrentDate;
		this.cityName=cityName;
		this.purchaseType=purchasetype;
		this.PurchaseCurrentDate=PurchaseCurrentDate;
	}

	public PurchaseHistory()
	{
		
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getPurchtype() {
		return purchaseType;
	}

	public void setPurchtype(String purchasetype) {
		this.purchaseType = purchasetype;
	}


	public String getPurchaseCurrentDate() {
		return PurchaseCurrentDate;
	}


	public void setPurchaseCurrentDate(String purchaseCurrentDate) {
		PurchaseCurrentDate = purchaseCurrentDate;
	}
	public String getPurchaseType() {
		return purchaseType;
	}
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
	public String getDownload() {
		return download;
	}
	public void setDownload(String download) {
		this.download = download;
	}

	
}