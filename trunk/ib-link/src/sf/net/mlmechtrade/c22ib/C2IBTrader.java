package sf.net.mlmechtrade.c22ib;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.ib.client.Contract;
import com.ib.client.Order;

import sf.net.mlmechtrade.c2ati.C2ATI;
import sf.net.mlmechtrade.c2ati.C2ATIAPI;
import sf.net.mlmechtrade.c2ati.C2ATIError;
import sf.net.mlmechtrade.c2ati.C2ATIMockImpl;
import sf.net.mlmechtrade.c2ati.domain.ActionEnum;
import sf.net.mlmechtrade.c2ati.domain.AssetEnum;
import sf.net.mlmechtrade.c2ati.domain.DurationEnum;
import sf.net.mlmechtrade.c2ati.domain.FillConfirm;
import sf.net.mlmechtrade.c2ati.domain.LatestSignals;
import sf.net.mlmechtrade.c2ati.domain.MultFillConfirmEnum;
import sf.net.mlmechtrade.c2ati.domain.OrderEnum;
import sf.net.mlmechtrade.c2ati.domain.Signal;
import sf.net.mlmechtrade.iblink.IB;
import sf.net.mlmechtrade.iblink.IBImpl;
import sf.net.mlmechtrade.iblink.IBOrderState;
import sf.net.mlmechtrade.iblink.IBOrderStatus;
import sf.net.mlmechtrade.iblink.test.IBMockImpl;

public class C2IBTrader {
	Logger log = Logger.getLogger(getClass());

	private long[] stratetiesToTrade;

	private C2ATIAPI c2ati;

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
	public C2IBTrader(String c2ConfigPath) throws IOException,
			XPathExpressionException, ParserConfigurationException,
			SAXException, C2ATIError {

		InputStream is = getClass().getClassLoader().getResourceAsStream(
				c2ConfigPath);
		Properties prop = new Properties();
		if (is != null) {
			prop.load(is);
		}
		String eMail = prop.getProperty("eMail", "email@company.com");
		String password = prop.getProperty("password", "password");
		String host = prop.getProperty("host", "host");
		String isLifeAccountStr = prop.getProperty("liveType", "0");
		boolean isLifeAccount = isLifeAccountStr.equals("1");
		// IB Life?
		if (isLifeAccount) {
			ib = new IBImpl();
			ib.connect("", IB.DEFAULT_PORT, 1);
		} else {
			ib = new IBMockImpl();
		}
		// C2ATI Life?
		String isLifeSignalsStr = prop.getProperty("liveSignals", "0");
		boolean isLifeSignals = isLifeSignalsStr.equals("1");
		if (isLifeSignals) {
			c2ati = new C2ATI(eMail, password, isLifeAccount, host);
		} else {
			c2ati = new C2ATIMockImpl();
		}
		// Login
		c2ati.login();
	}

	public void c2TradeCycle() throws HttpException, XPathExpressionException,
			IOException, ParserConfigurationException, SAXException, C2ATIError {
		LatestSignals signals = c2ati.latestSignals();
		// Cancel Signal List
		processCancelSigList(signals);
		// New Ordes
		processNewOrders(signals);
		// Complete Trades e.g. CLOSE Existing positions
		List<String> startegyCompletedSignals = signals
				.getCompletedTradesSigPerId();
		// TODO!
	}

	private void processNewOrders(LatestSignals signals) throws HttpException,
			IOException, ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError {
		List<Signal> tradeSignals = signals.getSignals();
		for (Signal signal : tradeSignals) {
			ActionEnum action = signal.getAction();
			OrderEnum orderType = signal.getOrderType();
			DurationEnum tif = signal.getTif();
			String symbol = signal.getSymbol();
			String excahge = signal.getExchange();
			AssetEnum securityType = signal.getAssetType();
			int quantity = signal.getScaledQuant();
			double stopPrice = signal.getStop();
			double limitPrice = signal.getLimit();

			// TODO: PermID may be null but order already filled!!!
			Observer observer = new Observer() {
				public void update(Observable o, Object arg) {
					System.out.println("Observable=" + o + " arg=" + arg);
				}
			};
			Integer permId = placeOrder(action, orderType, tif, symbol,
					excahge, securityType, quantity, stopPrice, limitPrice,
					observer);
			if (permId == null) {
				// Unable to retrieve PermID retry next time!
				ib.cancelOrder(ib.getLastOrderId());
			} else {
				c2ati.confirmSig(quantity, signal.getSignalid(), permId
						.toString());
			}
		}
	}

