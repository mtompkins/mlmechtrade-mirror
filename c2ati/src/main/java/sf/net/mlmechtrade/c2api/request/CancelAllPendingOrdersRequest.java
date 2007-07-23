package sf.net.mlmechtrade.c2api.request;

import sf.net.mlmechtrade.C2Error;
import sf.net.mlmechtrade.c2api.C2SignalEntryCommandEnum;

/**
 * You can issue a blanket cancellation of all pending orders for a
 *  trading system like this:<pre>
http://www.collective2 .com/cgi-perl/signal. mpl
?cmd=cancelallpending&signalid=XXX&systemid=123&pw=abcd
</pre>
 *
 */
public class CancelAllPendingOrdersRequest extends TradeSignalRequest {
	
	private Integer signalid;
	private Integer systemid; //TODO - Is systemid common??
	private String pw; //TODO - Is pw common??

	public CancelAllPendingOrdersRequest() {
		super(C2SignalEntryCommandEnum.cancelallpending);
	}

	@Override
	public C2Error validate() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public Integer getSignalid() {
		return signalid;
	}

	public void setSignalid(Integer signalid) {
		this.signalid = signalid;
	}

	public Integer getSystemid() {
		return systemid;
	}

	public void setSystemid(Integer systemid) {
		this.systemid = systemid;
	}

}
