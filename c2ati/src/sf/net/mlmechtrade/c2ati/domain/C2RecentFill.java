package sf.net.mlmechtrade.c2ati.domain;

public class C2RecentFill {
	private long signalId;

	private long filledAgo;

	private double filledPrice;

	private String permId;

	public String toString() {
		return "Recent C2 Fill: signalId=" + signalId + " permId=" + permId
				+ " filledAgo=" + filledAgo + " filledPrice" + filledPrice;
	}

	public long getFilledAgo() {
		return filledAgo;
	}

	public void setFilledAgo(long filledAgo) {
		this.filledAgo = filledAgo;
	}

	public double getFilledPrice() {
		return filledPrice;
	}

	public void setFilledPrice(double filledPrice) {
		this.filledPrice = filledPrice;
	}

	public long getSignalId() {
		return signalId;
	}

	public void setSignalId(long signalId) {
		this.signalId = signalId;
	}

	public String getPermId() {
		return permId;
	}

	public void setPermId(String permId) {
		this.permId = permId;
	}
}
