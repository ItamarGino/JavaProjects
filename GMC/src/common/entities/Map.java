package common.entities;

/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 * Map is an entity for storing  the  Map deatils
 * We will use this entity to upload data to tables on the screens and Send data in the object to the server. .
 */
public class Map 
{
	private String CityName;
	private String MapID;
	private String mapDescription;
	private double price;
	private static int mapWatchQuantity; // Per instance!
	private static int mapDownloadQuantity; // Per instance!
	
	public Map(String CityName,String MapID) {
		this.MapID=MapID;
		this.CityName=CityName;
	}
	public String getCityName() {
		return CityName;
	}
	public void setCityName(String cityName) {
		CityName = cityName;
	}
	@Override
	public boolean equals(Object obj)
	{
		Map other = (Map) obj;
		if(other.MapID.equals(this.MapID) == true)
			return true;
		return false;
	}
	
	public String getMapID() {
		return MapID;
	}
	public void setMapID(String mapID) {
		MapID = mapID;
	}
	public String getMapDescription() {
		return mapDescription;
	}
	public void setMapDescription(String mapDescription) {
		this.mapDescription = mapDescription;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public static int getMapWatchQuantity() {
		return mapWatchQuantity;
	}
	public static void setMapWatchQuantity(int mapWatchQuantity) {
		Map.mapWatchQuantity = mapWatchQuantity;
	}
	public static int getMapDownloadQuantity() {
		return mapDownloadQuantity;
	}
	public static void setMapDownloadQuantity(int mapDownloadQuantity) {
		Map.mapDownloadQuantity = mapDownloadQuantity;
	}
	
}
