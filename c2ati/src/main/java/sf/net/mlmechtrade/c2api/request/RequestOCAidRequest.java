package sf.net.mlmechtrade.c2api.request;

import sf.net.mlmechtrade.C2Error;
import sf.net.mlmechtrade.C2Request;
import sf.net.mlmechtrade.c2api.C2SignalEntryCommandEnum;
import sf.net.mlmechtrade.c2api.C2SignalEntryCommandParamEnum;
import static sf.net.mlmechtrade.c2api.C2SignalEntryCommandEnum.requestocaid;

public class RequestOCAidRequest
		extends C2Request<C2SignalEntryCommandEnum, C2SignalEntryCommandParamEnum> {

	public RequestOCAidRequest() {
		super(requestocaid);
	}

	@Override
	public C2Error validate() {
		// TODO Auto-generated method stub
		return null;
	}

}
