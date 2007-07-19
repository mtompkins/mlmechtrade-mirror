package sf.net.mlmechtrade.c2api.request;

import sf.net.mlmechtrade.C2Error;
import sf.net.mlmechtrade.c2api.C2SignalEntryCommandEnum;

public class GetBuyPowerRequest extends TradeSignalRequest {

	public GetBuyPowerRequest() {
		super(C2SignalEntryCommandEnum.getbuypower);
	}

	@Override
	public C2Error validate() {
		// TODO Auto-generated method stub
		return null;
	}

}
