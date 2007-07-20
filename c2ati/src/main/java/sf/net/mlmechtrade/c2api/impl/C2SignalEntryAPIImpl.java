package sf.net.mlmechtrade.c2api.impl;

import org.apache.log4j.Logger;

import sf.net.mlmechtrade.C2Error;
import sf.net.mlmechtrade.c2api.C2SignalEntryAPI;
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

public class C2SignalEntryAPIImpl implements C2SignalEntryAPI {

	private static Logger log = Logger.getLogger(C2SignalEntryAPIImpl.class);

	public PlaceOrderResponse placeOrder(PlaceOrderRequest request) throws C2Error {
		return null;
	}

	public RequestOCAidResponse requestOCAid(RequestOCAidRequest request) throws C2Error {
		// TODO Auto-generated method stub
		return null;
	}

	public AllSystemsResponse retrieveAllSystems(AllSystemsRequest request) throws C2Error {
		// TODO Auto-generated method stub
		return null;
	}

	public ReversalOrderResponse reverseOrder(ReversalOrderRequest request) throws C2Error {
		// TODO Auto-generated method stub
		return null;
	}

	public CancelOrderResponse cancelOrder(CancelOrderRequest request) throws C2Error {
		// TODO Auto-generated method stub
		return null;
	}

	public CancelAllPendingResponse cancelOrder(CancelAllPendingRequest request) throws C2Error {
		// TODO Auto-generated method stub
		return null;
	}

	public FlushPendingSignalsResponse flushPendingSignals(FlushPendingSignalsRequest request) throws C2Error {
		// TODO Auto-generated method stub
		return null;
	}
	
	//private String systemid; //?
	//private String password; //?

}
