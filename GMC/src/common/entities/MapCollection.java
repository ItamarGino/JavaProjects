package common.entities;

/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 * MapCollection is an entity for storing  the  Catalog version and if it approve ot not for Internal Use.
 */
public class MapCollection extends GenericEntity<Map>
{
	/* approveFlag => true only if approved by one of the managers	*/
	private int version;
	private boolean approveFlag;
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public boolean isApproveFlag() {
		return approveFlag;
	}
	public void setApproveFlag(boolean approveFlag) {
		this.approveFlag = approveFlag;
	}
	
}