	private void processCancelSigList(LatestSignals signals)
			throws HttpException, IOException, ParserConfigurationException,
			SAXException, XPathExpressionException, C2ATIError {
		List<String> cancelList = signals.getCancelListPermIds();
		// Cancel
		for (String permId : cancelList) {
			// Asumptions CancelSig is stable...
			try {
				int id = Integer.parseInt(permId);
				ib.cancelOrderPermId(id);
				c2ati.cancelConfirmPermId(permId);
			} catch (NumberFormatException e) {
				log.error("None IB (not integer) passed", e);
			} catch (RuntimeException e) {
				log.error("Unable to cancel permId=" + permId, e);
			}
		}
	}

	public Integer placeOrder(ActionEnum action, OrderEnum orderType,
			DurationEnum tif, String symbol, String excahge,
			AssetEnum securityType, int quantity, double stopPrice,
			double limitPrice, Observer observer) {

		// everything like in TWS
		Contract contract = new Contract();
		contract.m_symbol = symbol;

		contract.m_secType = getSecurityTypeMap2IB(securityType);

		// Use SMART for stocs if not specified. TODO: Generalise if possible
		if (excahge == null || excahge.equals("")) {
			excahge = contract.m_secType.equals("STK") ? "SMART" : excahge;
		}
		contract.m_exchange = excahge;

		Order order = new Order();
		order.m_allOrNone = true;
		order.m_tif = tif.toString();
		order.m_action = getActionMap2IB(action);
		order.m_totalQuantity = quantity;
		order.m_orderType = getOrderType2IB(orderType);
		order.m_auxPrice = stopPrice;
		order.m_lmtPrice = limitPrice;
		order.m_transmit = true;
		order.m_origin = 1;

		// place order. Look at tws for order to appear!
		Integer permId = ib.placeOrder(contract, order, observer);
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

	private class IBOrderStateManager implements Observer {
		private IB ib;

		private C2ATIAPI c2ati;

		public IBOrderStateManager(IB ib, C2ATIAPI c2ati) {
			this.ib = ib;
			this.c2ati = c2ati;
		}

		public void update(Observable o, Object arg) {
			if (o instanceof IBOrderStatus) {
				// Current order status
				IBOrderStatus orderStatus = (IBOrderStatus) o;
				// Previuse State
				IBOrderState prevState = (IBOrderState) arg;
				// Fill?
				confirmFill(orderStatus, prevState);
				// Confirm 
			}
		}

		private void confirmFill(IBOrderStatus orderStatus, IBOrderState prevState) {
			// Confirm Fill
			if ((prevState == IBOrderState.PreSubmitted
					|| prevState == IBOrderState.Submitted || prevState == IBOrderState.PendingSubmit)
					&& orderStatus.getState() == IBOrderState.Filled) {
				FillConfirm confirm = new FillConfirm();
				List<FillConfirm> list = new ArrayList<FillConfirm>();
				list.add(confirm);
				// Fill Confirm structure
				confirm.setBrokerageExecutionId(Integer
						.toString(orderStatus.getOrderId()));
				confirm.setFillPrice(orderStatus.getAvgFillPrice());
				confirm
						.setPermId(Integer
								.toString(orderStatus.getPermId()));
				confirm.setQuantity(orderStatus.getFilled());
				try {
					c2ati.multFillConfirm(list,
							MultFillConfirmEnum.mult2fillconfirm);
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XPathExpressionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (C2ATIError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}