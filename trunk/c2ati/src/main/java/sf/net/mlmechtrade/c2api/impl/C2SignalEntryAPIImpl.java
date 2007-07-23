package sf.net.mlmechtrade.c2api.impl;

import org.apache.log4j.Logger;

import sf.net.mlmechtrade.C2Error;
import sf.net.mlmechtrade.c2api.C2SignalEntryAPI;
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

	public CancelAllPendingOrdersResponse cancelOrder(CancelAllPendingOrdersRequest request) throws C2Error {
		// TODO Auto-generated method stub
		return null;
	}

	public FlushPendingSignalsResponse flushPendingSignals(FlushPendingSignalsRequest request) throws C2Error {
		// TODO Auto-generated method stub
		return null;
	}

	public CancelAllPendingOrdersResponse cancelAllPendingOrders(CancelAllPendingOrdersRequest request) throws C2Error {
		// TODO Auto-generated method stub
		return null;
	}

	public CloseAllPositionsResponse closeAllPositions(CloseAllPositionsRequest request) throws C2Error {
		// TODO Auto-generated method stub
		return null;
	}

	public AllSystemsResponse getAllSystems(AllSystemsRequest request) throws C2Error {
		// TODO Auto-generated method stub
		return null;
	}

	public GetBuyPowerResponse getBuyPower(GetBuyPowerRequest request) throws C2Error {
		// TODO Auto-generated method stub
		return null;
	}

	public PositionStatusResponse getPosition(PositionStatusRequest request) throws C2Error {
		// TODO Auto-generated method stub
		return null;
	}

	public SignalStatusResponse getSignalStatus(SignalStatusRequest request) throws C2Error {
		// TODO Auto-generated method stub
		return null;
	}

	public GetSystemHypotheticalResponse getSystemHypothetical(GetSystemHypotheticalRequest request) throws C2Error {
		// TODO Auto-generated method stub
		return null;
	}
	
	//private String systemid; //?
	//private String password; //?

}
