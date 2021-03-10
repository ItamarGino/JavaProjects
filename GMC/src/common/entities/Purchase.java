package common.entities;
/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 * Purchase is an entity for storing  the  Purchase deatils
 * We will use this entity Send data in the object to the server,To the reports And the history of Purchase.
 */
public abstract class Purchase 
{
	private String dateOfPurchase;
	private int quantity;
	
	public String getDateOfPurchase() {
		return dateOfPurchase;
	}
	public void setDateOfPurchase(String dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
