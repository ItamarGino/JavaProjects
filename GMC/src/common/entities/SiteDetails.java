package common.entities;

import java.io.Serializable;
/**

* @author Shahar Ronen.
* @author Dorin Segall.
* @author Remez David.
* @author Itamar Gino.
* @author Amit Sinter.
* @version June 2019
* 
* Site Details Entity Temporarily saves SiteName and RecommendedVisitTime in tour
Data that exists in the Site and Site in tour table on the DB
We use it to upload data to tables and update them until the data is saved in DB.
*/

public class SiteDetails implements Serializable
{
	public String SiteName;
	public String RecommendedVisitTime;

	public SiteDetails(String SiteName, String RecommendedVisitTime) {
		super();
		this.SiteName = SiteName;
		this.RecommendedVisitTime = RecommendedVisitTime;
	}
	
	public SiteDetails() {
		// TODO Auto-generated constructor stub
	}

	public String getSiteName() {
		return SiteName;
	}
	public void setSiteName(String SiteName) {
		this.SiteName = SiteName;
	}
	public String getRecommendedVisitTime() {
		return RecommendedVisitTime;
	}
	public void setRecommendedVisitTime(String RecommendedVisitTime) {
		this.RecommendedVisitTime = RecommendedVisitTime;
	}

}
