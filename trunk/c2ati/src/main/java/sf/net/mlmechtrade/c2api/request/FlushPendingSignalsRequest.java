package sf.net.mlmechtrade.c2api.request;

import sf.net.mlmechtrade.C2Error;
import sf.net.mlmechtrade.c2api.C2SignalEntryCommandEnum;

public class FlushPendingSignalsRequest extends TradeSignalRequest {

	public FlushPendingSignalsRequest() {
		super(C2SignalEntryCommandEnum.flushpendingsignals);
	}

	@Override
	public C2Error validate() {
		// TODO Auto-generated method stub
		return null;
	}

}
