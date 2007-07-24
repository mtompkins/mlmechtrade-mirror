package sf.net.mlmechtrade.iblink.test;

import java.util.Iterator;

import sf.net.mlmechtrade.iblink.IB;
import sf.net.mlmechtrade.iblink.IBImpl;
import sf.net.mlmechtrade.iblink.IBOrderStatus;

import com.ib.client.Contract;
import com.ib.client.Order;

public class IBTest {

	public static void main(String[] args) {
		IB ib = new IBImpl();

		ib.connect("", 7496, 1);

		// everything like in TWS
		Contract contract = new Contract();
		contract.m_symbol = "IBM";
		contract.m_secType = "STK";
		contract.m_exchange = "SMART";

		Order order = new Order();
		order.m_tif = "LMT";
		order.m_action = "BUY";
		order.m_totalQuantity = 1;
		order.m_orderType = "LMT";
		order.m_lmtPrice = 80.44;
		order.m_transmit = true;
		order.m_origin = 1;

		// place order. Look at tws for order to appear!
		Integer permId = ib.placeOrder(contract, order);
		System.out.println("PermId " + permId);
		permId = ib.placeOrder(contract, order);
		System.out.println("PermId " + permId);		

		printOrderState(ib, permId);

		ib.cancelOrderPermId(permId);

		printOrderState(ib, permId);

		printOrders(ib);

		dissconnect(ib);
	}

	private static void printOrderState(IB ib, int permId) {
		IBOrderStatus os = ib.getIBOrderStatusPermId(permId);
		System.out.println("Order status: " + os.getState() + " permID="
				+ os.getPermId());
	}

	private static void printOrders(IB ib) {
		Iterator orders = ib.getOpenOrders().values().iterator();

		while (orders.hasNext()) {
			IBOrderStatus os = (IBOrderStatus) orders.next();
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
