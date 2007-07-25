package sf.net.mlmechtrade.iblink;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.ib.client.EClientSocket;
import com.ib.client.EWrapper;
import com.ib.client.Execution;
import com.ib.client.Order;

public class IBImpl implements EWrapper, IB {

	protected final static Logger log = Logger.getLogger(IBClient.class);

	private EClientSocket m_client = new EClientSocket(this);

	private int id = 0;

	private String ip;

	private int port;

	private int clientId;

	private HashMap<Integer, IBOrderStatus> orderStatus = new HashMap<Integer, IBOrderStatus>();

	public void connect(String ip, int port, int clientId) {
		this.ip = ip;
		this.port = port;
		this.clientId = clientId;
		m_client.eConnect(ip, port, clientId);
	}

	public void disconnect() {
		sleep(2000);
		m_client.eDisconnect();
	}

	public synchronized void reqId() {
		log.debug("");
		m_client.reqIds(1);
	}

	public int waitlId() {
		// request next unique id
		log.info("");

		if (id == 0) {
			// wait till we get new id.
			for (int i = 0; i < 10; i++) {
				reqId();
				sleep(100);
				if (id != 0) {
					break;
				}
			}
		}
		return id++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sf.net.mlmechtrade.iblink.IB#placeOrder(com.ib.client.Contract,
	 *      com.ib.client.Order)
	 */
	public Integer placeOrder(Contract contract, Order order) {
		int id = waitlId();
		m_client.placeOrder(id, contract, order);
		Integer permId = null;
		for (int i = 0; i < 4; i++) {
			reqOpenOrders();
			permId = getPermId(id);
			if (permId != null) {
				return permId;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sf.net.mlmechtrade.iblink.IB#cancelOrder(int)
	 */
	public void cancelOrder(int id) {
		log.debug("Order ID=" + id);
		m_client.cancelOrder(id);
	}

	public void reqOpenOrders() {
		// m_client.reqAllOpenOrders();
		m_client.reqOpenOrders();
		sleep(1100);
	}

	public int getOrderId(int permId) {
		reqOpenOrders();
		for (int i = 0; i < 20; i++) {
			if (!isConnected()) {
				connect("", DEFAULT_PORT, 1);
				reqOpenOrders();
				sleep(1500);
			}
			Iterator<IBOrderStatus> it = orderStatus.values().iterator();
			synchronized (orderStatus) {
				while (it.hasNext()) {
					IBOrderStatus value = it.next();
					if (value.getPermId() == permId) {
						return value.getOrderId();
					}
				} // while
			}
		}
		throw new RuntimeException("Unable to get id");
	}

	private void sleep(int milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
		}
	}

	public Integer getPermId(int id) {
		Iterator<IBOrderStatus> it = orderStatus.values().iterator();
		synchronized (orderStatus) {
			while (it.hasNext()) {
				IBOrderStatus value = it.next();
				if (value.getOrderId() == id) {
					return value.getPermId();
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sf.net.mlmechtrade.iblink.IB#getOrderStatus(int)
	 */
	public IBOrderStatus getOrderStatus(int permId) {
		reqOpenOrders();
		Integer id = null;
		for (int i = 0; i < 10; i++) {
			id = getPermId(permId);
			if (id != null) {
				return orderStatus.get(id);
			}
		}
		return null;
	}

	public void bondContractDetails(ContractDetails contractDetails) {
		// TODO Auto-generated method stub

	}

	public void contractDetails(ContractDetails contractDetails) {
		// TODO Auto-generated method stub

	}

	public void execDetails(int orderId, Contract contract, Execution execution) {
		// TODO Auto-generated method stub

	}

	public void historicalData(int reqId, String date, double open,
			double high, double low, double close, int volume, int count,
			double WAP, boolean hasGaps) {
	}

	public void managedAccounts(String accountsList) {
		// TODO Auto-generated method stub
	}

	public void nextValidId(int numIds) {
		id = numIds;
	}

	public void orderStatus(int orderId, String status, int filled,
			int remaining, double avgFillPrice, int permId, int parentId,
			double lastFillPrice, int clientId) {
		IBOrderStatus os = new IBOrderStatus();
		os.setOrderId(orderId);
		os.setStatus(status);
		os.setFilled(filled);
		os.setRemaining(remaining);
		os.setAvgFillPrice(avgFillPrice);
		os.setPermId(permId);
		os.setParentId(parentId);
		os.setLastFillPrice(lastFillPrice);
		os.setAvgFillPrice(avgFillPrice);

		synchronized (orderStatus) {
			orderStatus.put(os.getOrderId(), os);
		}
		if (log.isDebugEnabled()) {
			log.debug("IBOrderStatus=" + os);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sf.net.mlmechtrade.iblink.IB#getIBOrderStatus(int)
	 */
	public IBOrderStatus getIBOrderStatus(int orderId) {
		synchronized (orderStatus) {
			return (IBOrderStatus) orderStatus.get(new Integer(orderId));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sf.net.mlmechtrade.iblink.IB#getOpenOrders()
	 */
	public HashMap getOpenOrders() {
		log.debug("");
		reqOpenOrders();
		synchronized (orderStatus) {
			return (HashMap) orderStatus.clone();
		}
	}

	public void receiveFA(int faDataType, String xml) {
		if (log.isDebugEnabled()) {
			log.debug("faDataType = " + faDataType + " xml=" + xml);
		}
		// TODO Auto-generated method stub

	}

	public void scannerData(int reqId, int rank,
			ContractDetails contractDetails, String distance, String benchmark,
			String projection) {
		// TODO Auto-generated method stub

	}

	public void scannerParameters(String xml) {
		// TODO Auto-generated method stub

	}

	public void tickGeneric(int tickerId, int tickType, double value) {
		// TODO Auto-generated method stub

	}

	public void tickOptionComputation(int tickerId, int field,
			double impliedVol, double delta, double modelPrice,
			double pvDividend) {
		// TODO Auto-generated method stub

	}

	public void tickPrice(int tickerId, int field, double price,
			int canAutoExecute) {
	}

	public void tickSize(int tickerId, int field, int size) {

	}

	public void tickString(int tickerId, int tickType, String value) {
		// TODO Auto-generated method stub

	}

	public void updateAccountTime(String timeStamp) {
		// TODO Auto-generated method stub

	}

	public void updateAccountValue(String key, String value, String currency,
			String accountName) {
		// TODO Auto-generated method stub

	}

	public void updateMktDepth(int tickerId, int position, int operation,
			int side, double price, int size) {
		// TODO Auto-generated method stub

	}

	public void updateMktDepthL2(int tickerId, int position,
			String marketMaker, int operation, int side, double price, int size) {
		// TODO Auto-generated method stub

	}

	public void updateNewsBulletin(int msgId, int msgType, String message,
			String origExchange) {
		// TODO Auto-generated method stub

	}

	public void updatePortfolio(Contract contract, int position,
			double marketPrice, double marketValue, double averageCost,
			double unrealizedPNL, double realizedPNL, String accountName) {
		// TODO Auto-generated method stub

	}

	public void connectionClosed() {
		log.info("Connection Closed");
		for (IBOrderStatus order : orderStatus.values()) {
			order.notifyObservers();
		}
	}

	public void error(Exception e) {
		log.error(e);
		// throw new RuntimeException(e);
	}

	public void error(String str) {
		log.error(str);
		// throw new RuntimeException(str);
	}

	public void error(int id, int errorCode, String errorMsg) {
		if (errorCode == ERR_NOT_CONNECTED) {
			log.info("Try recconnect!");
			sleep(1000);
			m_client.eConnect(ip, port, clientId);
		}
		String excString = "Order ID = " + id + " Error Code=" + errorCode
				+ " " + errorMsg;
		log.error(excString);
		// throw new RuntimeException(excString);
	}

	public void cancelOrderPermId(int permId) {
		int id = getOrderId(permId);
		cancelOrder(id);
	}

	public IBOrderStatus getIBOrderStatusPermId(int permId) {
		int id = getOrderId(permId);
		return orderStatus.get(id);
	}

	public void openOrder(int orderId, Contract contract, Order order) {
		if (log.isDebugEnabled()) {
			log.debug("orderId=" + orderId + " contract=" + contract
					+ " order=" + order);
		}
		reqOpenOrders();
	}

	public boolean isConnected() {
		return m_client.isConnected();
	}
}
