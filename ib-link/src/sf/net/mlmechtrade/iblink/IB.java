package sf.net.mlmechtrade.iblink;

import java.util.HashMap;

import com.ib.client.Contract;
import com.ib.client.Order;

public interface IB {
	public static final int DEFAULT_PORT = 7496;
	
	public abstract boolean isConnected();

	public abstract Integer placeOrder(Contract contract, Order order);

	public abstract void cancelOrderPermId(int permId);

	public abstract HashMap getOpenOrders();

	/**
	 * 
	 * @param string
	 *            host name. "" for localhost
	 * @param port
	 *            the server port to TWS
	 * @param clientID
	 *            IB TWS Client ID
	 */
	public abstract void connect(String string, int port, int clientId);

	public abstract void disconnect();

	public abstract IBOrderStatus getIBOrderStatusPermId(int permId);
}