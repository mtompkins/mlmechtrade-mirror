package sf.net.mlmechtrade.iblink;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.ib.client.EClientSocket;
import com.ib.client.EWrapper;
import com.ib.client.Execution;
import com.ib.client.Order;

public class IBClient implements EWrapper {

	protected final static Logger log = Logger.getLogger(IBClient.class);

	private EClientSocket mClientSocket = new EClientSocket(this);

	private int id = 0;

	private Map<Integer, IBOrderStatus> orderStatus = Collections
			.synchronizedMap(new HashMap<Integer, IBOrderStatus>());

	private Queue<Order> orderQueue = new LinkedList<Order>();

	public void connect(String ip, int port, int clientId) {
		log.info("ip=" + ip + " port=" + port + " clientId=" + clientId);
		mClientSocket.eConnect(ip, port, clientId);
	}

	public void disconnect() {
		log.info("");
		mClientSocket.eDisconnect();
	}

	private synchronized void reqId() {
		log.info("");
		id = 0;
		mClientSocket.reqIds(1);
	}

	public int waitlId() {
		// request next unique id
		log.info("");

		// request next unique id
		int id = 0;
		reqId();

		// wait till we get new id.
		while (id == 0) {
			reqId();
			id = getId();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
		return id;
	}

	public IBOrderStatus waitOrderStatus(int orderId) {
		log.info("orderId=" + orderId);
		IBOrderStatus os = null;
		while (os == null) {
			os = getIBOrderStatus(orderId);
			// Sleep after invocation
			if (os != null) {
				break;
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
		log.info("return OrcerStatus=" + os);
		return os;
	}

	public int getId() {
		log.info("id=" + id);
		int temp = id;
		id = 0;
		return temp;
	}

	public void placeOrder(int id, Contract contract, Order order) {
		log.info("id=" + id + " contract=" + contract + " order=" + order);
		mClientSocket.placeOrder(id, contract, order);
	}

	public void cancelOrder(int id) {
		log.info("id=" + id);
		mClientSocket.cancelOrder(id);
	}

	public void reqOpenOrders() {
		log.info("");
		mClientSocket.reqOpenOrders();
	}

	public void reqAllOpenOrders() {
		log.info("");
		mClientSocket.reqAllOpenOrders();
	}

	public void reqContractDetails(Contract contract) {
		log.info("contract=" + contract);
		mClientSocket.reqContractDetails(contract);
	}

	public void bondContractDetails(ContractDetails contractDetails) {
		log.info("contractDetails=" + contractDetails);
		// TODO Auto-generated method stub

	}

	public void contractDetails(ContractDetails contractDetails) {
		log.info("contractDetails=" + contractDetails);
		// TODO Auto-generated method stub

	}

	public void execDetails(int orderId, Contract contract, Execution execution) {
		log.info("orderId=" + orderId + " contract=" + contract + " execution="
				+ execution);
		// TODO Auto-generated method stub

	}

	public void historicalData(int reqId, String date, double open,
			double high, double low, double close, int volume, int count,
			double WAP, boolean hasGaps) {
		log.info("reqId=" + reqId + " date=" + date + " open=" + open
				+ " high=" + high + " low=" + low + " close=" + close
				+ " volume=" + volume + " count=count");
	}

	public void managedAccounts(String accountsList) {
		log.info("accountsList=" + accountsList);
		// TODO Auto-generated method stub
	}

	public void nextValidId(int numIds) {
		id = numIds;
		log.info("numIds=" + numIds);
	}

	public void openOrder(int orderId, Contract contract, Order order) {
		synchronized (orderStatus) {
			IBOrderStatus status = orderStatus.get(orderId);
			if (status == null) {
				status = new IBOrderStatus();
				// status.setClientId(clientId);
				// TODO: FILL IBOrder details !!!
				// intentionally left
			}
			status.setConctract(contract);
			status.setOrder(order);
		}
		log.info("orderId=" + orderId + " contract=" + contract + " order="
				+ order);
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
		return orderStatus.get(orderId);
	}

	public Map getOpenOrders() {
		return orderStatus;
	}

	public void receiveFA(int faDataType, String xml) {
		log.info("faDataType=" + faDataType + " xml=" + xml);
		// TODO Auto-generated method stub

	}

	public void scannerData(int reqId, int rank,
			ContractDetails contractDetails, String distance, String benchmark,
			String projection) {
		log.info("reqId=" + reqId + " rank=" + rank + " contractDetails="
				+ contractDetails + " distance=" + distance + "benchmark="
				+ benchmark + " projection=" + projection);
		// TODO Auto-generated method stub

	}

	public void scannerParameters(String xml) {
		log.info(xml);
		// TODO Auto-generated method stub

	}

	public void tickGeneric(int tickerId, int tickType, double value) {
		log.info("tickerId=" + tickerId + " tickType=" + tickType + " value="
				+ value);
		// TODO Auto-generated method stub

	}

	public void tickOptionComputation(int tickerId, int field,
			double impliedVol, double delta, double modelPrice,
			double pvDividend) {
		log.info("tickerId=" + tickerId + " field=" + field + " impliedVol="
				+ impliedVol + " delta=" + delta + " modelPrice=" + modelPrice
				+ " pvDividend=" + pvDividend);
		// TODO Auto-generated method stub

	}

	public void tickPrice(int tickerId, int field, double price,
			int canAutoExecute) {
	}

	public void tickSize(int tickerId, int field, int size) {
		log.info("tickerId=" + tickerId + " field=" + field + " size=" + size);
	}

	public void tickString(int tickerId, int tickType, String value) {
		log.info("tickerId = " + tickerId + " tickType=" + tickType + " value="
				+ value);
		// TODO Auto-generated method stub

	}

	public void updateAccountTime(String timeStamp) {
		log.info("timeStamp=" + timeStamp);
		// TODO Auto-generated method stub

	}

	public void updateAccountValue(String key, String value, String currency,
			String accountName) {
		log.info("key=" + key + " value=" + value + " currency=" + currency
				+ " accountName=" + accountName);
		// TODO Auto-generated method stub

	}

	public void updateMktDepth(int tickerId, int position, int operation,
			int side, double price, int size) {
		log.info("tickerId=" + tickerId + " position=" + position
				+ " operation" + operation + " side=" + side + " price="
				+ price + " size=" + size);
		// TODO Auto-generated method stub

	}

	public void updateMktDepthL2(int tickerId, int position,
			String marketMaker, int operation, int side, double price, int size) {
		log.info("tickerId=" + tickerId + " position=" + position
				+ " marketMaker=" + marketMaker + " operation=" + operation
				+ " side=" + side + " price=" + price + " size=" + size);
		// TODO Auto-generated method stub

	}

	public void updateNewsBulletin(int msgId, int msgType, String message,
			String origExchange) {
		log.info("msgId=" + msgId + " msgType=" + msgType + " message="
				+ message);

	}

	public void updatePortfolio(Contract contract, int position,
			double marketPrice, double marketValue, double averageCost,
			double unrealizedPNL, double realizedPNL, String accountName) {
		// TODO Auto-generated method stub
		log.info("contract=" + contract + " position=" + position
				+ " marketPrice=" + marketPrice + " marketValue=" + marketValue
				+ " averageCost=" + averageCost + " unrealizedPNL="
				+ unrealizedPNL + " realizedPNL=" + realizedPNL
				+ " accountName=" + accountName);

	}

	public void connectionClosed() {
		log.info("");
		// TODO Auto-generated method stub

	}

	public void error(Exception e) {
		log.error(e);
	}

	public void error(String str) {
		log.error(str);
	}

	public void error(int id, int errorCode, String errorMsg) {
		log.error("id=" + id + " errorCode=" + errorCode + " errorMsg="
				+ errorMsg);
	}

}
