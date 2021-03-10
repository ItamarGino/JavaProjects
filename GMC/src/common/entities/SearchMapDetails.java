package common.entities;

/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 * SearchMapDetails is an entity for storing data and displays data on the screen when searching for a map.
 */

public class SearchMapDetails
{
	public String id;
	public String SITECOUNT;
	public String TOURCOUNT;

	public SearchMapDetails(String id, String SITECOUNT, String TOURCOUNT)
	{
		this.id = id;
		this.SITECOUNT = SITECOUNT;
		this.TOURCOUNT = TOURCOUNT;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSITECOUNT() {
		return SITECOUNT;
	}
	public void setSITECOUNT(String sITECOUNT) {
		SITECOUNT = sITECOUNT;
	}
	public String getTOURCOUNT() {
		return TOURCOUNT;
	}
	public void setTOURCOUNT(String tOURCOUNT) {
		TOURCOUNT = tOURCOUNT;
	}
}
