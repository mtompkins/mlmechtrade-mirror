package sf.net.mlmechtrade.c2api.request;

import static sf.net.mlmechtrade.c2api.C2SignalEntryCommandEnum.reverse;
import static sf.net.mlmechtrade.c2api.request.DurationEnum.GTC;
import sf.net.mlmechtrade.C2Error;
import sf.net.mlmechtrade.annotations.Required;

/**
 * If you are long an instrument, and you want to go short (or vice-versa) that is 
 * called a reversal. 
  */
public class ReversalOrderRequest extends TradeSignalRequest {

	/** symbol=SYMBOL (required) */
	@Required
	private String symbol;
	
	/** price (optional) */
	private double triggerprice;
	
	/** optional; will be GTC unless specified*/
	private DurationEnum duration = GTC;
	
	/** new opening quant (optional; use only if you want your final position to be
	 *  a quantity different than your prior quantity; if not specified, 
	 *  you will go from long to short, or short to long, 
	 *  using the same quantity of position before the reversal)
	 */
	private int quant;

	public ReversalOrderRequest() {
		super(reverse);
	}

	public DurationEnum getDuration() {
		return duration;
	}

	public void setDuration(DurationEnum duration) {
		this.duration = duration;
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

	public double getTriggerprice() {
		return triggerprice;
	}

	public void setTriggerprice(double triggerprice) {
		this.triggerprice = triggerprice;
	}

	@Override
	public C2Error validate() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
