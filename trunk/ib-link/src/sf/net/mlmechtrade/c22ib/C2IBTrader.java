package sf.net.mlmechtrade.c22ib;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.ib.client.Contract;
import com.ib.client.Order;

import sf.net.mlmechtrade.c2ati.C2ATI;
import sf.net.mlmechtrade.c2ati.C2ATIError;
import sf.net.mlmechtrade.c2ati.domain.ActionEnum;
import sf.net.mlmechtrade.c2ati.domain.AssetEnum;
import sf.net.mlmechtrade.c2ati.domain.DurationEnum;
import sf.net.mlmechtrade.c2ati.domain.OrderEnum;
import sf.net.mlmechtrade.c2ati.test.C2ATIMockImpl;
import sf.net.mlmechtrade.iblink.IB;
import sf.net.mlmechtrade.iblink.IBImpl;
import sf.net.mlmechtrade.iblink.test.IBMockImpl;

public class C2IBTrader {
	private long[] stratetiesToTrade;

	private C2ATI c2ati;

	private IB ib;

	/**
	 * The only possible constructor for automated trading interface between
	 * 
	 * @param c2ConfigPath
	 *            the property file for getting C2 account details.
	 * @param stratetiesToTrade
	 *            the list of StrategyIDs to trade see C2ATI documentation or C2
	 *            forums for descriptions of Strategy ID
	 * @throws IOException
	 *             Thrown in case of commication problems e.g. Lost network
	 *             connection
	 * @throws C2ATIError
	 *             C2ATI Error. Such as not authorized, no subscriptions etc.
	 *             see all Error causes {@link C2ATIError.ErrorCode}
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws XPathExpressionException
	 */
	public C2IBTrader(String c2ConfigPath, long[] stratetiesToTrade)
			throws IOException, XPathExpressionException,
			ParserConfigurationException, SAXException, C2ATIError {
		this.stratetiesToTrade = stratetiesToTrade;
		InputStream is = getClass().getResourceAsStream(c2ConfigPath);
		Properties prop = new Properties();
		prop.load(is);
		String eMail = prop.getProperty("eMail");
		String password = prop.getProperty("password");
		String host = prop.getProperty("host", "host");
		String isLifeAccountStr = prop.getProperty("liveType", "0");
		boolean isLifeAccount = isLifeAccountStr.equals("1");
		// C2ATI
		if (isLifeAccount) {
			c2ati = new C2ATI(eMail, password, isLifeAccount, host);
			ib = new IBImpl();
			ib.connect("", IB.DEFAULT_PORT, 1);
		} else {
			c2ati = new C2ATIMockImpl();
			ib = new IBMockImpl();
		}
		c2ati.login();
	}

	public void c2TradeCycle() {

	}

	public int placeOrder(ActionEnum action, OrderEnum orderType,
			DurationEnum tif, String symbol, String excahge,
			AssetEnum securityType, int quantity, double price) {

		// everything like in TWS
		Contract contract = new Contract();
		contract.m_symbol = symbol;

		contract.m_secType = getSecurityTypeMap2IB(securityType);
		contract.m_exchange = (excahge == null && contract.m_secType
				.equals("STK")) ? "SMART" : excahge;

		Order order = new Order();
		order.m_tif = tif.toString();
		order.m_action = getActionMap2IB(action);
		order.m_totalQuantity = quantity;
		order.m_orderType = getOrderType2IB(orderType);
		order.m_lmtPrice = price;
		order.m_transmit = true;
		order.m_origin = 1;

		// place order. Look at tws for order to appear!
		Integer permId = ib.placeOrder(contract, order);
		return permId;

	}

	private String getOrderType2IB(OrderEnum c2OrderType) {
		// All possible "LMT", "MKT", "STP", "STP LMT", "TRAIL", "MIT", "LIT",
		// "REL"
		if (c2OrderType == OrderEnum.LIMIT) {
			return "LMT";
		}
		if (c2OrderType == OrderEnum.MARKET) {
			return "MKT";
		}
		if (c2OrderType == OrderEnum.STOP) {
			return "STP";
		}
		return null;
	}

	private String getActionMap2IB(ActionEnum action) {
		if (action == ActionEnum.BTO || action == ActionEnum.BTC) {
			return "BUY";
		}
		return "SELL";
	}

	private String getSecurityTypeMap2IB(AssetEnum securityType) {
		if (securityType == AssetEnum.stock) {
			return "STK";
		}
		if (securityType == AssetEnum.futures) {
			return "FUT";
		}
		if (securityType == AssetEnum.options) {
			return "OPT";
		}
		if (securityType == AssetEnum.forex) {
			return "CASH";
		}
		return "STK";
	}
}