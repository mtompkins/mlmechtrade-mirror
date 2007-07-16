package sf.net.mlmechtrade.c2api.request;

import sf.net.mlmechtrade.C2Request;
import sf.net.mlmechtrade.c2api.C2SignalEntryCommandEnum;
import sf.net.mlmechtrade.c2api.C2SignalEntryCommandParamEnum;
import static sf.net.mlmechtrade.c2api.C2SignalEntryCommandEnum.*;

public class PlaceOrderRequest
					extends C2Request<C2SignalEntryCommandEnum, C2SignalEntryCommandParamEnum> {
	
	
	public static enum Action {

		/** Buy To Open : open a long position */
		BTO,

		/** Sell Short : used for stocks */
		SSHORT,

		/** Sell To Open : used for non-stocks */
		STO,

		/** Buy To Close : close a short position */
		BTC,

		/** Sell To Close : close a long position */
		STC
	}

	public static enum Instrument {

		/** Note that ETFs like QQQ and DIA are traded on a stock exchange and thus are considered stocks. */
		stock,
		option,
		future,
		forex
	}
	
	public static enum Duration {
		
		/** Day Order */
		DAY,

		/** Good Til Cancel */
		GTC
	}
	

	private String systemid;
	private String pw;
	private Action action;
	private int quant;
	private Instrument instrument;
	private String symbol;
	private double limit;
	private double stop;
	private Duration duration;
	/** own signal id - greater than zero and less than or equal to 4294967295. */
	private long signalid; 

	public PlaceOrderRequest() {
		super(signal);
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public double getLimit() {
		return limit;
	}

	public void setLimit(double limit) {
		this.limit = limit;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public int getQuant() {
		return quant;
	}

	public void setQuant(int quant) {
		this.quant = quant;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	public double getStop() {
		return stop;
	}

	public void setStop(double stop) {
		this.stop = stop;
	}

	public long getSignalid() {
		return signalid;
	}

	public void setSignalid(long signalid) {
		this.signalid = signalid;
	}

	public long getOwnSignalId() {
		return getSignalid();
	}

	public void setOwnSignalId(long ownSignalId) {
		setSignalid(signalid);
	}
}
