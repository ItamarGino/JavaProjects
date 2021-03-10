package common.entities;

/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 * RequestDetails is an entity for storing data.
 *  We will use this entity to Send data in the object to the server for the Request of price approve. 

 */

public class RequestDetails
{
	public String mapID;
	public String employeeName;
	public String mapDetails;
	public String lastPrice;
	public String RequestPrice;

	public RequestDetails( String mapID,String employeeName, String mapDetails, String lastPrice, String requestPrice) {
		super();
		this.employeeName = employeeName;
		this.mapDetails = mapDetails;
		this.lastPrice = lastPrice;
		RequestPrice = requestPrice;
		this.mapID = mapID;
	}

	public String getMapID() {
		return mapID;
	}

	public void setMapID(String mapID) {
		this.mapID = mapID;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getMapDetails() {
		return mapDetails;
	}

	public void setMapDetails(String mapDetails) {
		this.mapDetails = mapDetails;
	}

	public String getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}

	public String getRequestPrice() {
		return RequestPrice;
	}

	public void setRequestPrice(String requestPrice) {
		RequestPrice = requestPrice;
	}
}
