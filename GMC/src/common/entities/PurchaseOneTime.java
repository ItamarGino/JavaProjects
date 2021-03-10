package common.entities;

public class PurchaseOneTime extends Purchase
{
	// downloadFlag => Client only available for one-time purchase
	private boolean downloadFlag;

	public boolean getDownloadFlag() {
		return downloadFlag;
	}

	public void setDownloadFlag(boolean downloadFlag) {
		this.downloadFlag = downloadFlag;
	}
}
