package sf.net.mlmechtrade.c2api.request;

import sf.net.mlmechtrade.C2Error;
import sf.net.mlmechtrade.c2api.C2SignalEntryCommandEnum;

public class SignalStatusRequest extends TradeSignalRequest {

	public SignalStatusRequest() {
		super(C2SignalEntryCommandEnum.signalstatus);
	}

	@Override
	public C2Error validate() {
		// TODO Auto-generated method stub
		return null;
	}

}
