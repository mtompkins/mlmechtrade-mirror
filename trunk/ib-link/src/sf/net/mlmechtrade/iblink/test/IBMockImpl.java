package sf.net.mlmechtrade.iblink.test;

import java.util.HashMap;

import com.ib.client.Contract;
import com.ib.client.Order;

import sf.net.mlmechtrade.iblink.IB;
import sf.net.mlmechtrade.iblink.IBOrderStatus;

public class IBMockImpl implements IB {

	public void cancelOrder(int id) {
		// TODO Auto-generated method stub

	}

	public IBOrderStatus getIBOrderStatus(int orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap getOpenOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	public IBOrderStatus getOrderStatus(int permId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer placeOrder(Contract contract, Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	public void cancelOrderPermId(int permId) {
		// TODO Auto-generated method stub
		
	}

	public void connect(String string, int port, int clientId) {
		// TODO Auto-generated method stub
		
	}

	public Integer getOrderId(int permId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	public IBOrderStatus getIBOrderStatusPermId(int permId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateOrderStatus() {
		// TODO Auto-generated method stub
		
	}

	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

}
