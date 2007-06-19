package sf.net.mlmechtrade.c2ati.domain;

public class FillConfirm {
	private long sigId;

	private String permId;

	private long quantity;

	private String brokerageExecutionId;

	private double fillPrice;

	public String getBrokerageExecutionId() {
		return brokerageExecutionId;
	}

	public void setBrokerageExecutionId(String brokerageExecutionId) {
		this.brokerageExecutionId = brokerageExecutionId;
	}

	public double getFillPrice() {
		return fillPrice;
	}

	public void setFillPrice(double fillPrice) {
		this.fillPrice = fillPrice;
	}

	public String getPermId() {
		return permId;
	}

	public void setPermId(String permId) {
		this.permId = permId;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public long getSigId() {
		return sigId;
	}

	public void setSigId(long sigId) {
		this.sigId = sigId;
	}
}
