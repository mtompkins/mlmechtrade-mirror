package sf.net.mlmechtrade.c2api.request;

import sf.net.mlmechtrade.C2Error;
import sf.net.mlmechtrade.c2api.C2SignalEntryCommandEnum;

public class GetSystemHypotheticalRequest extends TradeSignalRequest {

	public GetSystemHypotheticalRequest() {
		super(C2SignalEntryCommandEnum.getsystemhypothetical);
	}

	@Override
	public C2Error validate() {
		// TODO Auto-generated method stub
		return null;
	}

}
