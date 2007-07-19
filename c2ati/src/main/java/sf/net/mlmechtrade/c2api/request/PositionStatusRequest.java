package sf.net.mlmechtrade.c2api.request;

import sf.net.mlmechtrade.C2Error;
import sf.net.mlmechtrade.c2api.C2SignalEntryCommandEnum;

public class PositionStatusRequest extends TradeSignalRequest {

	public PositionStatusRequest() {
		super(C2SignalEntryCommandEnum.positionstatus);
	}

	@Override
	public C2Error validate() {
		// TODO Auto-generated method stub
		return null;
	}

}
