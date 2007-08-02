package sf.net.mlmechtrade.iblink;

import java.util.Observable;

import com.ib.client.Contract;
import com.ib.client.Order;

public class IBOrderStatus extends Observable {
	private int orderId;

	private IBOrderState prevState;
	
	private IBOrderState state;

	private int filled;

	private int remaining;

	private double avgFillPrice;

	private int permId;

	private int parentId;

	private double lastFillPrice;

	private int clientId;

	private Contract conctract;

	private Order order;

	public String toString() {
		return "orderId=" + orderId + " state=" + state + " filled=" + filled
				+ " remaining=" + remaining + " avgFillPrice=" + avgFillPrice
				+ " permId=" + permId + " parentId=" + parentId
				+ " lastFillPrice=" + lastFillPrice + " clientId=" + clientId
				+ " conctract=" + conctract + " order=" + order;
	}
	
	public void copy(IBOrderStatus newState) {
		this.avgFillPrice = newState.avgFillPrice;
		this.clientId = newState.clientId;
		this.conctract = newState.conctract;
		this.filled = newState.filled;
		this.lastFillPrice = newState.lastFillPrice;
		this.order = newState.order;
		this.orderId = newState.orderId;
		this.parentId = newState.parentId;
		this.permId = newState.permId;
		this.prevState = this.state;
		this.state = newState.state;
	}

	public double getAvgFillPrice() {
		return avgFillPrice;
	}

	public void setAvgFillPrice(double avgFillPrice) {
		this.avgFillPrice = avgFillPrice;
		setChanged();
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
		setChanged();
	}

	public int getFilled() {
		return filled;
	}

	public void setFilled(int filled) {
		this.filled = filled;
		setChanged();
	}

	public double getLastFillPrice() {
		return lastFillPrice;
	}

	public void setLastFillPrice(double lastFillPrice) {
		this.lastFillPrice = lastFillPrice;
		setChanged();
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
		setChanged();
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
		setChanged();
	}

	public int getPermId() {
		return permId;
	}

	public void setPermId(int permId) {
		this.permId = permId;
		setChanged();
	}

	public int getRemaining() {
		return remaining;
	}

	public void setRemaining(int remaining) {
		this.remaining = remaining;
		setChanged();
	}

	public void setStatus(String status) {
		this.state = IBOrderState.valueOf(status);
	}

	public Contract getConctract() {
		return conctract;
	}

	public void setConctract(Contract conctract) {
		this.conctract = conctract;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public IBOrderState getState() {
		return state;
	}

	public IBOrderState getPrevState() {
		return prevState;
	}

	public void setPrevState(IBOrderState prevState) {
		this.prevState = prevState;
		setChanged();
		notifyObservers(prevState);
		//notifyObservers();
	}
}
