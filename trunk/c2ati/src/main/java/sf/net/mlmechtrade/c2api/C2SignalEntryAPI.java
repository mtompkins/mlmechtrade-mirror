package sf.net.mlmechtrade.c2api;

import sf.net.mlmechtrade.C2Error;
import sf.net.mlmechtrade.c2api.request.AllSystemsRequest;
import sf.net.mlmechtrade.c2api.request.CancelAllPendingRequest;
import sf.net.mlmechtrade.c2api.request.CancelOrderRequest;
import sf.net.mlmechtrade.c2api.request.FlushPendingSignalsRequest;
import sf.net.mlmechtrade.c2api.request.PlaceOrderRequest;
import sf.net.mlmechtrade.c2api.request.RequestOCAidRequest;
import sf.net.mlmechtrade.c2api.request.ReversalOrderRequest;
import sf.net.mlmechtrade.c2api.response.AllSystemsResponse;
import sf.net.mlmechtrade.c2api.response.CancelAllPendingResponse;
import sf.net.mlmechtrade.c2api.response.CancelOrderResponse;
import sf.net.mlmechtrade.c2api.response.FlushPendingSignalsResponse;
import sf.net.mlmechtrade.c2api.response.PlaceOrderResponse;
import sf.net.mlmechtrade.c2api.response.RequestOCAidResponse;
import sf.net.mlmechtrade.c2api.response.ReversalOrderResponse;

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
	
	/** Place an order */
	PlaceOrderResponse placeOrder(PlaceOrderRequest request) throws C2Error;

	/** Cancel an order */
	CancelOrderResponse cancelOrder(CancelOrderRequest request) throws C2Error;

	/** Cancel all pending orders */
	CancelAllPendingResponse cancelOrder(CancelAllPendingRequest request) throws C2Error;

	/** Reverse Order */
	ReversalOrderResponse reverseOrder(ReversalOrderRequest request) throws C2Error;
	
	/** Request id for One-Cancels-Another (OCA) group */
	RequestOCAidResponse requestOCAid(RequestOCAidRequest request) throws C2Error;

	/** Request all systems */
	AllSystemsResponse retrieveAllSystems(AllSystemsRequest request) throws C2Error;
	
	/** Flush signals */
	FlushPendingSignalsResponse flushPendingSignals(FlushPendingSignalsRequest request) throws C2Error;

	//	/**  */
//	Response xxxx(Request request) throws C2Error;
//	/**  */
//	Response xxxx(Request request) throws C2Error;
//	/**  */
//	Response xxxx(Request request) throws C2Error;
//	/**  */
//	Response xxxx(Request request) throws C2Error;
//	/**  */
//	Response xxxx(Request request) throws C2Error;
//	/**  */
//	Response xxxx(Request request) throws C2Error;
//	/**  */
//	Response xxxx(Request request) throws C2Error;
//	/**  */
//	Response xxxx(Request request) throws C2Error;
//	/**  */
//	Response xxxx(Request request) throws C2Error;
//	/**  */
//	Response xxxx(Request request) throws C2Error;
//	/**  */
//	Response xxxx(Request request) throws C2Error;
}
