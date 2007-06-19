package sf.net.mlmechtrade.c2ati.domain;

public class FillAcknowledgment {
	private long sigId;

	private String permId;

	private long totalQuant;

	public String toString() {
		return "FillAcknowledgment: sigId=" + sigId + " permId=" + permId
				+ " totalQuant" + totalQuant;
	}

	public long getTotalQuant() {
		return totalQuant;
	}

	public void setTotalQuant(long totalQuant) {
		this.totalQuant = totalQuant;
	}

	public String getPermId() {
		return permId;
	}

	public void setPermId(String permId) {
		this.permId = permId;
	}

	public long getSigId() {
		return sigId;
	}

	public void setSigId(long sigId) {
		this.sigId = sigId;
	}
}
