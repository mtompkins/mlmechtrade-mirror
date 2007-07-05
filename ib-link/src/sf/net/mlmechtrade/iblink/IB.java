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

public class IB implements EWrapper {

	protected final static Logger log = Logger.getLogger(IBClient.class);

	private EClientSocket m_client = new EClientSocket(this);

	private int id = 0;

	private HashMap<Integer, IBOrderStatus> orderStatus = new HashMap<Integer, IBOrderStatus>();

	public void connect(String ip, int port, int clientId) {
		m_client.eConnect(ip, port, clientId);
	}

	public void disconnect() {
		m_client.eDisconnect();
	}

	public synchronized void reqId() {
		id = 0;
		m_client.reqIds(1);
	}

	public int waitlId() {
		// request next unique id
		log.info("");

		// request next unique id
		int id = 0;
		reqId();

		// wait till we get new id.
		while (id == 0) {
			id = getId();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			if (id != 0) {
				break;
			}
			reqId();
		}
		return id;
	}

	public int getId() {
		int temp = id;
		id = 0;
		return temp;
	}

	public Integer placeOrder(Contract contract, Order order) {
		int id = waitlId();
		m_client.placeOrder(id, contract, order);
		reqOpenOrders();
		Integer permId = null;
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			reqOpenOrders();
			permId = getPermId(id);
			if (permId != null) {
				return permId;
			}
		}
		return null;
	}

	public void cancelOrder(int id) {
		m_client.cancelOrder(id);
	}

	public void reqOpenOrders() {
		// m_client.reqAllOpenOrders();
		m_client.reqOpenOrders();
	}

	public Integer getOrderId(int permId) {
		reqOpenOrders();
		Iterator<IBOrderStatus> it = orderStatus.values().iterator();
		synchronized (orderStatus) {
			IBOrderStatus value = it.next();
			if (value.getPermId() == permId) {
				return value.getOrderId();
			}
		}
		return null;
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

	public IBOrderStatus getOrderStatus(int permId) {
		reqOpenOrders();
		Integer id = null;
		for (int i = 0; i < 10; i++) {
			if (id != null) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
			}
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

	public void openOrder(int orderId, Contract contract, Order order) {
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
		log.info("IBOrderStatus=" + os);
	}

	public IBOrderStatus getIBOrderStatus(int orderId) {
		synchronized (orderStatus) {
			return (IBOrderStatus) orderStatus.get(new Integer(orderId));
		}
	}

	public HashMap getOpenOrders() {
		synchronized (orderStatus) {
			return (HashMap) orderStatus.clone();
		}
	}

	public void receiveFA(int faDataType, String xml) {
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
		// TODO Auto-generated method stub

	}

	public void error(Exception e) {
		// TODO Auto-generated method stub

	}

	public void error(String str) {
		// TODO Auto-generated method stub

	}

	public void error(int id, int errorCode, String errorMsg) {
		// TODO Auto-generated method stub

	}

}
