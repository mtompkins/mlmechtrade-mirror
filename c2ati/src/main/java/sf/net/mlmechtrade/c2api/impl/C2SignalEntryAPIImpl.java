package sf.net.mlmechtrade.c2api.impl;

import org.apache.log4j.Logger;

import sf.net.mlmechtrade.C2Error;
import sf.net.mlmechtrade.C2Request;
import sf.net.mlmechtrade.C2Response;
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
import sf.net.mlmechtrade.comm.C2Session;

public class C2SignalEntryAPIImpl implements C2SignalEntryAPI {

	private static Logger log = Logger.getLogger(C2SignalEntryAPIImpl.class);

	//private String systemid; //?
	//private String password; //?

	private C2Session clientSession;
	
	public C2SignalEntryAPIImpl() {
		
		clientSession = new C2Session();
		//TODO - configure clientSession
		
	}
	
	private <RESPONSE extends C2Response, REQUEST extends C2Request>
	RESPONSE invokeCommand(REQUEST request)throws C2Error {
		
		request.validate();
		
		throw new C2Error("NOT IMPLEMENTED");

		//return (RESPONSE)clientSession.invoke(request, reflectionalCommandParser);
		//TODO implement invokeCommand
		// v
		//chainedResponseHandler: XML, C2 error check, unpack response using reflection, validate response
		// then all implemmentation commands will be 
	}

	public PlaceOrderResponse placeOrder(PlaceOrderRequest request) throws C2Error {
		return invokeCommand(request);
	}

	public RequestOCAidResponse requestOCAid(RequestOCAidRequest request) throws C2Error {
		return invokeCommand(request);
	}

	public AllSystemsResponse retrieveAllSystems(AllSystemsRequest request) throws C2Error {
		return invokeCommand(request);
	}

	public ReversalOrderResponse reverseOrder(ReversalOrderRequest request) throws C2Error {
		return invokeCommand(request);
	}

	public CancelOrderResponse cancelOrder(CancelOrderRequest request) throws C2Error {
		return invokeCommand(request);
	}

	public CancelAllPendingOrdersResponse cancelOrder(CancelAllPendingOrdersRequest request) throws C2Error {
		return invokeCommand(request);
	}

	public FlushPendingSignalsResponse flushPendingSignals(FlushPendingSignalsRequest request) throws C2Error {
		return invokeCommand(request);
	}

	public CancelAllPendingOrdersResponse cancelAllPendingOrders(CancelAllPendingOrdersRequest request) throws C2Error {
		return invokeCommand(request);
	}

	public CloseAllPositionsResponse closeAllPositions(CloseAllPositionsRequest request) throws C2Error {
		return invokeCommand(request);
	}

	public AllSystemsResponse getAllSystems(AllSystemsRequest request) throws C2Error {
		return invokeCommand(request);
	}

	public GetBuyPowerResponse getBuyPower(GetBuyPowerRequest request) throws C2Error {
		return invokeCommand(request);
	}

	public PositionStatusResponse getPosition(PositionStatusRequest request) throws C2Error {
		return invokeCommand(request);
	}

	public SignalStatusResponse getSignalStatus(SignalStatusRequest request) throws C2Error {
		return invokeCommand(request);
	}

	public GetSystemHypotheticalResponse getSystemHypothetical(GetSystemHypotheticalRequest request) throws C2Error {
		return invokeCommand(request);
	}
	
}
