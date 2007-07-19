package sf.net.mlmechtrade.c2api.request;

import sf.net.mlmechtrade.C2Request;
import sf.net.mlmechtrade.c2api.C2SignalEntryCommandEnum;
import sf.net.mlmechtrade.c2api.C2SignalEntryCommandParamEnum;

public abstract class TradeSignalRequest
		extends C2Request<C2SignalEntryCommandEnum, C2SignalEntryCommandParamEnum> {
	
	public TradeSignalRequest(C2SignalEntryCommandEnum command) {
		super(command);
	}

	private int ocaid;

	public int getOcaid() {
		return ocaid;
	}

}
