package sf.net.mlmechtrade.c2api;

import sf.net.mlmechtrade.C2Error;
import sf.net.mlmechtrade.c2api.request.AllSystemsRequest;
import sf.net.mlmechtrade.c2api.request.CancelAllPendingOrdersRequest;
import sf.net.mlmechtrade.c2api.request.CancelOrderRequest;
import sf.net.mlmechtrade.c2api.request.CloseAllPositionsRequest;
import sf.net.mlmechtrade.c2api.request.FlushPendingSignalsRequest;
import sf.net.mlmechtrade.c2api.request.GetBuyPowerRequest;
import sf.net.mlmechtrade.c2api.request.GetSystemHypotheticalRequest;
import sf.net.mlmechtrade.c2api.request.PlaceOrderRequest;
import sf.net.mlmechtrade.c2api.request.PositionStatusRequest;
import sf.net.mlmechtrade.c2api.request.RequestOCAidRequest;
import sf.net.mlmechtrade.c2api.request.ReversalOrderRequest;
import sf.net.mlmechtrade.c2api.request.SignalStatusRequest;
import sf.net.mlmechtrade.c2api.response.AllSystemsResponse;
import sf.net.mlmechtrade.c2api.response.CancelAllPendingOrdersResponse;
import sf.net.mlmechtrade.c2api.response.CancelOrderResponse;
import sf.net.mlmechtrade.c2api.response.CloseAllPositionsResponse;
import sf.net.mlmechtrade.c2api.response.FlushPendingSignalsResponse;
import sf.net.mlmechtrade.c2api.response.GetBuyPowerResponse;
import sf.net.mlmechtrade.c2api.response.GetSystemHypotheticalResponse;
import sf.net.mlmechtrade.c2api.response.PlaceOrderResponse;
import sf.net.mlmechtrade.c2api.response.PositionStatusResponse;
import sf.net.mlmechtrade.c2api.response.RequestOCAidResponse;
import sf.net.mlmechtrade.c2api.response.ReversalOrderResponse;
import sf.net.mlmechtrade.c2api.response.SignalStatusResponse;

/**
 * <h1>C2 Signal Entry API</h1>
 * 
 * <p>Overview
 * <p>Many traders use computer programs to place trades.
 * To accommodate these traders,  Collective2 makes available a Signal Entry API.
 * All calls to this API are accomplished through HTTP,
 *  by having your program call a URL.
 * <p>
 * 
 * Before you begin, you'll need to have created at least one trading system on 
 * Collective2. You'll need also to know the system id of the trading system for 
 * which you want to place a trade. (Every system on Collective2 has an unique id number 
 * associated with it.)
 * <p>The strategy for using the Web interface is as follows. You will code a web 
 * address (URL) which contains all the information necessary to place a trade. 
 * Unless you specify otherwise, the trade will become valid immediately, 
 * and also will be emailed (or Instant Trade Messengered) to subscribers immediately.
 * 
 * <p>The URL is in the following format:
 * <pre>http://www.collective2. com/cgi-perl/signal. mpl?[PARAMETERS]</pre>
 *
 * See <a href="http://www.collective2.com/???">http://www.collective2.com/???</a> 
 *
 */
public interface C2SignalEntryAPI {
	
	/** <h1>Place an order</h1>
	 */
	PlaceOrderResponse placeOrder(PlaceOrderRequest request) throws C2Error;

	/** <h1>Cancel an order</h1>
	 */
	CancelOrderResponse cancelOrder(CancelOrderRequest request) throws C2Error;

	/** <h1>Cancel all pending orders</h1>
	 */
	CancelAllPendingOrdersResponse cancelAllPendingOrders(CancelAllPendingOrdersRequest request) throws C2Error;

	/** <h1>Reverse Order</h1>
	 */
	ReversalOrderResponse reverseOrder(ReversalOrderRequest request) throws C2Error;
	
	/** <h1>Request id for One-Cancels-Another (OCA) group</h1>
	 */
	RequestOCAidResponse requestOCAid(RequestOCAidRequest request) throws C2Error;

	/** <h1>Request all systems</h1>
	 */
	AllSystemsResponse retrieveAllSystems(AllSystemsRequest request) throws C2Error;
	
	/** <h1>Flush signals</h1>
	 */
	FlushPendingSignalsResponse flushPendingSignals(FlushPendingSignalsRequest request) throws C2Error;

	/**
	 * <h1>Close all positions</h1>
	 *
	 * You can instruct the system to close all open positions. This will issue
	 * a set of closing orders, at market, for any position you have open. You
	 * might want to issue this at the end of the trading day, to make sure you
	 * are flat before the markets close.
	 * <p>
	 * If you have no positions open, the command will be ignored.
	 */
	CloseAllPositionsResponse closeAllPositions(CloseAllPositionsRequest request)
			throws C2Error;

	/**<h1>Request Buying Power</h1>
	 * 
	 */
	GetBuyPowerResponse getBuyPower(GetBuyPowerRequest request) throws C2Error;

	/**<h1>Request signal status</h1>
	 * 
	 */
	SignalStatusResponse getSignalStatus(SignalStatusRequest request) throws C2Error;

	/**<h1>Request position status</h1>
	 * 
	 */
	PositionStatusResponse getPosition(PositionStatusRequest request) throws C2Error;
	
	/**
	 * <h1>Request all systems owned by me</h1>
	 * The command allsystems returns a list of the trading systems "owned" by a
	 * particular C2 user.
	 * <p>
	 * Please note that the list returns only the trading systems started by the
	 * customer -- not the systems subscribed to by the customer.
	 */
	AllSystemsResponse getAllSystems(AllSystemsRequest request) throws C2Error;

	
	/**
	 * <h1>Get equity info</h1>
	 * The command called getsystemhypothetical (getsystemhypo also accepted)
	 * allows you to request equity information about trading systems on
	 * Collective2.
	 * <p>
	 * <b>Important:</b> the data returned reveals Collective2’s hypothetical
	 * system account information. The data does not include any
	 * customer/brokerage real-life account data.
	 */
	GetSystemHypotheticalResponse getSystemHypothetical(
			GetSystemHypotheticalRequest request) throws C2Error;	
	
	//TODO : check missing commands, especially derivates of a very owerloaded signal command
	
	
	//	/**<h1></h1>
//	 * 
//	 */
//	Response xxxx(Request request) throws C2Error;
}
