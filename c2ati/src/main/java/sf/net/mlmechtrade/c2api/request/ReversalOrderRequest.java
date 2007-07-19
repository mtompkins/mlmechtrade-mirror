package sf.net.mlmechtrade.c2api.request;

import static sf.net.mlmechtrade.c2api.C2SignalEntryCommandEnum.reverse;
import sf.net.mlmechtrade.C2Error;

/**
 * If you are long an instrument, and you want to go short (or vice-versa) that is called a reversal. 
 *
<p>Here are the reversal-related parameters you can use:

<li>cmd=reverse (required)

<li>symbol=SYMBOL (required)

<li>triggerprice=price (optional)

<li>duration=DAY/GTC (optional; will be GTC unless specified)

<li>quant=new opening quant (optional; use only if you want your final position to be a quantity different than your prior quantity; if not specified, you will go from long to short, or short to long, using the same quantity of position before the reversal)


 */
public class ReversalOrderRequest extends TradeSignalRequest {
	
	private String symbol;
	private double triggerprice;
	private DurationEnum duration;
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
