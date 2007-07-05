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

		// everything like in TWS
		Contract contract = new Contract();
		contract.m_symbol = "IBM";
		contract.m_secType = "STK";
		contract.m_exchange = "SMART";

		Order order = new Order();
		order.m_tif = "GTC";
		order.m_action = "BUY";
		order.m_totalQuantity = 1;
		order.m_orderType = "LMT";
		order.m_lmtPrice = 80.44;
		order.m_transmit = true;
		order.m_origin = 1;

		// place order. Look at tws for order to appear!
		Integer permId = ib.placeOrder(contract, order);
		System.out.println("PermId " + permId);

		printOrderState(ib, permId);

		// id = ib.waitlId();
		// contract = new Contract();
		//
		// contract.m_symbol = "MSFT";
		// contract.m_secType = "STK";
		// contract.m_exchange = "SMART";
		//
		// order = new Order();
		//
		// order.m_action = "BUY";
		// order.m_totalQuantity = 1;
		// order.m_orderType = "MKT";
		// order.m_transmit = true;
		// order.m_origin = 1;
		//
		// ib.placeOrder(id, contract, order);
		// System.out.println("Order id " + id);

		printOrders(ib);

		dissconnect(ib);
	}

	private static void printOrderState(IB ib, int permId) {
		Integer id = ib.getOrderId(permId);
		IBOrderStatus os = ib.getIBOrderStatus(id);

		System.out.println("Order status: " + os.getState() + " permID="
				+ os.getPermId());
	}

	private static void printOrders(IB ib) {
		IBOrderStatus os;
		// Get orders we have just submitted to tws
		ib.reqOpenOrders();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		Iterator orders = ib.getOpenOrders().values().iterator();

		while (orders.hasNext()) {
			os = (IBOrderStatus) orders.next();
			System.out.println("Order id: " + os.getOrderId()
					+ " Order status: " + os.getState() + " Filled "
					+ os.getFilled() + " Remaining " + os.getRemaining()
					+ " Avg price " + os.getAvgFillPrice() + " PermId="
					+ os.getPermId());
		}
	}

	private static void dissconnect(IB ib) {
		// Always sleep before closing othewise orders can be lost
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		ib.disconnect();
	}

}
