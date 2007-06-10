package sf.net.mlmechtrade.iblink.test;

import java.util.Iterator;

import sf.net.mlmechtrade.iblink.IB;
import sf.net.mlmechtrade.iblink.IBOrderStatus;

import com.ib.client.Contract;
import com.ib.client.Order;

public class IBTest {

	public static void main(String[] args) {
		IB ib = new IB();

		ib.connect("", 7496, 1);

		// request next unique id
		ib.reqId();

		int id = 0;

		// wait till we get new id.
		while (id == 0) {
			id = ib.getId();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}

		// everything like in TWS
		Contract contract = new Contract();

		contract.m_symbol = "IBM";
		contract.m_secType = "STK";
		contract.m_exchange = "SMART";

		Order order = new Order();

		order.m_action = "BUY";
		order.m_totalQuantity = 55;
		order.m_orderType = "LMT";
		order.m_lmtPrice = 80.44;
		order.m_transmit = true;
		order.m_origin = 1;

		// place order. Look at tws for order to appear!
		ib.placeOrder(id, contract, order);
		System.out.println("Order id " + id);

		IBOrderStatus os = null;

		// just checking order status
		while (os == null) {
			os = ib.getIBOrderStatus(id);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}

		System.out.println("Order status: " + os.status);

		// the same with another stock. Please note order type is MKT (market)
		// now.
		ib.reqId();

		id = 0;

		while (id == 0) {
			id = ib.getId();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}

		contract = new Contract();

		contract.m_symbol = "MSFT";
		contract.m_secType = "STK";
		contract.m_exchange = "SMART";

		order = new Order();

		order.m_action = "BUY";
		order.m_totalQuantity = 155;
		order.m_orderType = "MKT";
		order.m_transmit = true;
		order.m_origin = 1;

		ib.placeOrder(id, contract, order);
		System.out.println("Order id " + id);

		// Get orders we have just submitted to tws
		ib.reqOpenOrders();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		Iterator orders = ib.getOpenOrders().values().iterator();

		while (orders.hasNext()) {
			os = (IBOrderStatus) orders.next();
			System.out.println("Order id: " + os.orderId + " Order status: "
					+ os.status + " Filled " + os.filled + " Remaining "
					+ os.remaining + " Avg price " + os.avgFillPrice);
		}

		// Always sleep before closing othewise orders can be lost
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		ib.disconnect();
	}

}
