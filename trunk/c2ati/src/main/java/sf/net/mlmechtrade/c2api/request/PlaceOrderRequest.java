package sf.net.mlmechtrade.c2api.request;

import static sf.net.mlmechtrade.c2api.C2SignalEntryCommandEnum.signal;
import sf.net.mlmechtrade.C2Error;

public class PlaceOrderRequest extends TradeSignalRequest {
	
	
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
	
	private String systemid;
	private String pw;
	private Action action;
	private int quant;
	private Instrument instrument;
	private String symbol;
	private double limit;
	private double stop;
	private DurationEnum duration;
	
	/** own signal id - greater than zero and less than or equal to 4294967295. */
	private long signalid;
	
	/** A conditional order is an order that does not become valid until a preceding order is filled. */
	private double conditionalupon;

	public PlaceOrderRequest() {
		super(signal);
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public DurationEnum getDuration() {
		return duration;
	}

	public void setDuration(DurationEnum duration) {
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

	public double getConditionalupon() {
		return conditionalupon;
	}

	public void setConditionalupon(double conditionalupon) {
		this.conditionalupon = conditionalupon;
	}

	@Override
	public C2Error validate() {
		// TODO Auto-generated method stub
		return null;
	}
}
